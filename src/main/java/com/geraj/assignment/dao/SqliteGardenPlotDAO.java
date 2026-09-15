package com.geraj.assignment.dao;

import com.geraj.assignment.model.Garden;
import com.geraj.assignment.model.GardenPlot;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
                ph REAL NOT NULL,
                light INTEGER NOT NULL,
                nutriments INTEGER NOT NULL,
                salinity INTEGER NOT NULL,
                texture INTEGER NOT NULL,
                depth REAL NOT NULL,
                soilHumidity INTEGER NOT NULL,
                FOREIGN KEY (garden_id) REFERENCES gardens(id)
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
        String query = """
            INSERT INTO gardenPlots (width, length, ph, light, nutriments, salinity, texture, depth, soilHumidity, garden_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, gardenPlot.getWidth());
            statement.setDouble(2, gardenPlot.getLength());
            statement.setDouble(3, gardenPlot.getPh());
            statement.setInt(4, gardenPlot.getLight());
            statement.setInt(5, gardenPlot.getNutriments());
            statement.setInt(6, gardenPlot.getSalinity());
            statement.setInt(7, gardenPlot.getTexture());
            statement.setDouble(8, gardenPlot.getSoilHumidity());
//            statement.setInt(9, garden.getId());

            if (garden.getOwner() != null) {
                statement.setInt(6, garden.getOwner().getId());
            } else {
                statement.setNull(6, java.sql.Types.INTEGER);
            }

            statement.executeUpdate();

//            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                    garden.setId(generatedKeys.getInt(1));
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<GardenPlot> getGardenPlots(Garden garden) {
        return List.of();
    }
}
