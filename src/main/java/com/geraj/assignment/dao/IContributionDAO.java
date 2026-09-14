package com.geraj.assignment.dao;

import com.geraj.assignment.model.Account;
import com.geraj.assignment.model.ContributionSummary;

/**
 * Defines persistence operations for account contribution totals.
 */
public interface IContributionDAO {
    /**
     * Returns the contribution totals for an account.
     *
     * @param account the account whose contributions are requested
     * @return the account's contribution summary
     */
    ContributionSummary getContributionSummary(Account account);
}
