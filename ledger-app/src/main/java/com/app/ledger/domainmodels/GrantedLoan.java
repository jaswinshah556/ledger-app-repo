package com.app.ledger.domainmodels;

import com.app.ledger.constants.enums.GrantedLoanStateEnum;
import com.app.ledger.constants.enums.LoanEmiPaymentState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


/**
 * @author jaswin.shah
 * @version $Id: GrantedLoan.java, v 0.1 2021-06-21 07:00 AM jaswin.shah Exp $$
 */
@Data
@Entity
@Table(name = "granted_loan")
@EqualsAndHashCode(exclude = {"loanEmiPayments","loanNonEmiPayments"})
public class GrantedLoan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "availed_loan_amount")
    Double availedLoanAmount;

    @Column(name = "balance_amount")
    Double balanceAmount;


    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "loan_request_id", referencedColumnName = "id")
    private LoanRequest loanRequest;

    @Enumerated(EnumType.STRING)
    @Column(name = "granted_loan_state")
    private GrantedLoanStateEnum grantedLoanStateEnum;

    @OneToMany(targetEntity = LoanEmiPayment.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "granted_loan_id", referencedColumnName = "id")
    private Set<LoanEmiPayment> loanEmiPayments = new HashSet<>();

    @OneToMany(targetEntity = LoanNonEmiPayment.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "granted_loan_id", referencedColumnName = "id")
    private Set<LoanNonEmiPayment> loanNonEmiPayments = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "customer", allowSetters = true)
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "bankBranch", allowSetters = true)
    BankBranch bankBranch;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "loanProduct", allowSetters = true)
    LoanProduct loanProduct;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at", nullable = false)
    LocalDateTime modifiedAt;

    public void cancelPendingEmis() {
        for(LoanEmiPayment emi : loanEmiPayments) {
            if(LoanEmiPaymentState.FUTURE.equals(emi.getLoanEmiPaymentState())) {
                emi.setLoanEmiPaymentState(LoanEmiPaymentState.CANCELLED);
            }
        }
    }

}
