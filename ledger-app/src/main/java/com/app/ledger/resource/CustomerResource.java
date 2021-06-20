package com.app.ledger.resource;

import com.app.ledger.constants.MessageConstants;
import com.app.ledger.constants.enums.ResultCodeEnum;
import com.app.ledger.requests.CustomerRegistrationRequest;
import com.app.ledger.responses.CustomResponse;
import com.app.ledger.responses.ResultInfo;
import com.app.ledger.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author jaswin.shah
 * @version $Id: CustomerResource.java, v 0.1 2021-06-21 11:18 AM jaswin.shah Exp $$
 */
@RestController
@RequestMapping("/api/ledger/customers")
@Slf4j
@Api(value = "Customer Resource", description = "APIs for Customers registrations and de-registrations")
public class CustomerResource {


    @Autowired
    CustomerService customerService;

    @PostMapping("/register")
    @ApiOperation(value = "Register Customer Account with the ledger system")
    public ResponseEntity<CustomResponse<String>> registerCustomerAccount(@RequestBody @Valid CustomerRegistrationRequest request) {
        log.info("Customer Registration Request : {}", request);
        ResponseEntity<CustomResponse<String>> responseEntity;
        CustomResponse<String> response = new CustomResponse<>();
        try {
            customerService.registerCustomerAccount(request);
            response.setMessage(MessageConstants.REGISTRATION_SUCCESS);
            response.setResultInfo(new ResultInfo(ResultCodeEnum.SUCCESS));
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            log.error("Exception : {}", e);
            response.setMessage(e.getMessage());
            response.setResultInfo(new ResultInfo(ResultCodeEnum.PARAM_ILLEGAL,e.getMessage()));
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception exception) {
            log.error("Exception : {}", exception);
            response.setMessage(exception.getMessage());
            response.setResultInfo(new ResultInfo(ResultCodeEnum.INTERNAL_SERVER_ERROR,exception.getMessage()));
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return responseEntity;
    }

}
