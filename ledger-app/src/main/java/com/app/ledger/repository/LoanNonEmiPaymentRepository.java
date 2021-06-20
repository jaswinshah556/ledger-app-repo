package com.app.ledger.repository;

import com.app.ledger.domainmodels.LoanNonEmiPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jaswin.shah
 * @version $Id: LoanNonEmiPaymentRepository.java, v 0.1 2021-06-21 08:18 AM jaswin.shah Exp $$
 */
@Repository
public interface LoanNonEmiPaymentRepository  extends JpaRepository<LoanNonEmiPayment, Long> {
}
