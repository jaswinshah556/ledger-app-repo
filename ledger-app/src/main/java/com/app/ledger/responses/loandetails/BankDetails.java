package com.app.ledger.responses.loandetails;

import lombok.Data;

/**
 * @author jaswin.shah
 * @version $Id: BankDetails.java, v 0.1 2021-06-21 11:49 AM jaswin.shah Exp $$
 */
@Data
public class BankDetails {

    private String bankName;

    private String bankCode;

    private String bankBranch;

    private String bankCity;

    private String pinCode;
}
