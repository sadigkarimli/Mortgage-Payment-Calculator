package com.company.hypothec_calculator.calculator;

import java.math.BigDecimal;

public class Summary {

    private BigDecimal totalMonthlyPayment;
    private BigDecimal totalMainMonthlyPayment;
    private BigDecimal totalInterestMonthlyPayment;

    public Summary() {

    }

    public void setTotalMonthlyPayment(BigDecimal totalMonthlyPayment) {
        this.totalMonthlyPayment = totalMonthlyPayment;
    }

    public void setTotalMainMonthlyPayment(BigDecimal totalMainMonthlyPayment) {
        this.totalMainMonthlyPayment = totalMainMonthlyPayment;
    }

    public void setTotalInterestMonthlyPayment(BigDecimal totalInterestMonthlyPayment) {
        this.totalInterestMonthlyPayment = totalInterestMonthlyPayment;
    }

    public BigDecimal getTotalMonthlyPayment() {
        return totalMonthlyPayment;
    }
    public BigDecimal getTotalMainMonthlyPayment() {
        return totalMainMonthlyPayment;
    }

    public BigDecimal getTotalInterestMonthlyPayment() {
        return totalInterestMonthlyPayment;
    }
}
