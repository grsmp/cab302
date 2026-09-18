package com.geraj.assignment.controller;

import com.geraj.assignment.AccountSession;
import com.geraj.assignment.SceneSwitcher;
import com.geraj.assignment.dao.IGardenDAO;
import com.geraj.assignment.dao.SqliteGardenDAO;
import com.geraj.assignment.model.Account;
import com.geraj.assignment.model.Garden;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.Objects;

/**
 * Controls the Create Garden screen.
 */
public class CreateGardenController {

    @FXML private TextField nameField;
    @FXML private TextField locationField;
    @FXML private Label messageLabel;
    @FXML private Button createButton;

    private final IGardenDAO gardenDAO;

    /**
     * Used by FXMLLoader when opening the screen.
     */
    public CreateGardenController() {
        this(new SqliteGardenDAO());
    }

    /**
     * Allows tests to supply a DAO without accessing SQLite.
     *
     * @param gardenDAO the garden persistence implementation
     */
    public CreateGardenController(IGardenDAO gardenDAO) {
        this.gardenDAO = Objects.requireNonNull(gardenDAO);
    }

    /**
     * Validates and saves a garden using the supplied owner.
     *
     * @param name the entered garden name
     * @param location the entered location
     * @param owner the account supplied by the active session
     * @return the garden after the DAO completes successfully
     * @throws IllegalArgumentException if required details are missing
     * @throws IllegalStateException if persistence fails
     */

    public Garden createGarden(
            String name,
            String location,
            Account owner
    ) {
        if (owner == null) {
            throw new IllegalArgumentException("Please sign in first.");
        }

        String cleanName = requireText(name, "Enter a garden name.");
        String cleanLocation = requireText(location, "Enter a location.");

        Garden garden = new Garden(
                cleanName,
                cleanLocation,
                null,
                null,
                null,
                owner
        );

        gardenDAO.createGarden(garden);
        return garden;
    }

    /**
     * Requires nonblank text and removes surrounding whitespace.
     */
    private String requireText(String value, String errorMessage) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(errorMessage);
        }

        return value.strip();
    }

    @FXML
    private void onCreate() {
        messageLabel.setTextFill(Color.FIREBRICK);

        AccountSession session = AccountSession.getInstance();
        Account owner = null;

        if (session != null) {
            owner = session.getAccount();
        }

        try {
            createGarden(
                    nameField.getText(),
                    locationField.getText(),
                    owner
            );
        } catch (IllegalArgumentException exception) {
            messageLabel.setText(exception.getMessage());
            return;
        } catch (IllegalStateException exception) {
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