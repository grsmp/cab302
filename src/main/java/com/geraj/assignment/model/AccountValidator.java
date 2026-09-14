package com.geraj.assignment.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Validates personal information stored for an account.
 */
public final class AccountValidator {
    private static final int MAXIMUM_NAME_LENGTH = 100;
    private static final int MAXIMUM_EMAIL_LENGTH = 254;
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$"
    );
    private static final Pattern LOCAL_PHONE_PATTERN = Pattern.compile("^0\\d{9}$");
    private static final Pattern INTERNATIONAL_PHONE_PATTERN = Pattern.compile("^\\+61\\d{9}$");

    private AccountValidator() {
    }

    /**
     * Validates editable personal information for an account profile.
     *
     * @param firstName the account holder's first name
     * @param lastName the account holder's last name
     * @param email the account holder's email address
     * @param phoneNumber the account holder's optional phone number
     * @return the field-level validation result
     */
    public static ValidationResult validatePersonalInformation(
            String firstName,
            String lastName,
            String email,
            String phoneNumber
    ) {
        Map<String, String> errors = new LinkedHashMap<>();

        validateName(firstName, "firstName", "First name", errors);
        validateName(lastName, "lastName", "Last name", errors);
        validateEmail(email, errors);
        validatePhoneNumber(phoneNumber, errors);

        return new ValidationResult(errors);
    }

    private static void validateName(
            String value,
            String fieldName,
            String label,
            Map<String, String> errors
    ) {
        if (value == null || value.trim().isEmpty()) {
            errors.put(fieldName, label + " is required.");
        } else if (value.trim().length() > MAXIMUM_NAME_LENGTH) {
            errors.put(fieldName, label + " must be 100 characters or fewer.");
        }
    }

    private static void validateEmail(String email, Map<String, String> errors) {
        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "Email is required.");
            return;
        }

        String trimmedEmail = email.trim();
        if (trimmedEmail.length() > MAXIMUM_EMAIL_LENGTH
                || !EMAIL_PATTERN.matcher(trimmedEmail).matches()) {
            errors.put("email", "Enter a valid email address.");
        }
    }

    private static void validatePhoneNumber(
            String phoneNumber,
            Map<String, String> errors
    ) {
        if (phoneNumber == null) {
            errors.put("phoneNumber", "Phone number cannot be null.");
            return;
        }

        String phoneNumberWithoutSpaces = phoneNumber.replace(" ", "");
        if (!phoneNumberWithoutSpaces.isEmpty()
                && !LOCAL_PHONE_PATTERN.matcher(phoneNumberWithoutSpaces).matches()
                && !INTERNATIONAL_PHONE_PATTERN.matcher(phoneNumberWithoutSpaces).matches()) {
            errors.put("phoneNumber", "Enter a valid Australian phone number.");
        }
    }

    /**
     * Contains immutable field-level validation errors.
     */
    public static final class ValidationResult {
        private final Map<String, String> errors;

        private ValidationResult(Map<String, String> errors) {
            this.errors = Collections.unmodifiableMap(new LinkedHashMap<>(errors));
        }

        /**
         * Reports whether every supplied field is valid.
         *
         * @return true when the result contains no errors
         */
        public boolean isValid() {
            return errors.isEmpty();
        }

        /**
         * Reports whether a field has a validation error.
         *
         * @param fieldName the field name to inspect
         * @return true when an error exists for the field
         */
        public boolean hasError(String fieldName) {
            return errors.containsKey(fieldName);
        }

        /**
         * Returns all validation errors by field name.
         *
         * @return an unmodifiable map of field names to error messages
         */
        public Map<String, String> getErrors() {
            return errors;
        }
    }
}
