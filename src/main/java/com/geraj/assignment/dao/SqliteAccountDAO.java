package com.geraj.assignment.dao;

import com.geraj.assignment.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Optional;

/**
 * Stores and retrieves accounts using SQLite.
 */
public class SqliteAccountDAO implements IAccountDAO {
    private final Connection connection;

    /**
     * Creates an account DAO using the application's shared database connection.
     */
    public SqliteAccountDAO() {
        this(SqliteConnection.getInstance());
    }

    /**
     * Creates an account DAO using a supplied database connection.
     *
     * @param connection the connection used for account persistence
     */
    public SqliteAccountDAO(Connection connection) {
        this.connection = Objects.requireNonNull(
                connection,
                "Account database connection cannot be null"
        );
        createTable();
    }

    private void createTable() {
        String query = """
            CREATE TABLE IF NOT EXISTS accounts (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL UNIQUE,
                email TEXT NOT NULL UNIQUE,
                firstName TEXT NOT NULL,
                lastName TEXT NOT NULL,
                phoneNumber TEXT NOT NULL,
                hash TEXT NOT NULL
            );
            """;

        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException exception) {
            throw new IllegalStateException(
                    "Could not create the accounts table",
                    exception
            );
        }
    }

    /**
     * Stores a new account.
     *
     * @param account the account to store
     */
    @Override
    public void createAccount(Account account) {
        String query = """
            INSERT INTO accounts (name, email, firstName, lastName, phoneNumber, hash)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, account.getName());
            statement.setString(2, account.getEmail());
            statement.setString(3, account.getFirstName());
            statement.setString(4, account.getLastName());
            statement.setString(5, account.getPhoneNumber());
            statement.setString(6, account.getHash());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not create the account", exception);
        }
    }

    /**
     * Finds an account by its username.
     *
     * @param searchName the username to find
     * @return the matching account, or null when no account exists
     */
    @Override
    public Account getAccountByName(String searchName) {
        String query = """
            SELECT name, email, firstName, lastName, phoneNumber, hash
            FROM accounts
            WHERE name = ?
            """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, searchName);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapAccount(resultSet);
                }
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not find the account", exception);
        }

        return null;
    }

    /**
     * Finds an account by its database identifier.
     *
     * @param accountId the account identifier
     * @return the matching account, or an empty optional when none exists
     */
    @Override
    public Optional<Account> getAccountById(int accountId) {
        String query = """
            SELECT name, email, firstName, lastName, phoneNumber, hash
            FROM accounts
            WHERE id = ?
            """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, accountId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapAccount(resultSet));
                }
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not find the account", exception);
        }

        return Optional.empty();
    }

    /**
     * Finds an account by its email address.
     *
     * @param email the email address to find
     * @return the matching account, or an empty optional when none exists
     */
    @Override
    public Optional<Account> getAccountByEmail(String email) {
        String query = """
            SELECT name, email, firstName, lastName, phoneNumber, hash
            FROM accounts
            WHERE email = ?
            """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapAccount(resultSet));
                }
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not find the account", exception);
        }

        return Optional.empty();
    }

    /**
     * Updates the editable personal information for an existing account.
     *
     * @param account the account containing the new personal information
     * @return true when one account was updated, otherwise false
     */
    @Override
    public boolean updatePersonalInformation(Account account) {
        String query = """
            UPDATE accounts
            SET firstName = ?, lastName = ?, email = ?, phoneNumber = ?
            WHERE name = ?
            """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, account.getFirstName());
            statement.setString(2, account.getLastName());
            statement.setString(3, account.getEmail());
            statement.setString(4, account.getPhoneNumber());
            statement.setString(5, account.getName());
            return statement.executeUpdate() == 1;
        } catch (SQLException exception) {
            return false;
        }
    }

    private Account mapAccount(ResultSet resultSet) throws SQLException {
        return new Account(
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("firstName"),
                resultSet.getString("lastName"),
                resultSet.getString("phoneNumber"),
                resultSet.getString("hash")
        );
    }
}
