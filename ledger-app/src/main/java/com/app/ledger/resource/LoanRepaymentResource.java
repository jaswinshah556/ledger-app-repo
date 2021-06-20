package com.app.ledger.resource;


import com.app.ledger.constants.MessageConstants;
import com.app.ledger.constants.enums.ResultCodeEnum;
import com.app.ledger.requests.repayment.LoanRepaymentRequest;
import com.app.ledger.responses.CustomResponse;
import com.app.ledger.responses.ResultInfo;
import com.app.ledger.responses.loandetails.LoanDetailedResponse;
import com.app.ledger.service.LoanRepaymentService;
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
 * @version $Id: LoanRepaymentResource.java, v 0.1 2021-06-21 11:23 AM jaswin.shah Exp $$
 */
@RestController
@RequestMapping("/api/ledger/re-payments")
@Slf4j
@Api(value = "Loan payments Resource", description = "APIs for Loan repayments")
public class LoanRepaymentResource {

    @Autowired
    private LoanRepaymentService loanRepaymentService;

    @PostMapping("/pay")
    @ApiOperation(value = "Pay Loan amount ")
    public ResponseEntity<CustomResponse<String>> payLoan(@RequestBody @Valid LoanRepaymentRequest request) {
        log.info("Repay loan amount request: {}", request);
        ResponseEntity<CustomResponse<String>> responseEntity;
        CustomResponse<String> response = new CustomResponse<>();
        try {
            loanRepaymentService.repayLoan(request);
            response.setMessage(MessageConstants.SUCCESS);
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
