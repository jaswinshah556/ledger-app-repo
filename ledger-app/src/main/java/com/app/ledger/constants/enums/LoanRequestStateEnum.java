package com.app.ledger.constants.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author jaswin.shah
 * @version $Id: LoanRequestStateEnum.java, v 0.1 2021-06-21 07:50 AM jaswin.shah Exp $$
 */
public enum LoanRequestStateEnum {

    /**
     * INIT
     */
    INIT("INIT", "Loan requested", "INIT"),

    /**
     * UNDER_REVIEW
     */
    UNDER_REVIEW("UNDER_REVIEW", "UNDER REVIEW PROCESS", "UNDER_REVIEW"),


    /**
     * APPROVED
     */
    APPROVED("APPROVED", "Approved by team", "APPROVED"),

    /**
     * REJECTED
     */
    REJECTED("REJECTED", "Loan request rejected", "REJECTED"),

    /**
     * DISBURSED
     */
    DISBURSED("DISBURSED", "Disbursed to customer", "DISBURSED");

    /**
     * enum code
     */
    private final String code;

    /**
     * enum desc
     */
    private final String description;

    /**
     * Value
     */
    private final String value;

    /**
     * @param code        enum code
     * @param description enum desc
     */
    LoanRequestStateEnum(String code, String description, String value) {
        this.code = code;
        this.description = description;
        this.value = value;
    }

    /**
     * From code to enum.
     *
     * @param code the code
     * @return
     */
    public static LoanRequestStateEnum fromCode(String code) {
        for (LoanRequestStateEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }


    /**
     * @param loanRequestStateEnum
     * @return
     */
    public boolean equals(LoanRequestStateEnum loanRequestStateEnum) {

        boolean result = false;
        if (Objects.nonNull(loanRequestStateEnum) && StringUtils.equals(loanRequestStateEnum.getCode(), getCode())) {
            result =  true;
        }
        return result;
    }

    /**
     * Getter method for property <tt>code</tt>.
     *
     * @return property value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Getter method for property <tt>description</tt>.
     *
     * @return property value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter method for property <tt>value</tt>.
     *
     * @return property value of value
     */
    public String getValue() {
        return value;
    }
}

