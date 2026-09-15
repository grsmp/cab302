package com.geraj.assignment.controller;

import com.geraj.assignment.AccountSession;
import com.geraj.assignment.SceneSwitcher;
import com.geraj.assignment.dao.SqliteGardenDAO;
import com.geraj.assignment.model.Garden;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class CreateGardenController {

    @FXML private TextField nameField;
    @FXML private TextField locationField;
    @FXML private Label messageLabel;
    @FXML private Button createButton;

    @FXML
    private void onCreate() {
        messageLabel.setTextFill(Color.FIREBRICK);

        AccountSession session = AccountSession.getInstance();

        if (session == null) {
            messageLabel.setText("Please sign in first.");
            return;
        }

        String name = nameField.getText().strip();
        String location = locationField.getText().strip();

        if (name.isBlank()) {
            messageLabel.setText("Enter a garden name.");
            return;
        }

        if (location.isBlank()) {
            messageLabel.setText("Enter a location.");
            return;
        }

        Garden garden = new Garden(
                name,
                location,
                1,
                1,
                1,
                session.getAccount()
        );

        try {
            new SqliteGardenDAO().createGarden(garden);
        } catch (IllegalStateException e) {
            messageLabel.setText(
                    "Could not save. The name may already be used, "
                            + "or the database may be unavailable."
            );
            return;
        }

        messageLabel.setTextFill(Color.DARKGREEN);
        messageLabel.setText("Garden saved. Click View gardens.");

        createButton.setDisable(true);
        nameField.setEditable(false);
        locationField.setEditable(false);
    }

    @FXML
    private void onBack(ActionEvent event) {
        SceneSwitcher.switchScene(event, "main-view.fxml");
    }

    @FXML
    private void onViewGardens(ActionEvent event) {
        SceneSwitcher.switchScene(event, "garden-list-view.fxml");
    }
}