package com.app.ledger.repository;

import com.app.ledger.domainmodels.LoanRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jaswin.shah
 * @version $Id: LoanRequestRepository.java, v 0.1 2021-06-21 09:43 AM jaswin.shah Exp $$
 */
@Repository
public interface LoanRequestRepository  extends JpaRepository<LoanRequest, Long> {
}
