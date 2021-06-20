package com.app.ledger.service;

import com.app.ledger.requests.EmiCalculationRequest;
import com.app.ledger.responses.EmiCalculationResponse;


/**
 * @author jaswin.shah
 * @version $Id: EmiCalculationService.java, v 0.1 2021-06-21 08:14 AM jaswin.shah Exp $$
 */
public interface EmiCalculationService {

    EmiCalculationResponse calculateEmi(EmiCalculationRequest emiCalculationRequest);

}
