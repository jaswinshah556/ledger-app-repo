package com.app.ledger.repository;

import com.app.ledger.domainmodels.GrantedLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jaswin.shah
 * @version $Id: GrantedLoanRepository.java, v 0.1 2021-06-21 09:21 AM jaswin.shah Exp $$
 */
@Repository
public interface GrantedLoanRepository  extends JpaRepository<GrantedLoan, Long> {
}
