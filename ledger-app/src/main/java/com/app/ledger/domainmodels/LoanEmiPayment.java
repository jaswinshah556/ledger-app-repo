package com.app.ledger.domainmodels;

import com.app.ledger.constants.enums.LoanEmiPaymentState;
import com.app.ledger.constants.enums.LoanRequestStateEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * @author jaswin.shah
 * @version $Id: LoanEmiPayment.java, v 0.1 2021-06-21 07:06 AM jaswin.shah Exp $$
 */
@Data
@Entity
@Table(name = "loan_emi_payment")
@EqualsAndHashCode(exclude = {"grantedLoan"})
public class LoanEmiPayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "emi_date")
    Date emiDate;

    @Column(name = "emi_amount")
    Double emiAmount;

    @Column(name = "principal_component")
    Double principalComponent;

    @Column(name = "interest_component")
    Double interestComponent;

    @Column(name = "principal_outstanding")
    Double principalOutstanding;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_emi_payment_state")
    private LoanEmiPaymentState loanEmiPaymentState;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "grantedLoan", allowSetters = true)
    GrantedLoan grantedLoan;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at", nullable = false)
    LocalDateTime modifiedAt;

}
