package com.app.ledger.domainmodels;

import com.app.ledger.constants.enums.LoanRequestStateEnum;
import com.app.ledger.requests.EmiCalculationRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author jaswin.shah
 * @version $Id: LoanRequest.java, v 0.1 2021-06-21 07:18 AM jaswin.shah Exp $$
 */
@Data
@Entity
@Table(name = "loan_request")
public class LoanRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "no_of_months")
    Integer noOfMonths;

    @Column(name = "loan_principal")
    Double loanPrincipal;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_request_State")
    private LoanRequestStateEnum loanRequestState;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "loanProduct", allowSetters = true)
    LoanProduct loanProduct;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "bankBranch", allowSetters = true)
    private BankBranch bankBranch;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "customer", allowSetters = true)
    private Customer customer;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at", nullable = false)
    LocalDateTime modifiedAt;

    public EmiCalculationRequest generateEmiCalculationRequest() {
        EmiCalculationRequest emiCalculationRequest = new EmiCalculationRequest();

        emiCalculationRequest.setLoanPrinciple(getLoanPrincipal());
        com.app.ledger.requests.loanprodcuts.LoanProduct loanProduct = new com.app.ledger.requests.loanprodcuts.LoanProduct();
        loanProduct.setRateOfInterest(getLoanProduct().getRateOfInterest());
        loanProduct.setMinDurationMonths(getLoanProduct().getMinDurationMonths());
        loanProduct.setMaxDurationMonths(getLoanProduct().getMaxDurationMonths());
        emiCalculationRequest.setLoanProduct(loanProduct);
        emiCalculationRequest.setRepaymentMonths(getNoOfMonths());;
        return emiCalculationRequest;
    }

    public LoanRequest clone() {
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setLoanRequestState(loanRequestState);
        loanRequest.setNoOfMonths(noOfMonths);
        loanRequest.setLoanProduct(loanProduct);
        loanRequest.setBankBranch(bankBranch);
        loanRequest.setCustomer(customer);
        loanRequest.setId(id);
        loanRequest.setLoanPrincipal(loanPrincipal);
        loanRequest.setCreatedAt(createdAt);
        loanRequest.setModifiedAt(modifiedAt);
        return loanRequest;
    }
}


// Register Bank; -Done
// Add bank loan products -Done
// Register Customer; -Done

// Fetch banks providing loans -Done
// Check loan products by bank -Done
//Show bank brances - Done

// Fetch EMI details for loan product with some duration for a bank.


// Request loanProduct from particular bank branch


// Update loan product state.


// Repay loan


// Fetch all loans for a customer.
// Fetch loan details by loanId.
// Close loan API : called by handler.
