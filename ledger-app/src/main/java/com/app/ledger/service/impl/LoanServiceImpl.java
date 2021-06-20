package com.app.ledger.service.impl;

import com.app.ledger.DateTimeUtils;
import com.app.ledger.constants.enums.GrantedLoanStateEnum;
import com.app.ledger.constants.enums.LoanEmiPaymentState;
import com.app.ledger.constants.enums.LoanRequestStateEnum;
import com.app.ledger.constants.MessageConstants;
import com.app.ledger.domainmodels.*;
import com.app.ledger.repository.*;
import com.app.ledger.requests.EmiCalculationRequest;
import com.app.ledger.requests.GrantedLoanDetailsFetchingRequest;
import com.app.ledger.requests.LoanAvailRequest;
import com.app.ledger.requests.LoanStatusModificationRequest;
import com.app.ledger.requests.loanprodcuts.LoanProductsRegistrationRequest;
import com.app.ledger.requests.loanprodcuts.LoanProductsViewRequest;
import com.app.ledger.responses.EmiCalculationResponse;
import com.app.ledger.responses.LoanRequestStatusResponse;
import com.app.ledger.responses.loandetails.*;
import com.app.ledger.service.EmiCalculationService;
import com.app.ledger.service.LoanService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author jaswin.shah
 * @version $Id: LoanServiceImpl.java, v 0.1 2021-06-21 08:23 AM jaswin.shah Exp $$
 */
