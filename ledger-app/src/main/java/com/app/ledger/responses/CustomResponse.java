package com.app.ledger.responses;

import lombok.Data;

/**
 * @author jaswin.shah
 * @version $Id: CustomResponse.java, v 0.1 2021-06-21 06:49 AM jaswin.shah Exp $$
 */
@Data
public class CustomResponse<T> {

    private T result;

    private String message;

    private ResultInfo resultInfo;
}
