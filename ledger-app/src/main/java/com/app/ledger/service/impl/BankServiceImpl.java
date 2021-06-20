package com.app.ledger.service.impl;

import com.app.ledger.constants.MessageConstants;
import com.app.ledger.domainmodels.Bank;
import com.app.ledger.domainmodels.BankBranch;
import com.app.ledger.repository.BankBranchRepository;
import com.app.ledger.repository.BankRepository;
import com.app.ledger.requests.BankBranchViewRequest;
import com.app.ledger.requests.BankRegistrationRequest;
import com.app.ledger.responses.BankBranchViewResponse;
import com.app.ledger.responses.BankViewResponse;
import com.app.ledger.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author jaswin.shah
 * @version $Id: BankServiceImpl.java, v 0.1 2021-06-21 08:32 AM jaswin.shah Exp $$
 */
@Service
@Slf4j
@Transactional
public class BankServiceImpl  implements BankService {

    @Autowired
   private  BankRepository bankRepository;

    @Autowired
    private BankBranchRepository bankBranchRepository;

    @Override
    public List<BankViewResponse> fetchAllBanks() {

        List<BankViewResponse> banks = new ArrayList<>();

        List<Bank> banksFromDb = bankRepository.findAll();

        for(Bank bank :banksFromDb ) {
            BankViewResponse bankViewResponse = new BankViewResponse();
            bankViewResponse.setBankCode(bank.getBankCode());
            bankViewResponse.setBankName(bank.getBankName());
            banks.add(bankViewResponse);
        }
        return banks;
    }

    public List<BankBranchViewResponse> fetchBranches(BankBranchViewRequest request){
        Assert.isTrue(StringUtils.isNotBlank(request.getBankName()), MessageConstants.BANK_NAME_ABSENT);
        Assert.isTrue(StringUtils.isNotBlank(request.getBankCode()), MessageConstants.BANK_CODE_ABSENT);
        Bank bank = bankRepository.findByBankNameAndBankCode(request.getBankName(),request.getBankCode());

        Assert.notNull(bank,MessageConstants.BANK_NOT_REGISTERED);

        List<BankBranch> branches = bankBranchRepository.findAllByBank(bank);
        List<BankBranchViewResponse> result = new ArrayList<>();
        if(Objects.nonNull(branches)) {
            for(BankBranch branch : branches) {
                BankBranchViewResponse bankBranchViewResponse = new BankBranchViewResponse();
                bankBranchViewResponse.setBranchCity(branch.getBranchCity());
                bankBranchViewResponse.setBranchName(branch.getBranchName());
                bankBranchViewResponse.setPinCode(branch.getPinCode());
                result.add(bankBranchViewResponse);
            }

        }
        return result;
    }


    @Override
    public void registerBank(BankRegistrationRequest bankRegistrationRequest) {
        validateBankRegistrationRequest(bankRegistrationRequest);

        Bank bank = bankRepository.findByBankNameAndBankCode(bankRegistrationRequest.getBankName(),bankRegistrationRequest.getBankCode());

        if(Objects.isNull(bank)) {
            bank = new Bank();
            bank.setBankName(bankRegistrationRequest.getBankName());
            bank.setBankCode(bankRegistrationRequest.getBankCode());
            bank = bankRepository.save(bank);
            log.info("Saved bank with name {} and code {} to database",bank.getBankCode(),bank.getBankName());
        }


        BankBranch newBranch = new BankBranch();
        newBranch.setBank(bank);
        newBranch.setBranchName(bankRegistrationRequest.getBranchName());
        newBranch.setBranchCity(bankRegistrationRequest.getCity());
        newBranch.setPinCode(bankRegistrationRequest.getPinCode());

        Assert.isTrue(!bank.isBranchPresent(newBranch), MessageConstants.BRANCH_ALREADY_EXIST);

        bankBranchRepository.save(newBranch);
        log.info("Saved bank-branch with name {} and city {} and pincode {} to database",newBranch.getBranchName(),newBranch.getBranchCity(),
                newBranch.getPinCode());

    }

    /**
     *
     * @param bankRegistrationRequest
     */
    private void validateBankRegistrationRequest(BankRegistrationRequest bankRegistrationRequest) {
        log.info("Registering new bank to the system, validating the request");
        Assert.isTrue(StringUtils.isNotBlank(bankRegistrationRequest.getBankName()), MessageConstants.BANK_NAME_ABSENT);
        Assert.isTrue(StringUtils.isNotBlank(bankRegistrationRequest.getBankCode()), MessageConstants.BANK_CODE_ABSENT);
        Assert.isTrue(StringUtils.isNotBlank(bankRegistrationRequest.getBranchName()), MessageConstants.BANK_BRANCH_ABSENT);
        Assert.isTrue(StringUtils.isNotBlank(bankRegistrationRequest.getCity()), MessageConstants.BANK_CITY_ABSENT);
        Assert.isTrue(StringUtils.isNotBlank(bankRegistrationRequest.getPinCode()), MessageConstants.BANK_PIN_CODE_ABSENT);
    }
}
