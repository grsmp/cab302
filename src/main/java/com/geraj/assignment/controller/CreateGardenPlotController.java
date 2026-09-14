package com.geraj.assignment.controller;

import com.geraj.assignment.model.GardenPlot;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateGardenPlotController {

    @FXML private TextField widthField;
    @FXML private TextField lengthField;
    @FXML private TextField phField;
    @FXML private TextField lightField;
    @FXML private TextField nutrimentsField;
    @FXML private TextField salinityField;
    @FXML private TextField textureField;
    @FXML private TextField depthField;
    @FXML private TextField soilHumidityField;

    @FXML private Button saveButton;
    @FXML private Label messageLabel;

    private GardenPlot newPlot = null;

    @FXML
    private void initialize() {

        /*
         * Disable the Create Garden button while any required
         * field is empty.
         */
        BooleanBinding emptyField =
                widthField.textProperty().isEmpty()
                        .or(lengthField.textProperty().isEmpty())
                        .or(phField.textProperty().isEmpty())
                        .or(lightField.textProperty().isEmpty())
                        .or(nutrimentsField.textProperty().isEmpty())
                        .or(salinityField.textProperty().isEmpty())
                        .or(textureField.textProperty().isEmpty())
                        .or(depthField.textProperty().isEmpty())
                        .or(soilHumidityField.textProperty().isEmpty());

        saveButton.disableProperty().bind(emptyField);
    }

    @FXML
    private void onCreate() {
        messageLabel.setText("");

        try {
            double width = parseDoubleField(widthField, "Width");
            double length = parseDoubleField(lengthField, "Length");
            double ph = parseDoubleField(phField, "pH");
            int light = parseIntField(lightField, "Light");
            int nutriments = parseIntField(nutrimentsField, "Nutriments");
            int salinity = parseIntField(salinityField, "Salinity");
            int texture = parseIntField(textureField, "Texture");
            double depth = parseDoubleField(depthField, "Depth");
            int soilHumidity = parseIntField(soilHumidityField, "Soil Humidity");

            newPlot = new GardenPlot(
                    width, length, ph, light, nutriments,
                    salinity, texture, depth, soilHumidity
            );

            closeWindow();

        } catch (IllegalArgumentException e) {
            messageLabel.setText(e.getMessage());
        }
    }

    private double parseDoubleField(TextField field, String fieldName) {
        try {
            return Double.parseDouble(field.getText());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " must be a valid decimal number.");
        }
    }

    private int parseIntField(TextField field, String fieldName) {
        try {
            return Integer.parseInt(field.getText());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " must be a valid whole number.");
        }
    }

    @FXML
    private void onCancel() {
        newPlot = null;
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    public GardenPlot getNewPlot() {
        return newPlot;
    }
}