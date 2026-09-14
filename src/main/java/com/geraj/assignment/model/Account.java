package com.geraj.assignment.model;

import java.util.Objects;

/**
 * A simple model class representing an Account with a name, email, first name, last name, phone number and password hash.
 */
public class Account {

    private String name;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String hash;

    /**
     * Constructs a new Account with the specified name, email, first name, last name, phone number and password hash.
     * @param name The username of the Account
     * @param email The email of the Account
     * @param firstName The first name of the Account
     * @param lastName The last name of the Account
     * @param phoneNumber The phone number of the Account
     * @param hash The hashed password of the Account
     */
    public Account(
            String name,
            String email,
            String firstName,
            String lastName,
            String phoneNumber,
            String hash
    ) {
        this.name = Objects.requireNonNull(
                name,
                "Account name cannot be null"
        );

        this.email = Objects.requireNonNull(
                email,
                "Account email cannot be null"
        );

        this.firstName = Objects.requireNonNull(
                firstName,
                "Account first name cannot be null"
        );

        this.lastName = Objects.requireNonNull(
                lastName,
                "Account last name cannot be null"
        );

        this.phoneNumber = Objects.requireNonNull(
                phoneNumber,
                "Account phone number cannot be null"
        );

        this.hash = Objects.requireNonNull(
                hash,
                "Account password hash cannot be null"
        );
    }

    /**
     * Gets the name of the account.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the account.
     * @param name the new name of the account; must not be null
     * @throws NullPointerException if {@code name} is null
     */
    public void setName(String name) {
        this.name = Objects.requireNonNull(
                name,
                "Account name cannot be null"
        );
    }

    /**
     * Gets the email of the account.
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the account.
     * @param email the new email of the account; must not be null
     * @throws NullPointerException if {@code email} is null
     */
    public void setEmail(String email) {
        this.email = Objects.requireNonNull(
                email,
                "Account email cannot be null"
        );
    }

    /**
     * Gets the first name of the account.
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the account.
     * @param firstName the new first name of the account; must not be null
     * @throws NullPointerException if {@code firstName} is null
     */
    public void setFirstName(String firstName) {
        this.firstName = Objects.requireNonNull(
                firstName,
                "Account first name cannot be null"
        );
    }

    /**
     * Gets the last name of the account.
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the account.
     * @param lastName the new last name of the account; must not be null
     * @throws NullPointerException if {@code lastName} is null
     */
    public void setLastName(String lastName) {
        this.lastName = Objects.requireNonNull(
                lastName,
                "Account last name cannot be null"
        );
    }

    /**
     * Gets the phone number of the account.
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the account.
     * @param phoneNumber the new phone number of the account; must not be null
     * @throws NullPointerException if {@code phoneNumber} is null
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = Objects.requireNonNull(
                phoneNumber,
                "Account phone number cannot be null"
        );
    }

    /**
     * Gets the hash of the account.
     * @return the hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * Sets the hash of the account.
     * @param hash the new phone number of the account; must not be null
     * @throws NullPointerException if {@code hash} is null
     */
    public void setHash(String hash) {
        this.hash = Objects.requireNonNull(
                hash,
                "Account password hash cannot be null"
        );
    }
}
