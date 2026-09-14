package com.geraj.assignment.controller;

import com.geraj.assignment.SceneSwitcher;
import com.geraj.assignment.dao.IAccountDAO;
import com.geraj.assignment.dao.IGardenDAO;
import com.geraj.assignment.dao.SqliteAccountDAO;
import com.geraj.assignment.dao.SqliteGardenDAO;
import com.geraj.assignment.model.Account;
import com.geraj.assignment.model.Garden;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class GardenListController {
    private final IGardenDAO gardenDAO = new SqliteGardenDAO();

    @FXML private TextField gardenNameTextField;
    @FXML private TextField gardenLocationTextField;
    @FXML private ListView<Garden> gardenListView;

    private final ObservableList<Garden> gardensObservableList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        ArrayList<Garden> searchResultGardens = gardenDAO.findGardens(null, null);
        gardensObservableList.setAll(searchResultGardens);
        gardenListView.setItems(gardensObservableList);

        gardenListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && gardenListView.getSelectionModel().getSelectedItem() != null) {
                onClickGarden(new ActionEvent(gardenListView, null));
            }
        });
    }

    @FXML
    private void onSearch(ActionEvent actionEvent) {
        String nameToSearch = gardenNameTextField.getText();
        String locationToSearch = gardenLocationTextField.getText();

        ArrayList<Garden> searchResultGardens = gardenDAO.findGardens(nameToSearch, locationToSearch);

        gardensObservableList.setAll(searchResultGardens);
    }

    @FXML
    private void onClickGarden(ActionEvent actionEvent) {
        Garden selectedGarden = gardenListView.getSelectionModel().getSelectedItem();

        if (selectedGarden != null) {
            GardenInfoContoller controller = SceneSwitcher.switchScene(actionEvent, "garden-info-view.fxml");
            if (controller != null) {
                controller.setInfo(selectedGarden);
            }
        }
    }

    @FXML
    private void onBack(ActionEvent actionEvent) {
        SceneSwitcher.switchScene(actionEvent, "main-view.fxml");
    }
}
