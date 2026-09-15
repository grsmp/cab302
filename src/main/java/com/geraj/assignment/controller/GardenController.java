package com.geraj.assignment.controller;

import com.geraj.assignment.AccountSession;
import com.geraj.assignment.SceneSwitcher;
import com.geraj.assignment.model.Account;
import com.geraj.assignment.model.Garden;
import com.geraj.assignment.model.GardenPlot;
import com.geraj.assignment.model.Plant;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class GardenController {
    @FXML
    private Label gardenNameLabel;

    private Garden user_garden;

    @FXML
    public void initialize() {
        user_garden = getUserGarden();
        if (user_garden != null) {
            gardenNameLabel.setText(user_garden.getName());
            displayGarden();
        } else {
            gardenNameLabel.setText("No garden found.");
        }
    }

    @FXML
    private void onEdit(ActionEvent actionEvent) {
        GardenEditorController controller = SceneSwitcher.switchScene(actionEvent, "garden-editor-view.fxml");
        controller.setGarden(user_garden);
    }

    private Garden getUserGarden() {
        AccountSession session = AccountSession.getInstance();
        if (session == null) {
            gardenNameLabel.setText("No signed-in account is available.");
            return null;
        }
        Account account = session.getAccount();
        return account.getGarden();
    }

    @FXML
    private FlowPane gardenContainer;

    private void displayGarden() {
        gardenContainer.getChildren().clear();

        if (user_garden == null || user_garden.getGardenPlots() == null) {
            return;
        }

        for (GardenPlot plot : user_garden.getGardenPlots()) {
            VBox plotBox = createPlot(plot);
            gardenContainer.getChildren().add(plotBox);
        }
    }

    private VBox createPlot(GardenPlot plot) {
        VBox plotBox = new VBox(8);
        plotBox.setAlignment(Pos.CENTER);
        plotBox.setPadding(new Insets(12));

        plotBox.setPrefWidth(180);
        plotBox.setPrefHeight(150);

        plotBox.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-border-color: #000000;" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 5;" +
                "-fx-background-radius: 5;"

        );

        VBox plantNames = new VBox(3);
        plantNames.setAlignment(Pos.CENTER);

        for (Plant plant : plot.getPlants()) {
            Label plantLabel = new Label(plant.getCommonName());
            plantLabel.setStyle("-fx-font-size: 14px;");
            plantNames.getChildren().add(plantLabel);
        }

        plotBox.getChildren().addAll(plantNames);

        return plotBox;
    }
}
