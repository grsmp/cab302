package com.geraj.assignment.controller;

import com.geraj.assignment.SceneSwitcher;
import com.geraj.assignment.model.Garden;
import com.geraj.assignment.model.GardenPlot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class GardenEditorController {
    private Garden current_garden;

    @FXML private ListView<GardenPlot> plotListView;

    public void setGarden(Garden garden) {
        current_garden = garden;
        plotListView.getItems().addAll(current_garden.getGardenPlots());

        plotListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && plotListView.getSelectionModel().getSelectedItem() != null) {
                onClickGardenPlot(new ActionEvent(plotListView, null));
            }
        });
    }

    @FXML
    private void onCreateNewGardenPlot(ActionEvent actionEvent) {
        CreateGardenPlotController createGardenPlotController = SceneSwitcher.openModalAndWait(
                actionEvent,
                "create-garden-plot-view.fxml",
                "Create New Garden Plot"
        );


        if (createGardenPlotController != null) {
            GardenPlot newPlot = createGardenPlotController.getNewPlot();

            if (newPlot != null) {
                current_garden.addGardenPlot(newPlot);

                plotListView.getItems().add(newPlot);
            }
        }
    }

    @FXML
    private void onClickGardenPlot(ActionEvent actionEvent) {
        GardenPlot selectedGarden = plotListView.getSelectionModel().getSelectedItem();

    }
}
