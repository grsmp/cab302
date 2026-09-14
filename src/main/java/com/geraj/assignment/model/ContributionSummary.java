package com.geraj.assignment.model;

/**
 * Contains the contribution totals displayed on an account profile.
 */
public final class ContributionSummary {
    private final int cropsContributed;
    private final int wateringShiftsDone;
    private final int harvestsLogged;

    /**
     * Creates a contribution summary.
     *
     * @param cropsContributed the number of crops contributed
     * @param wateringShiftsDone the number of completed watering shifts
     * @param harvestsLogged the number of harvests logged
     */
    public ContributionSummary(
            int cropsContributed,
            int wateringShiftsDone,
            int harvestsLogged
    ) {
        this.cropsContributed = cropsContributed;
        this.wateringShiftsDone = wateringShiftsDone;
        this.harvestsLogged = harvestsLogged;
    }

    /**
     * Returns the number of crops contributed.
     *
     * @return the crops contributed total
     */
    public int getCropsContributed() {
        return cropsContributed;
    }

    /**
     * Returns the number of completed watering shifts.
     *
     * @return the completed watering-shift total
     */
    public int getWateringShiftsDone() {
        return wateringShiftsDone;
    }

    /**
     * Returns the number of harvests logged.
     *
     * @return the harvests logged total
     */
    public int getHarvestsLogged() {
        return harvestsLogged;
    }
}
