package com.app.ledger.requests;

import lombok.Data;

/**
 * @author jaswin.shah
 * @version $Id: BankRegistrationRequest.java, v 0.1 2021-06-21 10:18 AM jaswin.shah Exp $$
 */
@Data
public class BankRegistrationRequest {

   private String bankName;

   private String bankCode;

   private String branchName;

   private String city;

   private String pinCode;
}
