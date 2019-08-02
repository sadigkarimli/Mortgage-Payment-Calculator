package com.company.hypothec_calculator.banks;

import java.math.BigDecimal;

public class Bank {
    private BigDecimal initialPaymentInterest; // illik odenish
    private int creditPeriod; // is a period with years which depends banks

    public Bank(BigDecimal initialPaymentInterest,int creditPeriod) {
        this.initialPaymentInterest = initialPaymentInterest;
        this.creditPeriod = creditPeriod;
    }

    public BigDecimal getFirstPaymentInterest() {
        return initialPaymentInterest;
    }

    public int getCreditPeriod() {
        return creditPeriod;
    }
}
