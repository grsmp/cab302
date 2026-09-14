package com.geraj.assignment.model;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple model class representing a garden with a name, location, temperature, precipitation, atmospheric humidity, owner and garden plots.
 */
public class Garden {
    private String name;
    private String location;
    private Double temperature;
    private Double precipitation;
    private Integer atmosphericHumidity;
    private Account owner;
    private ArrayList<GardenPlot> gardenPlots;

    /**
     * Constructs a new garden with the specified name, location, temperature, precipitation, atmospheric humidity and owner.
     * @param name the name of the garden
     * @param location the location of the garden
     * @param temperature the temperature of the garden
     * @param precipitation the precipitation of the garden
     * @param atmosphericHumidity the atmospheric humidity of the garden
     * @param owner the owner of the garden
     */
    public Garden(String name,
                  String location,
                  Double temperature,
                  Double precipitation,
                  Integer atmosphericHumidity,
                  Account owner) {
        this.name = Objects.requireNonNull(name, "Garden name cannot be null");
        this.location = Objects.requireNonNull(location, "Garden location cannot be null");
        this.temperature = temperature;
        this.precipitation = precipitation;
        this.atmosphericHumidity = atmosphericHumidity;
        this.owner = owner;
        this.gardenPlots = new ArrayList<>();
    }

    /**
     * Gets the name of the garden.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the garden.
     * @param name the new name of the garden
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the location of the garden.
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the garden.
     * @param location the new location of the garden
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the temperature of the garden.
     * @return the temperature
     */
    public Double getTemperature() {
        return temperature;
    }

    /**
     * Sets the temperature of the garden.
     * @param temperature the new temperature of the garden
     */
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    /**
     * Gets the precipitation of the garden.
     * @return the precipitation
     */
    public Double getPrecipitation() {
        return precipitation;
    }

    /**
     * Sets the precipitation of the garden.
     * @param precipitation the new precipitation of the garden
     */
    public void setPrecipitation(double precipitation) {
        this.precipitation = precipitation;
    }

    /**
     * Gets the atmospheric humidity of the garden.
     * @return the atmospheric humidity
     */
    public Integer getAtmosphericHumidity() {
        return atmosphericHumidity;
    }

    /**
     * Sets the atmospheric humidity of the garden.
     * @param atmosphericHumidity the new atmospheric humidity of the garden
     */
    public void setAtmosphericHumidity(int atmosphericHumidity) {
        this.atmosphericHumidity = atmosphericHumidity;
    }

    /**
     * Gets the owner of the garden.
     * @return the owner
     */
    public Account getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the garden.
     * @param owner the new owner of the garden
     */
    public void setOwner(Account owner) {
        this.owner = owner;
    }

    /**
     * Gets the list of garden plots of the garden.
     * @return the list of garden plots
     */
    public ArrayList<GardenPlot> getGardenPlots() {
        return gardenPlots;
    }

    /**
     * Adds a garden plot to the list of garden plots of the garden.
     * @param gardenPlot the garden plot to add
     */
    public void addGardenPlot(GardenPlot gardenPlot) {
        this.gardenPlots.add(gardenPlot);
    }

    /**
     * Removes a garden plot from the list of garden plots of the garden.
     * @param gardenPlot the garden plot to remove
     */
    public void removeGardenPlot(GardenPlot gardenPlot) {
        this.gardenPlots.remove(gardenPlot);
    }

    /**
     * Returns the string representation of the garden.
     * @return formatted string with name and location
     */
    @Override
    public String toString() {
        return getName() + " - " + getLocation();
    }

    /**
     * Gets a description of the garden as a string.
     * @return the description
     */
    public String getDescription() {
        return String.format("Garden: %s located in %s.\nConditions:\n\t- Temp: %s\n\t- Precip: %s\n\t- Humidity: %s\n\t- Owner: %s\n\t- Total Plots: %d",
                name,
                location,
                temperature != null ? temperature + "°" : "N/A",
                precipitation != null ? precipitation + "mm" : "N/A",
                atmosphericHumidity != null ? atmosphericHumidity + "%" : "N/A",
                owner != null ? owner.getName() : "Unknown",
                gardenPlots != null ? gardenPlots.size() : 0);
    }
}
