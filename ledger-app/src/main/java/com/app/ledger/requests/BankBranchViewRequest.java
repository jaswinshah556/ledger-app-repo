package com.app.ledger.requests;

import lombok.Data;


/**
 * @author jaswin.shah
 * @version $Id: BankBranchViewRequest.java, v 0.1 2021-06-21 10:16 AM jaswin.shah Exp $$
 */
@Data
public class BankBranchViewRequest {

    private String bankName;

    private String bankCode;

}
