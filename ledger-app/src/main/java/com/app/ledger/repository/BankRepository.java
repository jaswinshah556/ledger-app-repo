package com.app.ledger.repository;

import com.app.ledger.domainmodels.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author jaswin.shah
 * @version $Id: BankRepository.java, v 0.1 2021-06-21 09:20 AM jaswin.shah Exp $$
 */
@Repository
public interface BankRepository  extends JpaRepository<Bank, Long> {

    Bank findByBankNameAndBankCode(String bankName, String bankCode);

}
