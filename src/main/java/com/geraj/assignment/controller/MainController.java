package com.geraj.assignment.controller;

import com.geraj.assignment.AccountSession;
import com.geraj.assignment.SceneSwitcher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainController {

    @FXML
    private void onViewGardens(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(
                actionEvent,
                "garden-list-view.fxml"
        );
    }

    @FXML
    private void onCreateGarden(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(
                actionEvent,
                "create-garden-view.fxml"
        );
    }

    @FXML
    private void onViewProfile(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(
                actionEvent,
                "account-profile-view.fxml"
        );
    }

    @FXML
    private void onLogout(ActionEvent actionEvent) {
        AccountSession.logout();
        SceneSwitcher.switchScene(
                actionEvent,
                "landing-view.fxml"
        );
    }
}
