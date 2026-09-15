package com.geraj.assignment.controller;

import com.geraj.assignment.PasswordService;
import com.geraj.assignment.SceneSwitcher;
import com.geraj.assignment.dao.IAccountDAO;
import com.geraj.assignment.dao.SqliteAccountDAO;
import com.geraj.assignment.model.Account;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.Arrays;

public class CreateAccountController {

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button createAccountButton;

    @FXML
    private Label messageLabel;

    private final IAccountDAO accountDAO =
            new SqliteAccountDAO();

    @FXML
    private void initialize() {

        /*
         * Disable the Create Account button while any required
         * field is empty.
         */
        BooleanBinding emptyField =
                firstNameTextField.textProperty().isEmpty()
                        .or(surnameTextField.textProperty().isEmpty())
                        .or(usernameTextField.textProperty().isEmpty())
                        .or(emailTextField.textProperty().isEmpty())
                        .or(phoneNumberTextField.textProperty().isEmpty())
                        .or(passwordField.textProperty().isEmpty())
                        .or(confirmPasswordField.textProperty().isEmpty());

        createAccountButton.disableProperty().bind(emptyField);
    }

    @FXML
    private void onCreateAccount(ActionEvent actionEvent) {

        String firstName =
                firstNameTextField.getText().trim();

        String surname =
                surnameTextField.getText().trim();

        String username =
                usernameTextField.getText().trim();

        String email =
                emailTextField.getText().trim();

        String phoneNumber =
                phoneNumberTextField.getText().trim();

        char[] password =
                passwordField.getText().toCharArray();

        char[] confirmedPassword =
                confirmPasswordField.getText().toCharArray();

        messageLabel.setText("");

        if (!Arrays.equals(password, confirmedPassword)) {
            messageLabel.setText(
                    "The passwords do not match."
            );
            return;
        }

        if (!isValidEmail(email)) {
            messageLabel.setText(
                    "Please enter a valid email address."
            );
            return;
        }

        if (!isValidPhoneNumber(phoneNumber)) {
            messageLabel.setText(
                    "Please enter a valid 10-digit Australian phone number."
            );
            return;
        }

        if (password.length < 8) {
            messageLabel.setText(
                    "The password must contain at least 8 characters."
            );
            return;
        }

        PasswordService passwordService =
                PasswordService.getInstance();

        String passwordHash =
                passwordService.hashPassword(
                        password
                );
        passwordService.wipePassword(confirmedPassword);

        /*
         * Account constructor order:
         * username, email, first name, surname,
         * phone number, postcode, password hash.
         */
        Account account = new Account(
                username,
                email,
                firstName,
                surname,
                phoneNumber,
                passwordHash
        );

        accountDAO.createAccount(account);

        /*
         * After creating the account, send the user
         * to the sign-in screen.
         */
        SceneSwitcher.switchScene(
                actionEvent,
                "sign-in-view.fxml"
        );
    }

    @FXML
    private void onBack(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(
                actionEvent,
                "landing-view.fxml"
        );
    }

    private boolean isValidEmail(String email) {
        return email.contains("@")
                && email.contains(".")
                && !email.startsWith("@")
                && !email.endsWith(".");
    }

    private boolean isValidPhoneNumber(String phoneNumber) {

        /*
         * Remove spaces so both of these are accepted:
         * 0412345678
         * 0412 345 678
         */
        String phoneNumberWithoutSpaces =
                phoneNumber.replaceAll("\\s", "");

        return phoneNumberWithoutSpaces.matches(
                "0\\d{9}"
        );
    }
}