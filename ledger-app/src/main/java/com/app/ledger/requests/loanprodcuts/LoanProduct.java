package com.app.ledger.requests.loanprodcuts;


import lombok.Data;

/**
 * @author jaswin.shah
 * @version $Id: LoanProduct.java, v 0.1 2021-06-21 10:21 AM jaswin.shah Exp $$
 */
@Data
public class LoanProduct {

    Double rateOfInterest;

    Integer minDurationMonths;

    Integer maxDurationMonths;
}
