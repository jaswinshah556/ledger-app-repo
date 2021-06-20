package com.app.ledger.service;

import com.app.ledger.domainmodels.LoanEmiPayment;
import com.app.ledger.domainmodels.LoanRequest;
import com.app.ledger.requests.GrantedLoanDetailsFetchingRequest;
import com.app.ledger.requests.LoanAvailRequest;
import com.app.ledger.requests.LoanStatusModificationRequest;
import com.app.ledger.requests.loanprodcuts.LoanProduct;
import com.app.ledger.requests.loanprodcuts.LoanProductsRegistrationRequest;
import com.app.ledger.requests.loanprodcuts.LoanProductsViewRequest;
import com.app.ledger.responses.LoanRequestStatusResponse;
import com.app.ledger.responses.loandetails.LoanDetailedResponse;

import java.util.List;
import java.util.Set;

/**
 * @author jaswin.shah
 * @version $Id: LoanService.java, v 0.1 2021-06-21 09:03 AM jaswin.shah Exp $$
 */
public interface LoanService {

    void registerLoanProductsWithBank(LoanProductsRegistrationRequest request);

    List<LoanProduct> showBankLoanProducts(LoanProductsViewRequest request);

    LoanRequestStatusResponse submitLoanRequest(LoanAvailRequest loanRequest);

    void updateLoanRequestStatus(LoanStatusModificationRequest loanStatusModificationRequest);

    LoanDetailedResponse fetchLoanDetailedInfo(GrantedLoanDetailsFetchingRequest request);

    Set<LoanEmiPayment> calculateEmiBreakups(LoanRequest loanRequest);
}
