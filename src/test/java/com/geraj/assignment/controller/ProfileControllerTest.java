package com.geraj.assignment.controller;

import com.geraj.assignment.AccountSession;
import com.geraj.assignment.dao.IAccountDAO;
import com.geraj.assignment.model.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests profile logout decisions independently of JavaFX windows and storage.
 */
public class ProfileControllerTest {
    private Account account;

    @BeforeEach
    void setUp() {
        AccountSession.logout();
        account = new Account(
                "harresh", "harresh@example.com", "Harresh",
                "Patel", "0412345678", "stored-hash"
        );
        AccountSession.startSession(account);
    }

    @AfterEach
    void tearDown() {
        // Prevent the singleton session from leaking into another test.
        AccountSession.logout();
    }

    /**
     * Cancelling the existing navigation confirmation must preserve the
     * session and leave the user on the profile.
     */
    @Test
    void cancelledLogoutShouldPreserveSessionAndPreventNavigation() {
        ProfileController controller = createController(false);
        AccountSession originalSession = AccountSession.getInstance();
        AtomicBoolean navigated = new AtomicBoolean(false);

        controller.logout(() -> navigated.set(true));

        assertAll(
                () -> assertSame(
                        originalSession, AccountSession.getInstance()
                ),
                () -> assertTrue(AccountSession.isLoggedIn()),
                () -> assertFalse(navigated.get())
        );
    }

    /**
     * Approved logout must clear the session before opening the destination,
     * so its navigation controls initialise in the signed-out state.
     */
    @Test
    void approvedLogoutShouldClearSessionBeforeNavigation() {
        ProfileController controller = createController(true);
        AtomicBoolean navigated = new AtomicBoolean(false);

        controller.logout(() -> {
            assertFalse(
                    AccountSession.isLoggedIn(),
                    "The session must end before navigation begins."
            );
            navigated.set(true);
        });

        assertAll(
                () -> assertNull(AccountSession.getInstance()),
                () -> assertTrue(navigated.get())
        );
    }

    /**
     * Supplies a predictable confirmation decision without opening an alert.
     * The actual alert and FXML wiring will be verified separately.
     */
    private ProfileController createController(boolean allowNavigation) {
        return new ProfileController(
                new UnusedAccountDAO(),
                requestedAccount -> {
                    throw new AssertionError(
                            "Logout must not query contribution totals."
                    );
                }
        ) {
            @Override
            public boolean confirmNavigationIfDirty() {
                return allowNavigation;
            }
        };
    }

    /**
     * Fails immediately if logout unexpectedly performs account persistence.
     */
    private static final class UnusedAccountDAO implements IAccountDAO {

        @Override
        public void createAccount(Account account) {
            throw unexpectedAccess();
        }

        @Override
        public Account getAccountByName(String name) {
            throw unexpectedAccess();
        }

        @Override
        public Optional<Account> getAccountById(int accountId) {
            throw unexpectedAccess();
        }

        @Override
        public Optional<Account> getAccountByEmail(String email) {
            throw unexpectedAccess();
        }

        @Override
        public boolean updatePersonalInformation(Account account) {
            throw unexpectedAccess();
        }

        private AssertionError unexpectedAccess() {
            return new AssertionError(
                    "Logout must not access account persistence."
            );
        }
    }
}
