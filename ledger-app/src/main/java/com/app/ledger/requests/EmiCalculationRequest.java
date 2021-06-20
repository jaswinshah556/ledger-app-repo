package com.app.ledger.requests;

import com.app.ledger.requests.loanprodcuts.LoanProduct;
import lombok.Data;

/**
 * @author jaswin.shah
 * @version $Id: EmiCalculationRequest.java, v 0.1 2021-06-21 10:23 AM jaswin.shah Exp $$
 */
@Data
public class EmiCalculationRequest {

    private LoanProduct loanProduct;

    private Double loanPrinciple;

    private Integer repaymentMonths;

}
