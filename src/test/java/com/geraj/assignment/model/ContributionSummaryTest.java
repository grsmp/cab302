package com.geraj.assignment.model;

import com.geraj.assignment.dao.IContributionDAO;
import com.geraj.assignment.dao.StubContributionDAO;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContributionSummaryTest {
    @Test
    void contributionSummaryShouldContainAllProfileTotals() {
        ContributionSummary summary = new ContributionSummary(14, 37, 9);

        assertAll(
                () -> assertEquals(14, summary.getCropsContributed()),
                () -> assertEquals(37, summary.getWateringShiftsDone()),
                () -> assertEquals(9, summary.getHarvestsLogged())
        );
    }

    @Test
    void contributionSummaryFieldsShouldBeImmutable() {
        Field[] fields = ContributionSummary.class.getDeclaredFields();

        assertTrue(Modifier.isFinal(ContributionSummary.class.getModifiers()));
        assertTrue(fields.length > 0);
        assertTrue(
                java.util.Arrays.stream(fields)
                        .allMatch(field -> Modifier.isPrivate(field.getModifiers())
                                && Modifier.isFinal(field.getModifiers()))
        );
    }

    @Test
    void stubContributionDAOShouldReturnZeroTotalsForAnAccount() {
        Account account = new Account(
                "harresh",
                "harresh@example.com",
                "Harresh",
                "Patel",
                "0412345678",
                "stored-hash"
        );
        IContributionDAO contributionDAO = new StubContributionDAO();

        ContributionSummary summary = contributionDAO.getContributionSummary(account);

        assertAll(
                () -> assertEquals(0, summary.getCropsContributed()),
                () -> assertEquals(0, summary.getWateringShiftsDone()),
                () -> assertEquals(0, summary.getHarvestsLogged())
        );
    }
}
