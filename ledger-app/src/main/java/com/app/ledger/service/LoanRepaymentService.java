package com.app.ledger.service;

import com.app.ledger.requests.repayment.LoanRepaymentRequest;


/**
 * @author jaswin.shah
 * @version $Id: LoanRepaymentService.java, v 0.1 2021-06-21 08:54 AM jaswin.shah Exp $$
 */
public interface LoanRepaymentService {

    void repayLoan(LoanRepaymentRequest loanRepaymentRequest);
}
