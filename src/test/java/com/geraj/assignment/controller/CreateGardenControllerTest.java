package com.geraj.assignment.controller;

import com.geraj.assignment.dao.IGardenDAO;
import com.geraj.assignment.model.Account;
import com.geraj.assignment.model.Garden;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests garden creation without opening JavaFX windows
 * or accessing the real database.
 */
public class CreateGardenControllerTest {

    private FakeGardenDAO gardenDAO;
    private CreateGardenController controller;
    private Account owner;

    @BeforeEach
    public void setUp() {
        gardenDAO = new FakeGardenDAO();
        controller = new CreateGardenController(gardenDAO);

        owner = new Account(
                "manny",
                "manny@example.com",
                "Manny",
                "Test",
                "0412345678",
                "test-hash",
                null
        );
    }

    @Test
    public void validDetailsShouldSaveTrimmedGardenForOwner() {
        // Act
        Garden result = controller.createGarden(
                "  Riverside Garden  ",
                "  West End  ",
                owner
        );

        // Assert
        assertEquals("Riverside Garden", result.getName());
        assertEquals("West End", result.getLocation());
        assertSame(owner, result.getOwner());
        assertEquals(1, gardenDAO.saveAttempts);
        assertSame(result, gardenDAO.savedGarden);
    }

    @Test
    public void blankNameShouldBeRejectedWithoutSaving() {
        // Act and assert
        IllegalArgumentException error = assertThrows(
                IllegalArgumentException.class,
                () -> controller.createGarden(" \t ", "West End", owner)
        );

        assertEquals("Enter a garden name.", error.getMessage());
        assertEquals(0, gardenDAO.saveAttempts);
        assertNull(gardenDAO.savedGarden);
    }

    @Test
    public void blankLocationShouldBeRejectedWithoutSaving() {
        // Act and assert
        IllegalArgumentException error = assertThrows(
                IllegalArgumentException.class,
                () -> controller.createGarden(
                        "Riverside Garden", " \t ", owner
                )
        );

        assertEquals("Enter a location.", error.getMessage());
        assertEquals(0, gardenDAO.saveAttempts);
        assertNull(gardenDAO.savedGarden);
    }

    @Test
    public void missingOwnerShouldBeRejectedWithoutSaving() {
        // Act and assert
        IllegalArgumentException error = assertThrows(
                IllegalArgumentException.class,
                () -> controller.createGarden(
                        "Riverside Garden", "West End", null
                )
        );

        assertEquals("Please sign in first.", error.getMessage());
        assertEquals(0, gardenDAO.saveAttempts);
        assertNull(gardenDAO.savedGarden);
    }

    @Test
    public void daoFailureShouldBePropagatedWithoutFalseSuccess() {
        // Arrange
        IllegalStateException databaseError =
                new IllegalStateException("Simulated database failure.");
        gardenDAO.failure = databaseError;

        // Act and assert
        IllegalStateException actualError = assertThrows(
                IllegalStateException.class,
                () -> controller.createGarden(
                        "Riverside Garden", "West End", owner
                )
        );

        assertSame(databaseError, actualError);
        assertEquals(1, gardenDAO.saveAttempts);
        assertNull(gardenDAO.savedGarden);
    }

    /**
     * Records save requests and can simulate a database failure.
     */
    private static final class FakeGardenDAO implements IGardenDAO {

        private int saveAttempts;
        private Garden savedGarden;
        private IllegalStateException failure;

        @Override
        public void createGarden(Garden garden) {
            saveAttempts++;

            if (failure != null) {
                throw failure;
            }

            savedGarden = garden;
        }

        @Override
        public ArrayList<Garden> findGardens(
                String searchName,
                String searchLocation
        ) {
            throw new AssertionError(
                    "Creating a garden should not search for gardens."
            );
        }
    }
}