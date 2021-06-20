package com.app.ledger.repository;

import com.app.ledger.domainmodels.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jaswin.shah
 * @version $Id: CustomerRepository.java, v 0.1 2021-06-21 09:22 AM jaswin.shah Exp $$
 */
@Repository
public interface CustomerRepository  extends JpaRepository<Customer, Long> {

    Customer findByFirstNameAndLastName(String firstName, String lastName);

}
