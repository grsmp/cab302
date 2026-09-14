package com.geraj.assignment.model;

/**
 * A simple model class representing a plant with an id, common name, growth months, maximum ph, minimum ph, light and minimum depth.
 */
public class Plant {
    private int id;
    private String commonName;
    private int growthMonths;
    private double phMax;
    private double phMin;
    private int light;
    private double minDepth;

    /**
     * Constructs a new plant with the specified common name, growth months, maximum ph, minimum ph, light and minimum depth.
     * @param commonName the common name of the plant
     * @param growthMonths the growth months of the plant
     * @param phMax the maximum ph of the plant
     * @param phMin the minimum ph of the plant
     * @param light the desired light of the plant
     * @param minDepth the minimum depth of the plant
     */
    public Plant(String commonName,
                 int growthMonths,
                 double phMax,
                 double phMin,
                 int light,
                 double minDepth) {
        this.commonName = commonName;
        this.growthMonths = growthMonths;
        this.phMax = phMax;
        this.phMin = phMin;
        this.light = light;
        this.minDepth = minDepth;
    }

    /**
     * Gets the common name of the plant.
     * @return the common name
     */
    public String getCommonName() {
        return commonName;
    }

    /**
     * Sets the common name of the plant.
     * @param commonName the new common name of the plant
     */
    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    /**
     * Gets the growth months of the plant.
     * @return the growth months
     */
    public int getGrowthMonths() {
        return growthMonths;
    }

    /**
     * Sets the growth months of the plant.
     * @param growthMonths the new growth months of the plant
     */
    public void setGrowthMonths(int growthMonths) {
        this.growthMonths = growthMonths;
    }

    /**
     * Gets the maximum ph of the plant.
     * @return the maximum ph
     */
    public double getPhMax() {
        return phMax;
    }

    /**
     * Sets the maximum ph of the plant.
     * @param phMax the new maximum ph of the plant
     */
    public void setPhMax(double phMax) {
        this.phMax = phMax;
    }

    /**
     * Gets the minimum ph of the plant.
     * @return the minimum ph
     */
    public double getPhMin() {
        return phMin;
    }

    /**
     * Sets the minimum ph of the plant.
     * @param phMin the new minimum ph of the plant
     */
    public void setPhMin(double phMin) {
        this.phMin = phMin;
    }

    /**
     * Gets the desired light of the plant.
     * @return the desired light
     */
    public int getLight() {
        return light;
    }

    /**
     * Sets the desired light of the plant.
     * @param light the new desired light of the plant
     */
    public void setLight(int light) {
        this.light = light;
    }

    /**
     * Gets the minimum depth of the plant.
     * @return the minimum depth
     */
    public double getMinDepth() {
        return minDepth;
    }

    /**
     * Sets the minimum depth of the plant.
     * @param minDepth the new minimum depth of the plant
     */
    public void setMinDepth(double minDepth) {
        this.minDepth = minDepth;
    }
}
