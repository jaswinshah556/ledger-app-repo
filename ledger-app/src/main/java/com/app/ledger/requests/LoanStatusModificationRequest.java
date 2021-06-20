package com.app.ledger.requests;

import com.app.ledger.constants.enums.LoanRequestStateEnum;
import lombok.Data;

/**
 * @author jaswin.shah
 * @version $Id: LoanStatusModificationRequest.java, v 0.1 2021-06-21 09:18 AM jaswin.shah Exp $$
 */
@Data
public class LoanStatusModificationRequest {

    private long loanRequestId;

    private LoanRequestStateEnum status;

}
