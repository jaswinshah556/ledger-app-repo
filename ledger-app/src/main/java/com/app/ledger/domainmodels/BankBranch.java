package com.app.ledger.domainmodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
 * @version $Id: BankBranch.java, v 0.1 2021-06-21 06:50 AM jaswin.shah Exp $$
 */
@Data
@Entity
@Table(name = "bank_branch")
@EqualsAndHashCode(exclude = {"bank"})
public class BankBranch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "branch_city")
    private String branchCity;

    @Column(name = "pin_code")
    private String pinCode;

    @OneToMany(targetEntity = GrantedLoan.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_branch_id", referencedColumnName = "id")
    private Set<GrantedLoan> grantedLoans = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "bank", allowSetters = true)
    private Bank bank;

    @OneToMany(targetEntity = LoanRequest.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_branch_id", referencedColumnName = "id")
    private Set<LoanRequest> loanRequests = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankBranch that = (BankBranch) o;
        return Objects.equals(branchName, that.branchName) &&
                Objects.equals(branchCity, that.branchCity) &&
                Objects.equals(pinCode, that.pinCode) &&
                Objects.equals(bank, that.bank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(branchName, branchCity, pinCode, bank);
    }

    @UpdateTimestamp
    @Column(name = "modified_at", nullable = false)
    LocalDateTime modifiedAt;

}
