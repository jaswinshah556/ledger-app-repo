package com.app.ledger.service.impl;

import com.app.ledger.constants.Constants;
import com.app.ledger.constants.MessageConstants;
import com.app.ledger.constants.enums.GrantedLoanStateEnum;
import com.app.ledger.constants.enums.LoanEmiPaymentState;
import com.app.ledger.domainmodels.GrantedLoan;
import com.app.ledger.domainmodels.LoanEmiPayment;
import com.app.ledger.domainmodels.LoanNonEmiPayment;
import com.app.ledger.domainmodels.LoanRequest;
import com.app.ledger.repository.GrantedLoanRepository;
import com.app.ledger.repository.LoanEmiPaymentRepository;
import com.app.ledger.requests.repayment.LoanRepaymentRequest;
import com.app.ledger.responses.loandetails.NonEmiPaymentDetails;
import com.app.ledger.service.LoanRepaymentService;
import com.app.ledger.service.LoanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * @author jaswin.shah
 * @version $Id: LoanRepaymentServiceImpl.java, v 0.1 2021-06-21 08:56 AM jaswin.shah Exp $$
 */
@Service
@Slf4j
@Transactional
public class LoanRepaymentServiceImpl implements LoanRepaymentService {

    @Autowired
    private GrantedLoanRepository grantedLoanRepository;

    @Autowired
    private LoanEmiPaymentRepository loanEmiPaymentRepository;

    @Autowired
    private LoanService loanService;

    @Override
    public void repayLoan(LoanRepaymentRequest loanRepaymentRequest) {
        Assert.notNull(loanRepaymentRequest, MessageConstants.INVALID_REQUEST);
        Optional<GrantedLoan> loanHolder = grantedLoanRepository.findById(loanRepaymentRequest.getLoanId());

        Assert.isTrue(loanHolder.isPresent(),MessageConstants.INVALID_REQUEST);

        GrantedLoan loan = loanHolder.get();

        Assert.isTrue(!loan.getGrantedLoanStateEnum().equals(GrantedLoanStateEnum.CLOSED),MessageConstants.LOAN_IS_CLOSED);

        List<LoanEmiPayment> emis = loanEmiPaymentRepository.findByGrantedLoanAndLoanEmiPaymentStateOrderByEmiDate(loan, LoanEmiPaymentState.FUTURE);

        switch (loanRepaymentRequest.getType()) {

            case Constants.LUMPSUM:

                Assert.isTrue(loanRepaymentRequest.getAmount()<=loan.getBalanceAmount(),MessageConstants.AMOUNT_INVALID);

                loan.setBalanceAmount(loan.getBalanceAmount() -loanRepaymentRequest.getAmount());

                break;

            case Constants.EMI:


                LoanEmiPayment loanEmiPayment = emis.get(0);

                loanEmiPayment.setLoanEmiPaymentState(LoanEmiPaymentState.CLOSED);

                loan.setBalanceAmount(loan.getBalanceAmount() - loanEmiPayment.getPrincipalComponent());

                loan.getLoanEmiPayments().add(loanEmiPayment);

                break;

             default:
                 Assert.isTrue(false,MessageConstants.INVALID_REQUEST);


        }
        if(loan.getBalanceAmount() <= 0) {
            loan.setGrantedLoanStateEnum(GrantedLoanStateEnum.CLOSED);
            loan.cancelPendingEmis();
        }

        grantedLoanRepository.save(loan);

        //Raise pending amount refun to customer
    }
}
