package com.geraj.assignment.dao;

import com.geraj.assignment.model.Account;
import com.geraj.assignment.model.ContributionSummary;

import java.util.Objects;

/**
 * Supplies empty contribution totals until contribution tables are available.
 */
public class StubContributionDAO implements IContributionDAO {
    /**
     * Returns zero contribution totals for an account.
     *
     * @param account the account whose contributions are requested
     * @return a contribution summary containing zero totals
     */
    @Override
    public ContributionSummary getContributionSummary(Account account) {
        Objects.requireNonNull(account, "Account cannot be null");
        return new ContributionSummary(0, 0, 0);
    }
}
