package com.geraj.assignment.controller;

import com.geraj.assignment.AccountSession;
import com.geraj.assignment.SceneSwitcher;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;

public class NavController {

    // Logged in
    @FXML private Label gardenLabel;
    @FXML private Label scheduleLabel;
    @FXML private Label accountLabel;
    @FXML private Label mainLabel;

    // Logged out
    @FXML private Label signInLabel;
    @FXML private Label createAccountLabel;

    @FXML
    public void initialize() {
        updateNavigationVisibility();
        highlightActiveTab(SceneSwitcher.getCurrentView());
    }

    private void updateNavigationVisibility() {
        boolean loggedIn = AccountSession.isLoggedIn();

        // Visible only when logged in
        setLabelVisible(gardenLabel, loggedIn);
        setLabelVisible(scheduleLabel, loggedIn);
        setLabelVisible(accountLabel, loggedIn);
        setLabelVisible(mainLabel, loggedIn);

        // Visible only when logged out
        setLabelVisible(signInLabel, !loggedIn);
        setLabelVisible(createAccountLabel, !loggedIn);
    }

    private void setLabelVisible(Label label, boolean visible) {
        if (label != null) {
            label.setVisible(visible);
            label.setManaged(visible);
        }
    }

    private void highlightActiveTab(String currentView) {
        if (currentView == null) return;

        switch (currentView) {
            // logged in
            case "garden-view.fxml" -> gardenLabel.getStyleClass().add("active");
//            case "schedule-view.fxml" -> scheduleLabel.getStyleClass().add("active");
            case "account-profile-view.fxml" -> accountLabel.getStyleClass().add("active");
            case "main-view.fxml" -> mainLabel.getStyleClass().add("active");

            // logged out
            case "sign-in-view.fxml" -> signInLabel.getStyleClass().add("active");
            case "create-account-view.fxml" -> createAccountLabel.getStyleClass().add("active");
        }
    }

    @FXML
    private void goToGarden(MouseEvent actionEvent) {
        SceneSwitcher.switchScene(
                actionEvent,
                "garden-view.fxml"
        );
    }

    @FXML
    private void goToSchedule(MouseEvent actionEvent) {
        SceneSwitcher.switchScene(
                actionEvent,
                "schedule-view.fxml"
        );
    }

    @FXML
    private void goToAccount(MouseEvent actionEvent) {
        SceneSwitcher.switchScene(
                actionEvent,
                "account-profile-view.fxml"
        );
    }

    @FXML
    private void goToMain(MouseEvent actionEvent) {
        SceneSwitcher.switchScene(
                actionEvent,
                "main-view.fxml"
        );
    }

    @FXML
    private void goToSignIn(MouseEvent actionEvent) {
        SceneSwitcher.switchScene(
                actionEvent,
                "sign-in-view.fxml"
        );
    }

    @FXML
    private void goToCreateAccount(MouseEvent actionEvent) {
        SceneSwitcher.switchScene(
                actionEvent,
                "create-account-view.fxml"
        );
    }
}
