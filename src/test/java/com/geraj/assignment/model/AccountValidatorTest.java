package com.geraj.assignment.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountValidatorTest {
    private static final String FIRST_NAME = "Harresh";
    private static final String LAST_NAME = "Patel";
    private static final String EMAIL = "harresh@example.com";
    private static final String PHONE_NUMBER = "0412345678";

    @Test
    void validPersonalInformationShouldReturnNoErrors() {
        AccountValidator.ValidationResult result = validate(
                FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER
        );

        assertTrue(result.isValid());
        assertTrue(result.getErrors().isEmpty());
    }

    @Test
    void namesWithSurroundingWhitespaceShouldReturnNoErrors() {
        AccountValidator.ValidationResult result = validate(
                "  Harresh  ", "  Patel  ", EMAIL, PHONE_NUMBER
        );

        assertTrue(result.isValid());
    }

    @Test
    void firstNameWithOneHundredCharactersShouldReturnNoError() {
        AccountValidator.ValidationResult result = validate(
                "a".repeat(100), LAST_NAME, EMAIL, PHONE_NUMBER
        );

        assertFalse(result.hasError("firstName"));
    }

    @Test
    void firstNameWithOneHundredAndOneCharactersShouldReturnError() {
        AccountValidator.ValidationResult result = validate(
                "a".repeat(101), LAST_NAME, EMAIL, PHONE_NUMBER
        );

        assertTrue(result.hasError("firstName"));
    }

    @Test
    void nullFirstNameShouldReturnError() {
        assertTrue(validate(null, LAST_NAME, EMAIL, PHONE_NUMBER)
                .hasError("firstName"));
    }

    @Test
    void blankFirstNameShouldReturnError() {
        assertTrue(validate("   ", LAST_NAME, EMAIL, PHONE_NUMBER)
                .hasError("firstName"));
    }

    @Test
    void lastNameWithOneHundredCharactersShouldReturnNoError() {
        AccountValidator.ValidationResult result = validate(
                FIRST_NAME, "a".repeat(100), EMAIL, PHONE_NUMBER
        );

        assertFalse(result.hasError("lastName"));
    }

    @Test
    void lastNameWithOneHundredAndOneCharactersShouldReturnError() {
        AccountValidator.ValidationResult result = validate(
                FIRST_NAME, "a".repeat(101), EMAIL, PHONE_NUMBER
        );

        assertTrue(result.hasError("lastName"));
    }

    @Test
    void nullLastNameShouldReturnError() {
        assertTrue(validate(FIRST_NAME, null, EMAIL, PHONE_NUMBER)
                .hasError("lastName"));
    }

    @Test
    void emptyLastNameShouldReturnError() {
        assertTrue(validate(FIRST_NAME, "", EMAIL, PHONE_NUMBER)
                .hasError("lastName"));
    }

    @Test
    void emailWithTwoHundredAndFiftyFourCharactersShouldReturnNoError() {
        String email = "a".repeat(242) + "@example.com";

        assertFalse(validate(FIRST_NAME, LAST_NAME, email, PHONE_NUMBER)
                .hasError("email"));
    }

    @Test
    void emailWithTwoHundredAndFiftyFiveCharactersShouldReturnError() {
        String email = "a".repeat(243) + "@example.com";

        assertTrue(validate(FIRST_NAME, LAST_NAME, email, PHONE_NUMBER)
                .hasError("email"));
    }

    @Test
    void nullEmailShouldReturnError() {
        assertTrue(validate(FIRST_NAME, LAST_NAME, null, PHONE_NUMBER)
                .hasError("email"));
    }

    @Test
    void emailWithoutAtSymbolShouldReturnError() {
        assertTrue(validate(
                FIRST_NAME, LAST_NAME, "harresh.example.com", PHONE_NUMBER
        ).hasError("email"));
    }

    @Test
    void emailWithoutDomainDotShouldReturnError() {
        assertTrue(validate(
                FIRST_NAME, LAST_NAME, "harresh@example", PHONE_NUMBER
        ).hasError("email"));
    }

    @Test
    void emailContainingWhitespaceShouldReturnError() {
        assertTrue(validate(
                FIRST_NAME, LAST_NAME, "harresh @example.com", PHONE_NUMBER
        ).hasError("email"));
    }

    @Test
    void emptyPhoneNumberShouldReturnNoError() {
        assertFalse(validate(FIRST_NAME, LAST_NAME, EMAIL, "")
                .hasError("phoneNumber"));
    }

    @Test
    void phoneNumberContainingSpacesShouldReturnNoError() {
        assertFalse(validate(FIRST_NAME, LAST_NAME, EMAIL, "0412 345 678")
                .hasError("phoneNumber"));
    }

    @Test
    void phoneNumberWithAustralianCountryCodeShouldReturnNoError() {
        assertFalse(validate(FIRST_NAME, LAST_NAME, EMAIL, "+61412345678")
                .hasError("phoneNumber"));
    }

    @Test
    void nullPhoneNumberShouldReturnError() {
        assertTrue(validate(FIRST_NAME, LAST_NAME, EMAIL, null)
                .hasError("phoneNumber"));
    }

    @Test
    void phoneNumberWithIncorrectPrefixShouldReturnError() {
        assertTrue(validate(FIRST_NAME, LAST_NAME, EMAIL, "1412345678")
                .hasError("phoneNumber"));
    }

    @Test
    void phoneNumberWithTooFewDigitsShouldReturnError() {
        assertTrue(validate(FIRST_NAME, LAST_NAME, EMAIL, "041234567")
                .hasError("phoneNumber"));
    }

    private AccountValidator.ValidationResult validate(
            String firstName,
            String lastName,
            String email,
            String phoneNumber
    ) {
        return AccountValidator.validatePersonalInformation(
                firstName,
                lastName,
                email,
                phoneNumber
        );
    }
}
