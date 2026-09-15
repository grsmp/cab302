package com.geraj.assignment.controller;

import com.geraj.assignment.AccountSession;
import com.geraj.assignment.SceneSwitcher;
import com.geraj.assignment.model.Account;
import com.geraj.assignment.model.Garden;
import com.geraj.assignment.model.GardenPlot;
import com.geraj.assignment.model.Plant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class GardenInfoContoller {
    @FXML private Label GardenName;
    @FXML private Label GardenLocation;
    @FXML private TextArea gardenDescription;


    private Garden current_garden;
    private Account user_account;

    public void setInfo(Garden garden, Account account) {
        current_garden = garden;
        user_account = account;
        GardenName.setText(garden.getName());
        GardenLocation.setText(garden.getLocation());
        gardenDescription.setText(garden.getDescription());
    }

    @FXML
    private void onJoin(ActionEvent actionEvent) {
        GardenPlot newPlot = new GardenPlot(
                10, 10, 10, 10, 10,
                10, 10, 10.0, 10
        );
        Plant newPlant = new Plant(
                "Potato", 3, 6, 5, 60, 5.0
        );
        newPlot.addPlant(newPlant);
        current_garden.addGardenPlot(newPlot);
        user_account.setGarden(current_garden);
        SceneSwitcher.switchScene(actionEvent, "garden-view.fxml");
    }

    @FXML
    private void onBack(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(actionEvent, "garden-list-view.fxml");
    }

    @FXML
    private void joinGarden(ActionEvent actionEvent) {
        AccountSession instance = AccountSession.getInstance();
        Account user_account = instance.getAccount();
        user_account.setGarden(current_garden);
    }
}
