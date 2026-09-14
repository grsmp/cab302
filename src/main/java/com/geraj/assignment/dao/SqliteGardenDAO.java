package com.geraj.assignment.dao;

import com.geraj.assignment.model.Account;
import com.geraj.assignment.model.Garden;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SqliteGardenDAO implements IGardenDAO {
    private final Connection connection;

    public SqliteGardenDAO() {
        connection = SqliteConnection.getInstance();
        createTable();
    }

    private void createTable() {
        String query = """
            CREATE TABLE IF NOT EXISTS gardens (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL UNIQUE,
                location TEXT NOT NULL,
                temperature REAL,
                precipitation REAL,
                atmosphericHumidity REAL,
                owner_id INTEGER,
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
    public void createGarden(Garden garden) {
        String query = """
            INSERT INTO gardens (name, location, temperature, precipitation, atmosphericHumidity, owner_id)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, garden.getName());
            statement.setString(2, garden.getLocation());

            if (garden.getTemperature() != null) {
                statement.setDouble(3, garden.getTemperature());
            } else {
                statement.setNull(3, java.sql.Types.DOUBLE);
            }

            if (garden.getPrecipitation() != null) {
                statement.setDouble(4, garden.getPrecipitation());
            } else {
                statement.setNull(4, java.sql.Types.DOUBLE);
            }

            if (garden.getAtmosphericHumidity() != null) {
                statement.setInt(5, garden.getAtmosphericHumidity());
            } else {
                statement.setNull(5, java.sql.Types.INTEGER);
            }

            if (garden.getOwner() != null) {
                statement.setInt(6, garden.getOwner().getId());
            } else {
                statement.setNull(6, java.sql.Types.VARCHAR);
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

    public ArrayList<Garden> findGardens(String searchName, String searchLocation) {
        ArrayList<Garden> gardens = new ArrayList<>();

        String query = """
            SELECT *
            FROM gardens
            WHERE (? IS NULL OR name LIKE ?)
            AND (? IS NULL OR location LIKE ?)
            """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            if (searchName != null && !searchName.isBlank()) {
                statement.setString(1, searchName);
                statement.setString(2, "%" + searchName + "%");
            } else {
                statement.setNull(1, java.sql.Types.VARCHAR);
                statement.setNull(2, java.sql.Types.VARCHAR);
            }

            if (searchLocation != null && !searchLocation.isBlank()) {
                statement.setString(3, searchLocation);
                statement.setString(4, "%" + searchLocation + "%");
            } else {
                statement.setNull(3, java.sql.Types.VARCHAR);
                statement.setNull(4, java.sql.Types.VARCHAR);
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String location = resultSet.getString("location");

                    Double temperature = resultSet.getDouble("temperature");
                    if (resultSet.wasNull()) temperature = null;

                    Double precipitation = resultSet.getDouble("precipitation");
                    if (resultSet.wasNull()) precipitation = null;

                    Integer atmosphericHumidity = resultSet.getInt("atmosphericHumidity");
                    if (resultSet.wasNull()) atmosphericHumidity = null;

                    int ownerId = resultSet.getInt("owner_id");
                    Account owner = null;

                    if (!resultSet.wasNull()) {
                        IAccountDAO accountDAO = new SqliteAccountDAO();
                        owner = accountDAO.getAccountById(ownerId).orElse(null);
                    }

                    Garden garden = new Garden(name, location, temperature, precipitation, atmosphericHumidity, owner);
                    gardens.add(garden);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gardens;
    }
}
