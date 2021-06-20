package com.app.ledger.repository;

import com.app.ledger.domainmodels.Bank;
import com.app.ledger.domainmodels.BankBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jaswin.shah
 * @version $Id: BankBranchRepository.java, v 0.1 2021-06-21 09:18 AM jaswin.shah Exp $$
 */
@Repository
public interface BankBranchRepository  extends JpaRepository<BankBranch, Long> {

    BankBranch findByBranchNameAndBranchCityAndPinCode(String branchName, String branchCity, String pinCode);

    List<BankBranch> findAllByBank(Bank bank);


}

