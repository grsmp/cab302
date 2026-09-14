package com.geraj.assignment.controller;

import com.geraj.assignment.AccountSession;
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

public class SignInController {

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private Label messageLabel;

    private final IAccountDAO accountDAO =
            new SqliteAccountDAO();

    @FXML
    private void initialize() {

        BooleanBinding emptyField =
                usernameTextField.textProperty().isEmpty()
                        .or(passwordField.textProperty().isEmpty());

        signInButton.disableProperty().bind(emptyField);
    }

    @FXML
    private void onSignIn(ActionEvent actionEvent) {

        String username = usernameTextField.getText().trim();
        char[] password = passwordField.getText().toCharArray();

        messageLabel.setText("");

        if (username.isEmpty() || password.length == 0) {
            messageLabel.setText(
                    "Please enter your username and password."
            );
            return;
        }

        Account account = accountDAO.getAccountByName(username);

        if (account == null) {
            messageLabel.setText(
                    "Incorrect username or password."
            );
            return;
        }

        PasswordService passwordService =
                PasswordService.getInstance();

        if (
                passwordService.verifyPassword(
                        account.getHash(),
                        password
                )
        ) {
            AccountSession.startSession(account);
            SceneSwitcher.switchScene(
                    actionEvent,
                    "main-view.fxml"
            );
        }
        else {
            messageLabel.setText(
                    "Incorrect username or password."
            );
        }
    }

    @FXML
    private void onBack(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(
                actionEvent,
                "landing-view.fxml"
        );
    }
}
