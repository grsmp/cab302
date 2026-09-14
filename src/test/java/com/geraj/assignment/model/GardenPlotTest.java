package com.geraj.assignment.model;

import com.geraj.assignment.model.Account;
import com.geraj.assignment.model.GardenPlot;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class GardenPlotTest {
    private GardenPlot gardenPlot;

    @BeforeEach
    public void setUp() {
        gardenPlot = new GardenPlot(
                10.9,
                21.8,
                7.1,
                5,
                5,
                5,
                5,
                10.11,
                50
        );
    }

    @Test
    public void testGetWidth() {
        assertEquals(10.9, gardenPlot.getWidth());
    }

    @Test
    public void testSetWidth() {
        gardenPlot.setWidth(21.8);
        assertEquals(21.8, gardenPlot.getWidth());
    }

    @Test
    public void testGetLength() {
        assertEquals(21.8, gardenPlot.getLength());
    }

    @Test
    public void testSetLength() {
        gardenPlot.setLength(10.9);
        assertEquals(10.9, gardenPlot.getLength());
    }

    @Test
    public void testGetPh() {
        assertEquals(7.1, gardenPlot.getPh());
    }

    @Test
    public void testSetPh() {
        gardenPlot.setPh(1.7);
        assertEquals(1.7, gardenPlot.getPh());
    }

    @Test
    public void testGetLight() {
        assertEquals(5, gardenPlot.getLight());
    }

    @Test
    public void testSetLight() {
        gardenPlot.setLight(6);
        assertEquals(6, gardenPlot.getLight());
    }

    @Test
    public void testGetNutriments() {
        assertEquals(5, gardenPlot.getNutriments());
    }

    @Test
    public void testSetNutriments() {
        gardenPlot.setNutriments(6);
        assertEquals(6, gardenPlot.getNutriments());
    }

    @Test
    public void testGetSalinity() {
        assertEquals(5, gardenPlot.getSalinity());
    }

    @Test
    public void testSetSalinity() {
        gardenPlot.setSalinity(6);
        assertEquals(6, gardenPlot.getSalinity());
    }

    @Test
    public void testGetTexture() {
        assertEquals(5, gardenPlot.getTexture());
    }

    @Test
    public void testSetTexture() {
        gardenPlot.setTexture(6);
        assertEquals(6, gardenPlot.getTexture());
    }

    @Test
    public void testGetDepth() {
        assertEquals(10.11, gardenPlot.getDepth());
    }

    @Test
    public void testSetDepth() {
        gardenPlot.setDepth(6.1);
        assertEquals(6.1, gardenPlot.getDepth());
    }

    @Test
    public void testGetSoilHumidity() {
        assertEquals(50, gardenPlot.getSoilHumidity());
    }

    @Test
    public void testSetSoilHumidity() {
        gardenPlot.setSoilHumidity(40);
        assertEquals(40, gardenPlot.getSoilHumidity());
    }

    @Test
    public void testGetContributors() {
        assertEquals(new ArrayList<Account>(), gardenPlot.getContributors());
    }

    @Test
    public void testAddContributor() {
        Account newContributor = new Account("NewContributor", "newContributor@example.com", "newFirst","newLast", "0987654321", "new_hash_string");
        gardenPlot.addContributor(newContributor);
        assertEquals(new ArrayList<Account>(List.of(newContributor)), gardenPlot.getContributors());
    }

    @Test
    public void testRemoveGardenPlot() {
        Account newContributor = new Account("NewContributor", "newContributor@example.com","newFirst","newLast", "0987654321", "new_hash_string");
        gardenPlot.addContributor(newContributor);
        gardenPlot.removeContributor(newContributor);
        assertEquals(new ArrayList<Account>(), gardenPlot.getContributors());
    }

    @Test
    public void testGetPlants() {
        assertEquals(new ArrayList<Plant>(), gardenPlot.getPlants());
    }

    @Test
    public void testAddPlant() {
        Plant newPlant = new Plant(
                "Potato",
                4,
                6,
                5,
                7000,
                0.15
        );
        gardenPlot.addPlant(newPlant);
        assertEquals(new ArrayList<Plant>(List.of(newPlant)), gardenPlot.getPlants());
    }

    @Test
    public void testRemovePlant() {
        Plant newPlant = new Plant(
                "Potato",
                4,
                6,
                5,
                7000,
                0.15
        );
        gardenPlot.addPlant(newPlant);
        gardenPlot.removePlant(newPlant);
        assertEquals(new ArrayList<Plant>(), gardenPlot.getPlants());
    }
}
