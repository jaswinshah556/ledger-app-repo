package com.app.ledger.domainmodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author jaswin.shah
 * @version $Id: LoanProduct.java, v 0.1 2021-06-21 07:15 AM jaswin.shah Exp $$
 */
@Data
@Entity
@Table(name = "loan_product")
public class LoanProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rate_of_interest")
    Double rateOfInterest;

    @Column(name = "min_duration_months")
    Integer minDurationMonths;

    @Column(name = "max_duration_months")
    Integer maxDurationMonths;

    @OneToMany(targetEntity = GrantedLoan.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "loan_product_id", referencedColumnName = "id")
    private Set<GrantedLoan> grantedLoans = new HashSet<>();

    @OneToMany(targetEntity = LoanRequest.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "loan_product_id", referencedColumnName = "id")
    private Set<LoanRequest> loanRequests = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "bank", allowSetters = true)
    Bank bank;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at", nullable = false)
    LocalDateTime modifiedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanProduct that = (LoanProduct) o;
        return Objects.equals(rateOfInterest, that.rateOfInterest) &&
                Objects.equals(minDurationMonths, that.minDurationMonths) &&
                Objects.equals(maxDurationMonths, that.maxDurationMonths);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rateOfInterest, minDurationMonths, maxDurationMonths);
    }
}
