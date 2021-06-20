package com.app.ledger.requests.repayment;

import lombok.Data;


/**
 * @author jaswin.shah
 * @version $Id: LoanRepaymentRequest.java, v 0.1 2021-06-21 10:18 AM jaswin.shah Exp $$
 */
@Data
public class LoanRepaymentRequest {

    private long loanId;

    private String type;

    private Double amount;

}
