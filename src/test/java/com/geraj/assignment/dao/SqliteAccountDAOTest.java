package com.geraj.assignment.dao;

import com.geraj.assignment.model.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.sql.PreparedStatement;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SqliteAccountDAOTest {
    private Connection connection;
    private SqliteAccountDAO accountDAO;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        accountDAO = new SqliteAccountDAO(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    void getAccountByIdShouldReturnTheStoredAccount() {
        accountDAO.createAccount(createAccount(
                "harresh", "harresh@example.com", "Harresh", "Patel",
                "0412345678", "stored-hash"
        ));

        Optional<Account> result = accountDAO.getAccountById(1);

        assertTrue(result.isPresent());
        Account account = result.orElseThrow();
        assertAll(
                () -> assertEquals("harresh", account.getName()),
                () -> assertEquals("harresh@example.com", account.getEmail()),
                () -> assertEquals("Harresh", account.getFirstName()),
                () -> assertEquals("Patel", account.getLastName()),
                () -> assertEquals("0412345678", account.getPhoneNumber()),
                () -> assertEquals("stored-hash", account.getHash())
        );
    }

    @Test
    void getAccountByEmailWithUnknownEmailShouldReturnEmpty() {
        Optional<Account> result = accountDAO.getAccountByEmail("missing@example.com");

        assertTrue(result.isEmpty());
    }

    @Test
    void updatePersonalInformationShouldPersistEditableFieldsAndPreserveCredentials() {
        Account account = createAccount(
                "harresh", "old@example.com", "Old", "Name", "", "stored-hash"
        );
        accountDAO.createAccount(account);

        account.setFirstName("Harresh");
        account.setLastName("Patel");
        account.setEmail("harresh@example.com");
        account.setPhoneNumber("0412345678");

        assertTrue(accountDAO.updatePersonalInformation(account));

        Account savedAccount = accountDAO.getAccountByEmail("harresh@example.com")
                .orElseThrow();
        assertAll(
                () -> assertEquals("Harresh", savedAccount.getFirstName()),
                () -> assertEquals("Patel", savedAccount.getLastName()),
                () -> assertEquals("0412345678", savedAccount.getPhoneNumber()),
                () -> assertEquals("harresh", savedAccount.getName()),
                () -> assertEquals("stored-hash", savedAccount.getHash())
        );
    }

    @Test
    void updatePersonalInformationWithDuplicateEmailShouldNotWriteAnyChanges() {
        Account firstAccount = createAccount(
                "harresh", "harresh@example.com", "Harresh", "Patel",
                "0412345678", "first-hash"
        );
        Account secondAccount = createAccount(
                "alex", "alex@example.com", "Alex", "Smith",
                "0498765432", "second-hash"
        );
        accountDAO.createAccount(firstAccount);
        accountDAO.createAccount(secondAccount);

        secondAccount.setFirstName("Changed");
        secondAccount.setEmail("harresh@example.com");

        assertFalse(accountDAO.updatePersonalInformation(secondAccount));

        Account savedAccount = accountDAO.getAccountByEmail("alex@example.com")
                .orElseThrow();
        assertAll(
                () -> assertEquals("Alex", savedAccount.getFirstName()),
                () -> assertEquals("Smith", savedAccount.getLastName()),
                () -> assertEquals("0498765432", savedAccount.getPhoneNumber()),
                () -> assertEquals("alex", savedAccount.getName()),
                () -> assertEquals("second-hash", savedAccount.getHash())
        );
    }

    /**
     * Verifies that retrieving an existing account preserves its database ID.
     * The row is inserted directly so the test does not depend on createAccount
     * assigning generated identifiers, which will be tested separately.
     *
     * @throws SQLException if the test row cannot be inserted
     */
    @Test
    void getAccountByIdShouldPreserveStoredIdentifier() throws SQLException {
        // Use an explicit ID to verify that the DAO reads the stored value.
        String query = """
            INSERT INTO accounts
                (id, name, email, firstName, lastName, phoneNumber, hash)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, 42);
            statement.setString(2, "harresh");
            statement.setString(3, "harresh@example.com");
            statement.setString(4, "Harresh");
            statement.setString(5, "Patel");
            statement.setString(6, "0412345678");
            statement.setString(7, "stored-hash");
            statement.executeUpdate();
        }

        Optional<Account> result = accountDAO.getAccountById(42);

        assertTrue(result.isPresent(), "The stored account should be found.");
        assertEquals(
                Integer.valueOf(42),
                result.orElseThrow().getId(),
                "The retrieved account should retain its database identifier."
        );
    }

    private Account createAccount(
            String name,
            String email,
            String firstName,
            String lastName,
            String phoneNumber,
            String hash
    ) {
        return new Account(name, email, firstName, lastName, phoneNumber, hash);
    }
}
