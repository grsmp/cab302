package com.geraj.assignment.model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlantTest {
    private Plant plant;

    @BeforeEach
    public void setupPlant() {
        plant = new Plant(
                "Potato",
                4,
                6,
                5,
                7000,
                0.15
        );
    }

    @Test
    public void testGetCommonName() {
        assertEquals("Potato", plant.getCommonName());
    }

    @Test
    public void testSetCommonName() {
        plant.setCommonName("Carrot");
        assertEquals("Carrot", plant.getCommonName());
    }

    @Test
    public void testGetGrowMonths() {
        assertEquals(4, plant.getGrowthMonths());
    }

    @Test
    public void testSetGrowMonths() {
        plant.setGrowthMonths(6);
        assertEquals(6, plant.getGrowthMonths());
    }

    @Test
    public void testGetPhMax() {
        assertEquals(6, plant.getPhMax());
    }

    @Test
    public void testSetPhMax() {
        plant.setPhMax(8);
        assertEquals(8, plant.getPhMax());
    }

    @Test
    public void testGetPhMin() {
        assertEquals(5, plant.getPhMin());
    }

    @Test
    public void testSetPhMin() {
        plant.setPhMin(1);
        assertEquals(1, plant.getPhMin());
    }

    @Test
    public void testGetLight() {
        assertEquals(7000, plant.getLight());
    }

    @Test
    public void testSetLight() {
        plant.setLight(1000);
        assertEquals(1000, plant.getLight());
    }

    @Test
    public void testGetMinDepth() {
        assertEquals(0.15, plant.getMinDepth());
    }

    @Test
    public void testSetMinDepth() {
        plant.setMinDepth(0.5);
        assertEquals(0.5, plant.getMinDepth());
    }

}
