package com.geraj.assignment.dao;

import com.geraj.assignment.model.Account;

import java.util.Optional;

/**
 * Defines persistence operations for accounts.
 */
public interface IAccountDAO {
    /**
     * Stores a new account.
     *
     * @param account the account to store
     */
    void createAccount(Account account);

    /**
     * Finds an account by its username.
     *
     * @param name the username to find
     * @return the matching account, or null when no account exists
     */
    Account getAccountByName(String name);

    /**
     * Finds an account by its database identifier.
     *
     * @param accountId the account identifier
     * @return the matching account, or an empty optional when none exists
     */
    Optional<Account> getAccountById(int accountId);

    /**
     * Finds an account by its email address.
     *
     * @param email the email address to find
     * @return the matching account, or an empty optional when none exists
     */
    Optional<Account> getAccountByEmail(String email);

    /**
     * Updates the editable personal information for an existing account.
     *
     * @param account the account containing the new personal information
     * @return true when one account was updated, otherwise false
     */
    boolean updatePersonalInformation(Account account);
}
