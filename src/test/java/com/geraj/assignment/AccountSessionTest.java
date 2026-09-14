package com.geraj.assignment;

import com.geraj.assignment.AccountSession;
import com.geraj.assignment.model.Account;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class AccountSessionTest {
    private AccountSession accountSession;
    private Account account;

    @BeforeEach
    public void setUp() {
        AccountSession.logout();
        account = new Account("Name", "email@example.com", "First", "Last", "0123456789","hash_string");
    }

    @Test
    public void getInstanceShouldReturnNullBeforeStartSession() {
        assertNull(AccountSession.getInstance());
    }

    @Test
    public void getInstanceShouldNotReturnNullAfterStartSession() {
        AccountSession.startSession(account);

        AccountSession session = AccountSession.getInstance();
        assertNotNull(session);
    }

    @Test
    public void getAccountShouldReturnAccountPassedToStartSession() {
        AccountSession.startSession(account);

        AccountSession session = AccountSession.getInstance();
        assertNotNull(session);
        assertSame(account, session.getAccount());
    }

    @Test
    public void getInstanceShouldReturnSameInstanceOnMultipleCalls() {
        AccountSession.startSession(account);

        AccountSession firstCall = AccountSession.getInstance();
        AccountSession secondCall = AccountSession.getInstance();

        assertSame(firstCall, secondCall);
    }

    @Test
    public void clearShouldResetInstanceToNull() {
        AccountSession.startSession(account);
        assertNotNull(AccountSession.getInstance());

        AccountSession.logout();

        assertNull(AccountSession.getInstance());
    }

    @Test
    public void startSessionShouldOverwriteExistingSession() {
        Account newAccount = new Account("NewName", "newEmail@example.com", "NewFirst", "NewLast", "0123456789","new_hash_string");

        AccountSession.startSession(account);
        AccountSession.startSession(newAccount);

        AccountSession currentSession = AccountSession.getInstance();
        assertNotNull(currentSession);
        assertEquals(newAccount, currentSession.getAccount());
    }

    @Test
    public void startSessionWithNullShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            AccountSession.startSession(null);
        });
    }
}
