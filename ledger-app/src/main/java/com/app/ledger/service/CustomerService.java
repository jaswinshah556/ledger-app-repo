package com.app.ledger.service;

import com.app.ledger.requests.CustomerRegistrationRequest;

/**
 * @author jaswin.shah
 * @version $Id: CustomerService.java, v 0.1 2021-06-21 08:10 AM jaswin.shah Exp $$
 */
public interface CustomerService {

     void registerCustomerAccount(CustomerRegistrationRequest request);
}
