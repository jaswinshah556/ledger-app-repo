package com.app.ledger.service.impl;

import com.app.ledger.constants.MessageConstants;
import com.app.ledger.domainmodels.Customer;
import com.app.ledger.repository.CustomerRepository;
import com.app.ledger.requests.CustomerRegistrationRequest;
import com.app.ledger.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


/**
 * @author jaswin.shah
 * @version $Id: CustomerServiceImpl.java, v 0.1 2021-06-21 07:34 AM jaswin.shah Exp $$
 */
@Service
@Slf4j
@Transactional
public class CustomerServiceImpl implements CustomerService {


    @Autowired
    CustomerRepository customerRepository;

    @Override
    public void registerCustomerAccount(CustomerRegistrationRequest request) {

        log.info("Registering customer");
        validateCustomerRegistrationRequest(request);

        Customer customer =  customerRepository.findByFirstNameAndLastName(request.getFirstName(), request.getLastName());

        Assert.isNull(customer, MessageConstants.CUSTOMER_ALREADY_REGISTERED_WITH_THIS_NAMES);

        customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setMobileNumber(request.getMobileNumber());

        customerRepository.save(customer);

        log.info("Customer entry added");

    }

    private void validateCustomerRegistrationRequest(CustomerRegistrationRequest request) {
        Assert.notNull(request, MessageConstants.INVALID_REQUEST);
        Assert.isTrue(StringUtils.isNotBlank(request.getEmail()),MessageConstants.EMAIL_MANDATORY);
        Assert.isTrue(StringUtils.isNotBlank(request.getFirstName()),MessageConstants.FIRST_NAME_MANDATORY);
        Assert.isTrue(StringUtils.isNotBlank(request.getLastName()),MessageConstants.LAST_NAME_MANDATORY);
        Assert.isTrue(StringUtils.isNotBlank(request.getMobileNumber()),MessageConstants.MOBILE_MANDATORY);
    }
}
