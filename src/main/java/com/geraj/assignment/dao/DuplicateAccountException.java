package com.geraj.assignment.dao;
import java.util.Objects;

/**
 * Indicates that an account cannot be created because a unique field
 * conflicts with an existing account.
 */
public class DuplicateAccountException extends IllegalStateException {

    private final String fieldName;

    /**
     * Creates an exception describing the conflicting account field.
     *
     * @param fieldName the conflicting field, such as email or username
     * @param cause the database exception that caused the failure
     * @throws NullPointerException if fieldName is null
     */
    public DuplicateAccountException(String fieldName, Throwable cause) {
        super("An account already exists with this " + fieldName + ".", cause);
        this.fieldName = Objects.requireNonNull(
                fieldName,
                "Conflicting account field cannot be null"
        );
    }

    /**
     * Returns the field responsible for the uniqueness conflict.
     *
     * @return the conflicting account field
     */
    public String getFieldName() {
        return fieldName;
    }
}
