package com.app.ledger.service.impl;

import com.app.ledger.constants.MessageConstants;
import com.app.ledger.requests.EmiCalculationRequest;
import com.app.ledger.responses.EmiCalculationResponse;
import com.app.ledger.service.EmiCalculationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author jaswin.shah
 * @version $Id: EmiCalculationServiceImpl.java, v 0.1 2021-06-21 09:24 AM jaswin.shah Exp $$
 */
@Service
@Slf4j
public class EmiCalculationServiceImpl implements EmiCalculationService {




    @Override
    public EmiCalculationResponse calculateEmi(EmiCalculationRequest emiCalculationRequest) {

        Assert.notNull(emiCalculationRequest,MessageConstants.INVALID_REQUEST);

        Assert.isTrue(emiCalculationRequest.getLoanPrinciple() !=0 ,MessageConstants.PRINCIPAL_CAN_NOT_BE_ZERO);

        Assert.notNull(emiCalculationRequest.getLoanProduct(),MessageConstants.LOAN_PRODUCT_INVALID);

        Double principal = emiCalculationRequest.getLoanPrinciple();

        Integer durationInMonths = emiCalculationRequest.getRepaymentMonths();

        Double rate = emiCalculationRequest.getLoanProduct().getRateOfInterest();

        Assert.isTrue(durationInMonths >= emiCalculationRequest.getLoanProduct().getMinDurationMonths()
        && durationInMonths <= emiCalculationRequest.getLoanProduct().getMaxDurationMonths(), MessageConstants.DURATION_NOT_SUPPORTED_BY_LOAN_PRODUCT);

        Double emi =  calculateEmi(principal,rate,durationInMonths);

        log.info("Emi is {}",emi);
        EmiCalculationResponse emiCalculationResponse = new EmiCalculationResponse();
        emiCalculationResponse.setEmi(emi);
        emiCalculationResponse.setPrincipal(emiCalculationRequest.getLoanPrinciple());
        emiCalculationResponse.setRate(emiCalculationRequest.getLoanProduct().getRateOfInterest());
        emiCalculationResponse.setTenureInMonths(emiCalculationRequest.getRepaymentMonths());
        emiCalculationResponse.setTotalRepayableAmount(emi*emiCalculationRequest.getRepaymentMonths());
        emiCalculationResponse.setInterestComponent(emiCalculationResponse.getTotalRepayableAmount()-emiCalculationResponse.getPrincipal());
        return emiCalculationResponse;
    }


    private double calculateEmi(double principal, double rate, Integer months) {
        rate/=1200;
        return (principal*rate*Math.pow(1+rate,months))/(Math.pow(1+rate,months)-1);
    }
}
