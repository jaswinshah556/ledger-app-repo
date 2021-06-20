package com.app.ledger.domainmodels;

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
 * @version $Id: Bank.java, v 0.1 2021-06-21 06:56 AM jaswin.shah Exp $$
 */
@Data
@Entity
@Table(name = "bank")
@EqualsAndHashCode(exclude = {"bankBranches","loanProducts"})
public class Bank implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bank_code")
    private String bankCode;

    @OneToMany(targetEntity = BankBranch.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_id", referencedColumnName = "id")
    private Set<BankBranch> bankBranches = new HashSet<>();

    @OneToMany(targetEntity = LoanProduct.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_id", referencedColumnName = "id")
    private Set<LoanProduct> loanProducts = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at", nullable = false)
    LocalDateTime modifiedAt;

    public boolean isBranchPresent(BankBranch branch) {
        boolean isBranchAlreadyOpened = false;
        for(BankBranch bankBranch : bankBranches) {
            if(bankBranch.equals(branch)) {
                isBranchAlreadyOpened = true;
                break;
            }
        }
        return isBranchAlreadyOpened;
    }

    public boolean isProductValid(LoanProduct loanProduct) {
        boolean isProductValid = false;
        for(LoanProduct product : loanProducts) {
            if(product.equals(loanProduct)) {
                isProductValid = true;
                break;
            }
        }
        return isProductValid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return Objects.equals(bankName, bank.bankName) &&
                Objects.equals(bankCode, bank.bankCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bankName, bankCode);
    }
}


