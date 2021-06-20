package com.app.ledger.domainmodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author jaswin.shah
 * @version $Id: LoanNonEmiPayment.java, v 0.1 2021-06-21 07:10 AM jaswin.shah Exp $$
 */
@Data
@Entity
@Table(name = "loan_non_emi_payment")
public class LoanNonEmiPayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    GrantedLoan loan;

    @Column(name = "paid_amount")
    Double paidAmount;

    @Column(name = "pay_date", nullable = false)
    LocalDateTime paymentDate;

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
