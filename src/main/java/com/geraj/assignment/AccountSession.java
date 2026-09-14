package com.geraj.assignment;

import com.geraj.assignment.model.Account;

/**
 * Manages the active user session as a Singleton.
 * Holds the currently authenticated {@link Account}.
 */
public class AccountSession {
    private static AccountSession instance;
    private final Account account;

    /**
     * Private constructor to enforce the Singleton pattern.
     * @param account the authenticated account to associate with this session
     */
    private AccountSession(Account account) {
        this.account = account;
    }

    /**
     * Starts a new user session for the specified account.
     * @param account the account to log in
     * @throws IllegalArgumentException if the provided account is {@code null}
     */
    public static void startSession(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        instance = new AccountSession(account);
    }

    /**
     * Retrieves the current active {@link AccountSession} instance.
     * @return the active {@link AccountSession} instance, or {@code null} if no user is logged in
     */
    public static AccountSession getInstance() {
        return instance;
    }

    /**
     * Retrieves the account bound to the current session.
     * @return the logged-in {@link Account}
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Ends the current session by clearing the singleton instance.
     */
    public static void logout() {
        instance = null;
    }

    /**
     * Checks if a user is currently logged in with an active session.
     * @return {@code true} if a session is active; {@code false} otherwise
     */
    public static boolean isLoggedIn() {
        return instance != null;
    }
}