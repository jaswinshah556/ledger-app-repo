package com.app.ledger.repository;

import com.app.ledger.constants.enums.LoanEmiPaymentState;
import com.app.ledger.domainmodels.GrantedLoan;
import com.app.ledger.domainmodels.LoanEmiPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


/**
 * @author jaswin.shah
 * @version $Id: LoanEmiPaymentRepository.java, v 0.1 2021-06-21 10:23 AM jaswin.shah Exp $$
 */
@Repository
public interface LoanEmiPaymentRepository  extends JpaRepository<LoanEmiPayment, Long> {

    List<LoanEmiPayment> findByGrantedLoanAndLoanEmiPaymentStateOrderByEmiDate(GrantedLoan grantedLoan, LoanEmiPaymentState loanEmiPaymentState);

}
