package com.app.ledger.requests;


import lombok.Data;

/**
 * @author jaswin.shah
 * @version $Id: CustomerRegistrationRequest.java, v 0.1 2021-06-21 10:45 AM jaswin.shah Exp $$
 */
@Data
public class CustomerRegistrationRequest {

    private String firstName;

    private String lastName;

    private String mobileNumber;

    private String email;
}
