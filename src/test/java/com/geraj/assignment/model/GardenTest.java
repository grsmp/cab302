package com.geraj.assignment.model;

import com.geraj.assignment.model.Account;
import com.geraj.assignment.model.Garden;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class GardenTest {
    private Garden garden;
    private Account owner;

    @BeforeEach
    public void setUp() {
        owner = new Account("Name", "email@example.com", "First", "Last", "0123456789","hash_string");
        garden = new Garden(
                "Name",
                "Location",
                21.6,
                1.149,
                43,
                owner
        );
    }

    @Test
    public void testGetName() {
        assertEquals("Name", garden.getName());
    }

    @Test
    public void testSetName() {
        garden.setName("NewName");
        assertEquals("NewName", garden.getName());
    }

    @Test
    public void testGetLocation() {
        assertEquals("Location", garden.getLocation());
    }

    @Test
    public void testSetLocation() {
        garden.setLocation("NewLocation");
        assertEquals("NewLocation", garden.getLocation());
    }

    @Test
    public void testGetTemperature() {
        assertEquals(21.6, garden.getTemperature());
    }

    @Test
    public void testSetTemperature() {
        garden.setTemperature(25.0);
        assertEquals(25.0, garden.getTemperature());
    }

    @Test
    public void testGetPrecipitation() {
        assertEquals(1.149, garden.getPrecipitation());
    }

    @Test
    public void testSetPrecipitation() {
        garden.setPrecipitation(2.0);
        assertEquals(2.0, garden.getPrecipitation());
    }

    @Test
    public void testGetAtmosphericHumidity() {
        assertEquals(43, garden.getAtmosphericHumidity());
    }

    @Test
    public void testSetAtmosphericHumidity() {
        garden.setAtmosphericHumidity(2);
        assertEquals(2, garden.getAtmosphericHumidity());
    }

    @Test
    public void testGetOwner() {
        assertSame(owner, garden.getOwner());
    }

    @Test
    public void testSetOwner() {
        Account newOwner = new Account("NewOwner", "newOwner@example.com", "NewFirst", "NewLast", "0123456789","new_hash_string");
        garden.setOwner(newOwner);
        assertSame(newOwner, garden.getOwner());
    }

    @Test
    public void testToString() {
        assertEquals("Name - Location", garden.toString());
    }

    @Test
    public void testToStringAfterUpdatingName() {
        garden.setName("NewName");
        assertEquals("NewName - Location", garden.toString());
    }

    @Test
    public void testToStringAfterUpdatingLocation() {
        garden.setLocation("NewLocation");
        assertEquals("Name - NewLocation", garden.toString());
    }

    @Test
    public void testToStringAfterUpdatingNameAndLocation() {
        garden.setName("NewName");
        garden.setLocation("NewLocation");
        assertEquals("NewName - NewLocation", garden.toString());
    }

    @Test
    public void testGetGardenPlots() {
        assertEquals(new ArrayList<GardenPlot>(), garden.getGardenPlots());
    }

    @Test
    public void testAddGardenPlot() {
        GardenPlot gardenPlot = new GardenPlot(12, 8, 7, 5, 5, 5, 5, 2, 1);
        garden.addGardenPlot(gardenPlot);
        assertEquals(new ArrayList<GardenPlot>(List.of(gardenPlot)), garden.getGardenPlots());
    }

    @Test
    public void testRemoveGardenPlot() {
        GardenPlot gardenPlot = new GardenPlot(12, 8, 7, 5, 5, 5, 5, 2, 1);
        garden.addGardenPlot(gardenPlot);
        garden.removeGardenPlot(gardenPlot);
        assertEquals(new ArrayList<GardenPlot>(), garden.getGardenPlots());
    }
}
