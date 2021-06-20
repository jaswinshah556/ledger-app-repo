package com.app.ledger.requests;

import lombok.Data;

/**
 * @author jaswin.shah
 * @version $Id: LoanAvailRequest.java, v 0.1 2021-06-21 09:18 AM jaswin.shah Exp $$
 */
@Data
public class LoanAvailRequest {

    Long loanProductId;

    Long bankBranchId;

    Long customerId;

    Double principal;

    Integer tenureInMonths;

}
