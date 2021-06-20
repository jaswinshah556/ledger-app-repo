package com.app.ledger.repository;

import com.app.ledger.domainmodels.Bank;
import com.app.ledger.domainmodels.LoanProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jaswin.shah
 * @version $Id: LoanProductRepository.java, v 0.1 2021-06-21 09:34 AM jaswin.shah Exp $$
 */
@Repository
public interface LoanProductRepository  extends JpaRepository<LoanProduct, Long> {

    List<LoanProduct> findAllByBank(Bank bank);

}
