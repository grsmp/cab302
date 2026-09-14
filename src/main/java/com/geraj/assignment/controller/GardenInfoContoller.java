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


    private Garden current_garden;

    public void setInfo(Garden garden) {
        current_garden = garden;
        GardenName.setText(garden.getName());
        GardenLocation.setText(garden.getLocation());
        gardenDescription.setText(garden.getDescription());
    }

    @FXML
    private void onJoin(ActionEvent actionEvent) {
        GardenEditorController controller = SceneSwitcher.switchScene(actionEvent, "garden-editor-view.fxml");
        controller.setGarden(current_garden);
    }

    @FXML
    private void onBack(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(actionEvent, "garden-list-view.fxml");
    }
}