@Service
@Slf4j
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanProductRepository loanProductRepository;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private BankBranchRepository bankBranchRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LoanRequestRepository loanRequestRepository;

    @Autowired
    private EmiCalculationService emiCalculationService;

    @Autowired
    private GrantedLoanRepository grantedLoanRepository;

    @Override
    public void registerLoanProductsWithBank(LoanProductsRegistrationRequest request) {

        Assert.isTrue(StringUtils.isNotBlank(request.getBankCode()),MessageConstants.BANK_CODE_ABSENT);
        Assert.isTrue(StringUtils.isNotBlank(request.getBankName()),MessageConstants.BANK_NAME_ABSENT);
        Assert.isTrue(!CollectionUtils.isEmpty(request.getProducts()),MessageConstants.NO_PRODUCTS_SPECIFIED);


        Bank bank = bankRepository.findByBankNameAndBankCode(request.getBankName(),request.getBankCode());

        Assert.notNull(bank, MessageConstants.BANK_NOT_REGISTERED);

        List<LoanProduct> currentProducts = loanProductRepository.findAllByBank(bank);

        List<LoanProduct> newProducts = new ArrayList<>();
        for(com.app.ledger.requests.loanprodcuts.LoanProduct newProduct: request.getProducts()) {
            LoanProduct newLoanProduct = new LoanProduct();
            newLoanProduct.setBank(bank);
            newLoanProduct.setMaxDurationMonths(newProduct.getMaxDurationMonths());
            newLoanProduct.setMinDurationMonths(newProduct.getMinDurationMonths());
            newLoanProduct.setRateOfInterest(newProduct.getRateOfInterest());
            newProducts.add(newLoanProduct);
        }

        if(Objects.nonNull(currentProducts)) {
            for (LoanProduct newProduct : newProducts) {
                Assert.isTrue(!currentProducts.contains(newProduct), MessageConstants.INVALID_REQUEST_FEW_PRODUCTS_ALREADY_REGISTERED);
            }
        }

        loanProductRepository.saveAll(newProducts);

    }

    @Override
    public void updateLoanRequestStatus(LoanStatusModificationRequest loanStatusModificationRequest) {

        Assert.notNull(loanStatusModificationRequest,MessageConstants.INVALID_REQUEST);

        Optional<LoanRequest> loanRequestPlaceholder = loanRequestRepository.findById(loanStatusModificationRequest.getLoanRequestId());

        Assert.isTrue(loanRequestPlaceholder.isPresent(),MessageConstants.INVALID_REQUEST);

        LoanRequest loanRequest = loanRequestPlaceholder.get();

        switch (loanStatusModificationRequest.getStatus()) {
            case APPROVED:
                Assert.isTrue(!LoanRequestStateEnum.APPROVED.equals(loanRequest.getLoanRequestState()),MessageConstants.LOAN_ALREADY_APPROVED);
                Assert.isTrue(!LoanRequestStateEnum.REJECTED.equals(loanRequest.getLoanRequestState()),MessageConstants.LOAN_ALREADY_REJECTED);
                Assert.isTrue(!LoanRequestStateEnum.DISBURSED.equals(loanRequest.getLoanRequestState()),MessageConstants.LOAN_ALREADY_DISBURSED);
                Assert.isTrue(!LoanRequestStateEnum.INIT.equals(loanRequest.getLoanRequestState()),MessageConstants.LOAN_NOT_REVIEWED);
                loanRequest.setLoanRequestState(LoanRequestStateEnum.APPROVED);

                break;

            case REJECTED:
                Assert.isTrue(!LoanRequestStateEnum.APPROVED.equals(loanRequest.getLoanRequestState()),MessageConstants.LOAN_ALREADY_APPROVED);
                Assert.isTrue(!LoanRequestStateEnum.REJECTED.equals(loanRequest.getLoanRequestState()),MessageConstants.LOAN_ALREADY_REJECTED);
                Assert.isTrue(!LoanRequestStateEnum.DISBURSED.equals(loanRequest.getLoanRequestState()),MessageConstants.LOAN_ALREADY_DISBURSED);
                Assert.isTrue(!LoanRequestStateEnum.INIT.equals(loanRequest.getLoanRequestState()),MessageConstants.LOAN_NOT_REVIEWED);
                loanRequest.setLoanRequestState(LoanRequestStateEnum.REJECTED);
                break;

            case UNDER_REVIEW:
                Assert.isTrue(!LoanRequestStateEnum.DISBURSED.equals(loanRequest.getLoanRequestState()),MessageConstants.LOAN_ALREADY_DISBURSED);
                Assert.isTrue(!LoanRequestStateEnum.UNDER_REVIEW.equals(loanRequest.getLoanRequestState()),MessageConstants.LOAN_ALREADY_UNDER_REVIEW);

                loanRequest.setLoanRequestState(LoanRequestStateEnum.UNDER_REVIEW);

                break;

            case DISBURSED:
                Assert.isTrue(!LoanRequestStateEnum.DISBURSED.equals(loanRequest.getLoanRequestState()),MessageConstants.LOAN_ALREADY_DISBURSED);
                Assert.isTrue(LoanRequestStateEnum.APPROVED.equals(loanRequest.getLoanRequestState()),MessageConstants.LOAN_REQUEST_NOT_APPROVED);
                loanRequest.setLoanRequestState(LoanRequestStateEnum.DISBURSED);

                updateDisbursedLoanAndEmiBreakups(loanRequest);
                break;

            default:
                Assert.isTrue(false,MessageConstants.INVALID_REQUEST);

        }
        loanRequestRepository.save(loanRequest);

    }

    private void updateDisbursedLoanAndEmiBreakups(LoanRequest loanRequest) {
        GrantedLoan grantedLoan = new GrantedLoan();
        grantedLoan.setAvailedLoanAmount(loanRequest.getLoanPrincipal());
        grantedLoan.setCustomer(loanRequest.getCustomer());
        grantedLoan.setBankBranch(loanRequest.getBankBranch());
        grantedLoan.setLoanProduct(loanRequest.getLoanProduct());
        grantedLoan.setBalanceAmount(grantedLoan.getAvailedLoanAmount());
        grantedLoan.setGrantedLoanStateEnum(GrantedLoanStateEnum.ACTIVE);
        Set<LoanEmiPayment> emiPayments = calculateEmiBreakups(loanRequest);
        grantedLoan.setLoanEmiPayments(emiPayments);
        grantedLoan.setLoanRequest(loanRequest);
        grantedLoanRepository.save(grantedLoan);
    }

    @Override
    public Set<LoanEmiPayment> calculateEmiBreakups(LoanRequest loanRequest) {

        EmiCalculationRequest emiCalculationRequest = loanRequest.generateEmiCalculationRequest();

        EmiCalculationResponse emiCalculationResponse = emiCalculationService.calculateEmi(emiCalculationRequest);
        Set<LoanEmiPayment> emiPayments = new HashSet<>();

        Date emiDate = DateTimeUtils.getLastDateOfCurrentMonth();
        Double loanPrincipalTemp = loanRequest.getLoanPrincipal();
        for(int emiNo = 1; emiNo <= emiCalculationResponse.getTenureInMonths();emiNo++) {
            LoanEmiPayment loanEmiPayment = new LoanEmiPayment();
            loanEmiPayment.setEmiDate(emiDate);

            Double interestComponent = loanPrincipalTemp*loanRequest.getLoanProduct().getRateOfInterest()/1200;

            loanEmiPayment.setInterestComponent(interestComponent);
            Double principalComponent = emiCalculationResponse.getEmi() - interestComponent;
            Double principalOutstanding = loanPrincipalTemp - principalComponent;
            loanPrincipalTemp = principalOutstanding;
            loanEmiPayment.setEmiAmount(emiCalculationResponse.getEmi());
            loanEmiPayment.setInterestComponent(interestComponent);
            loanEmiPayment.setPrincipalComponent(principalComponent);
            loanEmiPayment.setLoanEmiPaymentState(LoanEmiPaymentState.FUTURE);
            loanEmiPayment.setPrincipalOutstanding(principalOutstanding);
            emiPayments.add(loanEmiPayment);

            emiDate = DateUtils.addMonths(emiDate,1);
        }
        return emiPayments;
    }


    @Override
    public LoanRequestStatusResponse submitLoanRequest(LoanAvailRequest loanRequest) {


        Optional<BankBranch> bankBranchPlaceHolder = bankBranchRepository.findById(loanRequest.getBankBranchId());
        Assert.isTrue(bankBranchPlaceHolder.isPresent(),MessageConstants.INVALID_BRANCH);

        Optional<LoanProduct> loanProductPlaceholder = loanProductRepository.findById(loanRequest.getLoanProductId());
        Assert.isTrue(loanProductPlaceholder.isPresent(),MessageConstants.INVALID_PRODUCT);

        Optional<Customer> customerPlaceHolder = customerRepository.findById(loanRequest.getCustomerId());
        Assert.isTrue(customerPlaceHolder.isPresent(),MessageConstants.UNREGISTERED_CUSTOMER);


        LoanProduct product = loanProductPlaceholder.get();
        BankBranch branch = bankBranchPlaceHolder.get();
        Customer customer = customerPlaceHolder.get();

        Assert.isTrue(branch.getBank().isProductValid(product),MessageConstants.INVALID_PRODUCT);


        Assert.isTrue(loanRequest.getTenureInMonths() >= product.getMinDurationMonths()
                && loanRequest.getTenureInMonths() <= product.getMaxDurationMonths(),
                MessageConstants.DURATION_NOT_SUPPORTED_BY_LOAN_PRODUCT);


        LoanRequest loanRequest1 = registerLoanRequestInDB(loanRequest, product, branch, customer);


        EmiCalculationRequest emiCalculationRequest = loanRequest1.generateEmiCalculationRequest();

        LoanRequestStatusResponse loanRequestStatusResponse = new LoanRequestStatusResponse();
        EmiCalculationResponse emiCalculationResponse = emiCalculationService.calculateEmi(emiCalculationRequest);
        loanRequestStatusResponse.setRequestId(loanRequest1.getId());
        loanRequestStatusResponse.setLoanStatus(loanRequest1.getLoanRequestState());
        loanRequestStatusResponse.setEmiDetails(emiCalculationResponse);

        return loanRequestStatusResponse;
    }

    @Override
    public LoanDetailedResponse fetchLoanDetailedInfo(GrantedLoanDetailsFetchingRequest request){

        Assert.notNull(request,MessageConstants.INVALID_REQUEST);

        Optional<GrantedLoan> grantedLoanPlaceHolder = grantedLoanRepository.findById(request.getLoanId());

        Assert.isTrue(grantedLoanPlaceHolder.isPresent(),MessageConstants.INVALID_REQUEST);

        GrantedLoan loan = grantedLoanPlaceHolder.get();

        LoanDetailedResponse loanDetailedResponse = new LoanDetailedResponse();

        BankDetails bankInfo = new BankDetails();
        bankInfo.setBankName(loan.getBankBranch().getBank().getBankName());
        bankInfo.setBankBranch(loan.getBankBranch().getBranchName());
        bankInfo.setBankCode(loan.getBankBranch().getBank().getBankCode());
        bankInfo.setBankCity(loan.getBankBranch().getBranchCity());
        bankInfo.setPinCode(loan.getBankBranch().getPinCode());
        loanDetailedResponse.setBankInfo(bankInfo);

        CustomerDetails customerInfo = new CustomerDetails();
        customerInfo.setFirstName(loan.getCustomer().getFirstName());
        customerInfo.setLastName(loan.getCustomer().getLastName());
        customerInfo.setEmail(loan.getCustomer().getEmail());
        customerInfo.setMobile(loan.getCustomer().getMobileNumber());
        loanDetailedResponse.setCustomerInfo(customerInfo);

        loanDetailedResponse.setAvailedLoanPrincipal(loan.getAvailedLoanAmount());
        loanDetailedResponse.setLoanId(loan.getId());
        loanDetailedResponse.setBalanceAmount(loan.getBalanceAmount());
        List<LoanEmis> emiDetails = new ArrayList<>();

        for(LoanEmiPayment loanEmiPayment : loan.getLoanEmiPayments()) {
            LoanEmis loanEmi = new LoanEmis();
            loanEmi.setEmiAmount(loanEmiPayment.getEmiAmount());
            loanEmi.setInterestComponent(loanEmiPayment.getInterestComponent());
            loanEmi.setPrincipalComponent(loanEmiPayment.getPrincipalComponent());
            loanEmi.setPrincipalOutstanding(loanEmiPayment.getPrincipalOutstanding());
            loanEmi.setEmiDate(loanEmiPayment.getEmiDate());
            loanEmi.setLoanEmiPaymentState(loanEmiPayment.getLoanEmiPaymentState());
            emiDetails.add(loanEmi);
        }
        Collections.sort(emiDetails, new Comparator<LoanEmis>() {
            public int compare(LoanEmis o1, LoanEmis o2) {
                return o1.getEmiDate().compareTo(o2.getEmiDate());
            }
        });

        loanDetailedResponse.setEmiDetails(emiDetails);


        List<NonEmiPaymentDetails> nonEmiPaymentDetails = new ArrayList<>();



        for(LoanNonEmiPayment loanNonEmiPayment : loan.getLoanNonEmiPayments()) {
            NonEmiPaymentDetails nonEmiPaymentDetails1 = new NonEmiPaymentDetails();
            nonEmiPaymentDetails1.setPaidAmount(loanNonEmiPayment.getPaidAmount());
            nonEmiPaymentDetails1.setPaymentDate(loanNonEmiPayment.getPaymentDate());
            nonEmiPaymentDetails.add(nonEmiPaymentDetails1);
        }
        Collections.sort(nonEmiPaymentDetails, new Comparator<NonEmiPaymentDetails>() {
            public int compare(NonEmiPaymentDetails o1, NonEmiPaymentDetails o2) {
                return o1.getPaymentDate().compareTo(o2.getPaymentDate());
            }
        });

        loanDetailedResponse.setLumpsumPayments(nonEmiPaymentDetails);
        loanDetailedResponse.setLoanStatus(loan.getGrantedLoanStateEnum());

        return loanDetailedResponse;
    }


    private LoanRequest registerLoanRequestInDB(LoanAvailRequest loanRequest, LoanProduct product, BankBranch branch, Customer customer) {
        LoanRequest loanRequest1 = new LoanRequest();
        loanRequest1.setCustomer(customer);
        loanRequest1.setBankBranch(branch);
        loanRequest1.setLoanProduct(product);
        loanRequest1.setNoOfMonths(loanRequest.getTenureInMonths());
        loanRequest1.setLoanPrincipal(loanRequest.getPrincipal());
        loanRequest1.setLoanRequestState(LoanRequestStateEnum.INIT);

        loanRequest1 = loanRequestRepository.save(loanRequest1);
        return loanRequest1;
    }

    public List<com.app.ledger.requests.loanprodcuts.LoanProduct> showBankLoanProducts(LoanProductsViewRequest request) {
        Assert.isTrue(StringUtils.isNotBlank(request.getBankCode()),MessageConstants.BANK_CODE_ABSENT);
        Assert.isTrue(StringUtils.isNotBlank(request.getBankName()),MessageConstants.BANK_NAME_ABSENT);

        Bank bank = bankRepository.findByBankNameAndBankCode(request.getBankName(),request.getBankCode());

        Assert.notNull(bank, MessageConstants.BANK_NOT_REGISTERED);

        List<LoanProduct> currentProducts = loanProductRepository.findAllByBank(bank);

        List<com.app.ledger.requests.loanprodcuts.LoanProduct> result = new ArrayList<>();

        if(Objects.nonNull(currentProducts)) {
            for (LoanProduct product : currentProducts) {
                com.app.ledger.requests.loanprodcuts.LoanProduct resultProduct = new com.app.ledger.requests.loanprodcuts.LoanProduct();
                resultProduct.setMaxDurationMonths(product.getMaxDurationMonths());
                resultProduct.setMinDurationMonths(product.getMinDurationMonths());
                resultProduct.setRateOfInterest(product.getRateOfInterest());
                result.add(resultProduct);
            }
        }
        return result;
    }

}
