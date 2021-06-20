package com.app.ledger.responses;

import lombok.Data;

/**
 * @author jaswin.shah
 * @version $Id: BankBranchViewResponse.java, v 0.1 2021-06-21 11:49 AM jaswin.shah Exp $$
 */
@Data
public class BankBranchViewResponse {

    private String branchName;

    private String branchCity;

    private String pinCode;

}
