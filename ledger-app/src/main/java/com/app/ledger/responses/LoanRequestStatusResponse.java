package com.app.ledger.responses;

import com.app.ledger.constants.enums.LoanRequestStateEnum;
import lombok.Data;


/**
 * @author jaswin.shah
 * @version $Id: LoanRequestStatusResponse.java, v 0.1 2021-06-21 12:49 AM jaswin.shah Exp $$
 */
@Data
public class LoanRequestStatusResponse {

    private Long requestId;

    private LoanRequestStateEnum loanStatus;

    private EmiCalculationResponse emiDetails;

}
