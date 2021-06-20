package com.app.ledger.resource;

import com.app.ledger.constants.MessageConstants;
import com.app.ledger.constants.enums.ResultCodeEnum;
import com.app.ledger.requests.EmiCalculationRequest;
import com.app.ledger.requests.GrantedLoanDetailsFetchingRequest;
import com.app.ledger.requests.LoanAvailRequest;
import com.app.ledger.requests.LoanStatusModificationRequest;
import com.app.ledger.requests.loanprodcuts.LoanProduct;
import com.app.ledger.requests.loanprodcuts.LoanProductsRegistrationRequest;
import com.app.ledger.requests.loanprodcuts.LoanProductsViewRequest;
import com.app.ledger.responses.CustomResponse;
import com.app.ledger.responses.EmiCalculationResponse;
import com.app.ledger.responses.LoanRequestStatusResponse;
import com.app.ledger.responses.ResultInfo;
import com.app.ledger.responses.loandetails.LoanDetailedResponse;
import com.app.ledger.service.EmiCalculationService;
import com.app.ledger.service.LoanService;
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
import java.util.List;

/**
 * @author jaswin.shah
 * @version $Id: LoanResource.java, v 0.1 2021-06-21 11:49 AM jaswin.shah Exp $$
 */
@RestController
@RequestMapping("/api/ledger/loans")
@Slf4j
@Api(value = "Loans Resource", description = "APIs for Loan Product Management")
public class LoanResource {


    @Autowired
   private  LoanService loanService;

    @Autowired
    private EmiCalculationService emiCalculationService;

    @PostMapping("/register-loan-products")
    @ApiOperation(value = "Register loan product for a bank")
    public ResponseEntity<CustomResponse<String>> registerBankWithLedgerSystem(@RequestBody @Valid LoanProductsRegistrationRequest request) {
        log.info("Bank Registration Request : {}", request);
        ResponseEntity<CustomResponse<String>> responseEntity;
        CustomResponse<String> response = new CustomResponse<>();
        try {
            loanService.registerLoanProductsWithBank(request);
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



    @PostMapping("/show-bank-loan-products")
    @ApiOperation(value = "Register Bank to the ledger system")
    public ResponseEntity<CustomResponse<List<LoanProduct>>> registerBankWithLedgerSystem(@RequestBody @Valid LoanProductsViewRequest request) {
        log.info("Bank Registration Request : {}", request);
        ResponseEntity<CustomResponse<List<LoanProduct>>> responseEntity;
        CustomResponse<List<LoanProduct>> response = new CustomResponse<>();
        try {
            List<LoanProduct> result =  loanService.showBankLoanProducts(request);
            response.setResult(result);
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

    @PostMapping("/show-bank-loan-emis")
    @ApiOperation(value = "Show EMI's for loan product, principal and tenure")
    public ResponseEntity<CustomResponse<EmiCalculationResponse>> registerBankWithLedgerSystem(@RequestBody @Valid EmiCalculationRequest request) {
        log.info("Bank Registration Request : {}", request);
        ResponseEntity<CustomResponse<EmiCalculationResponse>> responseEntity;
        CustomResponse<EmiCalculationResponse> response = new CustomResponse<>();
        try {
            EmiCalculationResponse result =  emiCalculationService.calculateEmi(request);
            response.setResult(result);
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

    @PostMapping("/request-loan")
    @ApiOperation(value = "Submit loan request")
    public ResponseEntity<CustomResponse<LoanRequestStatusResponse>> registerBankWithLedgerSystem(@RequestBody @Valid LoanAvailRequest request) {
        log.info("Submit loan availing  Request : {}", request);
        ResponseEntity<CustomResponse<LoanRequestStatusResponse>> responseEntity;
        CustomResponse<LoanRequestStatusResponse> response = new CustomResponse<>();
        try {
            LoanRequestStatusResponse loanRequestStatusResponse = loanService.submitLoanRequest(request);
            response.setResult(loanRequestStatusResponse);
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

    @PostMapping("/update-loan-request-status")
    @ApiOperation(value = "Update Loan request status")
    public ResponseEntity<CustomResponse<String>> registerBankWithLedgerSystem(@RequestBody @Valid LoanStatusModificationRequest request) {
        log.info("Update Loan Status: {}", request);
        ResponseEntity<CustomResponse<String>> responseEntity;
        CustomResponse<String> response = new CustomResponse<>();
        try {
            loanService.updateLoanRequestStatus(request);
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

    @PostMapping("/fetch-loan-info")
    @ApiOperation(value = "Fetch Loan detailed info")
    public ResponseEntity<CustomResponse<LoanDetailedResponse>> registerBankWithLedgerSystem(@RequestBody @Valid GrantedLoanDetailsFetchingRequest request) {
        log.info("Fetch loan detailed info: {}", request);
        ResponseEntity<CustomResponse<LoanDetailedResponse>> responseEntity;
        CustomResponse<LoanDetailedResponse> response = new CustomResponse<>();
        try {
            LoanDetailedResponse loanDetailedResponse = loanService.fetchLoanDetailedInfo(request);
            response.setResult(loanDetailedResponse);
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
