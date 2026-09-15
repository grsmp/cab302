package com.geraj.assignment.dao;

import com.geraj.assignment.model.Garden;
import com.geraj.assignment.model.GardenPlot;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class SqliteGardenPlotDAO implements IGardenPlotDAO {
    private final Connection connection;

    public SqliteGardenPlotDAO() {
        connection = SqliteConnection.getInstance();
        createTable();
    }

    private void createTable() {
        String query = """
            CREATE TABLE IF NOT EXISTS gardenPlots (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                width REAL NOT NULL,
                length REAL NOT NULL,
                ph INTEGER NOT NULL,
                light INTEGER NOT NULL,
                nutriments INTEGER NOT NULL,
                salinity INTEGER NOT NULL,
                texture INTEGER NOT NULL,
                depth REAL NOT NULL,
                soilHumidity INTEGER NOT NULL,
                FOREIGN KEY (owner_id) REFERENCES accounts(id)
            );
            """;

        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createGardenPlot(GardenPlot gardenPlot, Garden garden) {

    }

    @Override
    public List<GardenPlot> getGardenPlots(Garden garden) {
        return List.of();
    }
}
