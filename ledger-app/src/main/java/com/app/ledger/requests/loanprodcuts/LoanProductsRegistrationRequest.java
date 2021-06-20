package com.app.ledger.requests.loanprodcuts;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jaswin.shah
 * @version $Id: LoanProductsRegistrationRequest.java, v 0.1 2021-06-21 10:18 AM jaswin.shah Exp $$
 */
@Data
public class LoanProductsRegistrationRequest {

    private List<LoanProduct> products = new ArrayList<>();

    private String bankName;

    private String bankCode;

}
