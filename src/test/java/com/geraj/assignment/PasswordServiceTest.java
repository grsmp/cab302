package com.geraj.assignment;

import com.geraj.assignment.PasswordService;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordServiceTest {
    private PasswordService passwordService;

    @BeforeEach
    public void setUp() {
        passwordService = PasswordService.getInstance();
    }

    @Test
    public void testHashPasswordReturnsArgon2Hash() {
        char[] password = "password123".toCharArray();
        String hash = passwordService.hashPassword(password);
        assertTrue(hash.startsWith("$argon2"));
    }

    @Test
    public void testVerifyPasswordCorrectPasswordSucceeds() {
        char[] passwordForHash = "password123".toCharArray();
        String hash = passwordService.hashPassword(passwordForHash);

        char[] passwordForVerify = "password123".toCharArray();
        assertTrue(passwordService.verifyPassword(hash, passwordForVerify));
    }

    @Test
    public void testVerifyPasswordIncorrectPasswordFails() {
        char[] passwordForHash = "password123".toCharArray();
        String hash = passwordService.hashPassword(passwordForHash);

        char[] passwordForVerify = "WrongPassword".toCharArray();
        assertFalse(passwordService.verifyPassword(hash, passwordForVerify));
    }

    @Test
    public void testVerifyPasswordNullHashReturnsFalse() {
        char[] password = "password".toCharArray();
        assertFalse(passwordService.verifyPassword(null, password));
    }

    @Test
    public void testVerifyPasswordNullPasswordReturnsFalse() {
        assertFalse(passwordService.verifyPassword("hash_string", null));
    }

    @Test
    public void testPasswordArrayIsWipedAfterHashing() {
        char[] password = "password123".toCharArray();
        passwordService.hashPassword(password);

        for (char c : password) {
            assertEquals('\0', c);
        }

    }

    @Test
    public void testPasswordArrayIsWipedAfterVerification() {
        char[] passwordForHash = "password123".toCharArray();
        String hash = passwordService.hashPassword(passwordForHash);

        char[] passwordForVerify = "password123".toCharArray();
        passwordService.verifyPassword(hash, passwordForVerify);

        for (char c : passwordForVerify) {
            assertEquals('\0', c);
        }
    }
}