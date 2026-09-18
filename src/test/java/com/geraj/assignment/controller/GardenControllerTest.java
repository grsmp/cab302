package com.geraj.assignment.controller;

import com.geraj.assignment.AccountSession;
import com.geraj.assignment.model.Account;
import com.geraj.assignment.model.Garden;
import com.geraj.assignment.model.GardenPlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.geraj.assignment.controller.GardenController.userStatus.*;
import static org.junit.jupiter.api.Assertions.*;

public class GardenControllerTest {
    private Account account;
    private Garden garden;
    private GardenPlot plot;
    private GardenController controller;

    @BeforeEach
    public void setUp() {
        AccountSession.logout();
        account = new Account("john", "john@webmail.com", "John", "Doe", "0123456789", "hash", null);
        garden = new Garden("Brisbane Garden", "4000", 24.0, 1.1, 40, account);
        plot = new GardenPlot(10,20,7.0,5,5,5,5,10.0,50);
        AccountSession.startSession(account);

        controller = new GardenController();
    }

    @Test
    public void statusShouldStateNoAccountIfNotLoggedIn() {
        AccountSession.logout();
        assertEquals(NO_ACCOUNT, controller.determineUserStatus());
    }

    @Test
    public void statusShouldStateNoGardenIfNoGarden() {
        assertEquals(NO_GARDEN, controller.determineUserStatus());
    }

    @Test
    public void statusShouldStateEmptyGardenIfGardenIsEmpty() {
        account.setGarden(garden);
        assertEquals(EMPTY_GARDEN, controller.determineUserStatus());
    }

    @Test
    public void statusShouldStateValidGardenIfUserHasGarden() {
        garden.addGardenPlot(plot);
        account.setGarden(garden);
        assertEquals(VALID_GARDEN, controller.determineUserStatus());
    }

    @Test
    public void confirmedGardenOwnership() {
        assertTrue(controller.isOwner(account, garden));
    }

    @Test
    public void deniedGardenOwnership() {
        garden.setOwner(null);
        assertFalse(controller.isOwner(account, garden));
    }
}
