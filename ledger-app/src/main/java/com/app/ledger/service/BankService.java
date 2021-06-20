package com.app.ledger.service;


import com.app.ledger.requests.BankBranchViewRequest;
import com.app.ledger.requests.BankRegistrationRequest;
import com.app.ledger.responses.BankBranchViewResponse;
import com.app.ledger.responses.BankViewResponse;

import java.util.List;

/**
 * @author jaswin.shah
 * @version $Id: BankService.java, v 0.1 2021-06-21 08:38 AM jaswin.shah Exp $$
 */
public interface BankService {

    void registerBank(BankRegistrationRequest  bankRegistrationRequest);

    List<BankViewResponse> fetchAllBanks();

    List<BankBranchViewResponse> fetchBranches(BankBranchViewRequest bankBranchViewRequest);

}
