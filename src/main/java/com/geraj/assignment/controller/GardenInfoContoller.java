package com.geraj.assignment.controller;

import com.geraj.assignment.SceneSwitcher;
import com.geraj.assignment.model.Garden;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class GardenInfoContoller {
    @FXML private Label GardenName;
    @FXML private Label GardenLocation;
    @FXML private TextArea gardenDescription;


    public void setInfo(Garden garden) {
        GardenName.setText(garden.getName());
        GardenLocation.setText(garden.getLocation());
        gardenDescription.setText(garden.getDescription());
    }

    @FXML
    private void onBack(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(actionEvent, "garden-list-view.fxml");
    }
}
