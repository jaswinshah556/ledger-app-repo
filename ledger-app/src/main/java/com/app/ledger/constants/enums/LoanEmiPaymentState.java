package com.app.ledger.constants.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;


/**
 * @author jaswin.shah
 * @version $Id: LoanEmiPaymentState.java, v 0.1 2021-06-21 08:45 AM jaswin.shah Exp $$
 */
public enum LoanEmiPaymentState {

    /**
     * PAST_PENDING
     */
    PAST_PENDING("PAST_PENDING", "Past Emi Not Paid", "PAST_PENDING"),

    /**
     * FUTURE
     */
    FUTURE("FUTURE", "Not paid", "FUTURE"),

    /**
     * CLOSED
     */
    CLOSED("CLOSED", "Emi paid", "CLOSED"),

    /**
     * CANCELLED
     */
    CANCELLED("CANCELLED", "CANCELLED", "CANCELLED")
    ;

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
    LoanEmiPaymentState(String code, String description, String value) {
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
    public static LoanEmiPaymentState fromCode(String code) {
        for (LoanEmiPaymentState type : values()) {
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


