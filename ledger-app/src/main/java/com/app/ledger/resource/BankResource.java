package com.app.ledger.resource;

import com.app.ledger.constants.MessageConstants;
import com.app.ledger.constants.enums.ResultCodeEnum;
import com.app.ledger.requests.BankBranchViewRequest;
import com.app.ledger.requests.BankRegistrationRequest;
import com.app.ledger.responses.BankBranchViewResponse;
import com.app.ledger.responses.BankViewResponse;
import com.app.ledger.responses.CustomResponse;
import com.app.ledger.responses.ResultInfo;
import com.app.ledger.service.BankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author jaswin.shah
 * @version $Id: BankResource.java, v 0.1 2021-06-21 11:12 AM jaswin.shah Exp $$
 */
@RestController
@RequestMapping("/api/ledger/banks")
@Slf4j
@Api(value = "Bank Resource", description = "APIs for Banks registrations and de-registrations")
public class BankResource {


    @Autowired
    BankService bankService;

    @PostMapping("/register-bank")
    @ApiOperation(value = "Register Bank to the ledger system")
    public ResponseEntity<CustomResponse<String>> registerBankWithLedgerSystem(@RequestBody @Valid BankRegistrationRequest request) {
        log.info("Bank Registration Request : {}", request);
        ResponseEntity<CustomResponse<String>> responseEntity;
        CustomResponse<String> response = new CustomResponse<>();
        try {
            bankService.registerBank(request);
            response.setMessage(MessageConstants.REGISTRATION_SUCCESS);
            response.setResultInfo(new ResultInfo(ResultCodeEnum.SUCCESS));
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            log.error("Exception : {}", e);
            response.setMessage(e.getMessage());
            response.setResultInfo(new ResultInfo(ResultCodeEnum.PARAM_ILLEGAL, e.getMessage()));
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception exception) {
            log.error("Exception : {}", exception);
            response.setMessage(exception.getMessage());
            response.setResultInfo(new ResultInfo(ResultCodeEnum.INTERNAL_SERVER_ERROR, exception.getMessage()));
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return responseEntity;
    }

    @GetMapping("/show-all-banks")
    @ApiOperation(value = "Fetch all Banks")
    public ResponseEntity<CustomResponse<List<BankViewResponse>>> fetchDaasConfigurations() {

        log.info("FetchAll banks");
        ResponseEntity<CustomResponse<List<BankViewResponse>>> responseEntity;
        CustomResponse<List<BankViewResponse>> response = new CustomResponse<>();
        try {

            List<BankViewResponse> banks = bankService.fetchAllBanks();
            response.setResult(banks);
            response.setMessage(MessageConstants.SUCCESS);
            response.setResultInfo(new ResultInfo(ResultCodeEnum.SUCCESS));
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception exception) {
            log.error("Exception : {}", exception);
            response.setMessage(exception.getMessage());
            response.setResultInfo(new ResultInfo(ResultCodeEnum.INTERNAL_SERVER_ERROR, exception.getMessage()));
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return responseEntity;
    }

    @PostMapping("/show-bank-branches")
    @ApiOperation(value = "Show Bank branches")
    public ResponseEntity<CustomResponse<List<BankBranchViewResponse>>> registerBankWithLedgerSystem(@RequestBody @Valid BankBranchViewRequest request) {
        log.info("Bank Registration Request : {}", request);
        ResponseEntity<CustomResponse<List<BankBranchViewResponse>>> responseEntity;
        CustomResponse<List<BankBranchViewResponse>> response = new CustomResponse<>();
        try {
            List<BankBranchViewResponse> branches = bankService.fetchBranches(request);
            response.setResult(branches);
            response.setMessage(MessageConstants.SUCCESS);
            response.setResultInfo(new ResultInfo(ResultCodeEnum.SUCCESS));
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            log.error("Exception : {}", e);
            response.setMessage(e.getMessage());
            response.setResultInfo(new ResultInfo(ResultCodeEnum.PARAM_ILLEGAL, e.getMessage()));
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception exception) {
            log.error("Exception : {}", exception);
            response.setMessage(exception.getMessage());
            response.setResultInfo(new ResultInfo(ResultCodeEnum.INTERNAL_SERVER_ERROR, exception.getMessage()));
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return responseEntity;
    }
}
