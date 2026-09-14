package com.geraj.assignment;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

/**
 * Singleton service for securely hashing and verifying passwords using the Argon2.
 * This class automatically wipes sensitive character arrays from memory after hashing or verification operations.
 */
public class PasswordService {
    private static final PasswordService INSTANCE = new PasswordService(2, 65536, 1);

    private final int iterations;
    private final int memory;
    private final int parallelism;
    private final Argon2 argon2;

    /**
     * Private constructor to enforce the Singleton pattern and initialize Argon2 configuration.
     * @param iterations the number of iterations (time cost)
     * @param memory the memory usage limit in kibibytes (memory cost)
     * @param parallelism the number of concurrent threads to use
     */
    private PasswordService(int iterations, int memory, int parallelism) {
        this.iterations = iterations;
        this.memory = memory;
        this.parallelism = parallelism;
        this.argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
    }

    /**
     * Retrieves the current active {@link PasswordService} instance.
     * @return the active {@link PasswordService} instance
     */
    public static PasswordService getInstance() {
        return INSTANCE;
    }

    /**
     * Hashes a plaintext password using Argon2.
     * The provided {@code password} array is automatically wiped from memory upon completion.
     * @param password the plaintext password array to hash
     * @return the formatted Argon2 hash string containing algorithm parameters, salt, and hash
     */
    public String hashPassword(char[] password) {
        try {
            return argon2.hash(iterations, memory, parallelism, password);
        } finally {
            argon2.wipeArray(password);
        }
    }

    /**
     * Verifies a plaintext password against an Argon2 hash string.
     * The provided {@code password} array is automatically wiped from memory upon completion.
     * @param hash the Argon2 hash string
     * @param password the plaintext password array to verify
     * @return {@code true} if the password matches the hash;
     * {@code false} if either parameter is {@code null} or the verification fails
     */
    public boolean verifyPassword(String hash, char[] password) {
        if (hash == null || password == null) {
            return false;
        }
        try {
            return argon2.verify(hash, password);
        } finally {
            argon2.wipeArray(password);
        }
    }

    /**
     * Clears sensitive password data from memory by zeroing out the character array.
     * @param password the plaintext password array to wipe
     */
    public void wipePassword(char[] password) {
        argon2.wipeArray(password);
    }
}