package com.geraj.assignment.dao;

import com.geraj.assignment.model.Garden;
import com.geraj.assignment.model.GardenPlot;

import java.util.List;

public interface IGardenPlotDAO {
    void createGardenPlot(GardenPlot gardenPlot, Garden garden);

    List<GardenPlot> getGardenPlots(Garden garden);
}
