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
    public enum userStatus {
        NO_ACCOUNT,
        NO_GARDEN,
        EMPTY_GARDEN,
        VALID_GARDEN
    }

    @FXML
    private Label gardenNameLabel;
    private String gardenTitle;
    public userStatus status;

    private Garden user_garden;
    private Account user_account;

    @FXML
    public void initialize() {
        status = determineUserStatus();
        user_garden = getUserGarden();
        user_account = getUserAccount();
        displayGarden();
    }

    public boolean isOwner(Account account, Garden garden) {
        return garden.getOwner() == account;
    }

    @FXML
    public void onEdit(ActionEvent actionEvent) {
        if (isOwner(user_account, user_garden)) {
            GardenEditorController controller = SceneSwitcher.switchScene(actionEvent, "garden-editor-view.fxml");
            controller.setGarden(user_garden);
        }
    }

    private Account getUserAccount() {
        AccountSession session = AccountSession.getInstance();
        return session.getAccount();
    }

    private Garden getUserGarden() {
        AccountSession session = AccountSession.getInstance();
        if (session == null) {
            gardenTitle = "No signed-in account is available.";
            return null;
        }
        Account account = session.getAccount();
        Garden garden = account.getGarden();
        if (garden == null) {
            gardenTitle = "No garden found.";
            return null;
        } else {
            gardenTitle = garden.getName();
            return garden;
        }
    }

    public userStatus determineUserStatus() {
        AccountSession session = AccountSession.getInstance();
        if (session == null) {
            return userStatus.NO_ACCOUNT;
        }
        Account account = session.getAccount();
        Garden garden = account.getGarden();
        if (garden == null) {
            return userStatus.NO_GARDEN;
        } else if (garden.getGardenPlots().isEmpty()) {
            return userStatus.EMPTY_GARDEN;
        } else {
            return userStatus.VALID_GARDEN;
        }
    }

    @FXML
    private FlowPane gardenContainer;

    private void displayGarden() {
        gardenContainer.getChildren().clear();

        gardenNameLabel.setText(gardenTitle);

        if (status != userStatus.VALID_GARDEN) {
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
