package com.app.ledger.responses.loandetails;

import com.app.ledger.constants.enums.GrantedLoanStateEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * @author jaswin.shah
 * @version $Id: LoanDetailedResponse.java, v 0.1 2021-06-21 11:49 AM jaswin.shah Exp $$
 */
@Data
public class LoanDetailedResponse {

    private long loanId;

    private GrantedLoanStateEnum loanStatus;

    BankDetails bankInfo;

    CustomerDetails customerInfo;

    private Double availedLoanPrincipal;

    private Double balanceAmount;

    private List<LoanEmis> emiDetails = new ArrayList<>();

    private List<NonEmiPaymentDetails> lumpsumPayments = new ArrayList<>();


}
