package com.app.ledger.responses.loandetails;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author jaswin.shah
 * @version $Id: NonEmiPaymentDetails.java, v 0.1 2021-06-21 11:49 AM jaswin.shah Exp $$
 */
@Data
public class NonEmiPaymentDetails {

    Double paidAmount;

    LocalDateTime paymentDate;

}
