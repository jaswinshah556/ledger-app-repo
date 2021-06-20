package com.app.ledger.responses.loandetails;

import com.app.ledger.constants.enums.LoanEmiPaymentState;
import lombok.Data;

import java.util.Date;

/**
 * @author jaswin.shah
 * @version $Id: LoanEmis.java, v 0.1 2021-06-21 11:49 AM jaswin.shah Exp $$
 */
@Data
public class LoanEmis {

    private Date emiDate;

    private Double emiAmount;

    private Double principalComponent;

    private Double interestComponent;

    private Double principalOutstanding;

    private LoanEmiPaymentState loanEmiPaymentState;


}
