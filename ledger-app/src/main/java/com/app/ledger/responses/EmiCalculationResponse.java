package com.app.ledger.responses;

import lombok.Data;

/**
 * @author jaswin.shah
 * @version $Id: EmiCalculationResponse.java, v 0.1 2021-06-21 06:49 AM jaswin.shah Exp $$
 */
@Data
public class EmiCalculationResponse {

    Integer tenureInMonths;

    Double principal;

    Double emi;

    Double rate;

    Double totalRepayableAmount;

    Double interestComponent;

}
