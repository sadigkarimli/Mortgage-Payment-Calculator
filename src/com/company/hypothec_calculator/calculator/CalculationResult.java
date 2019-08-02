package com.company.hypothec_calculator.calculator;

import java.util.List;

public class CalculationResult {

    private List<MonthlyPayments> paymentList;
    private boolean isEligible;
    private Summary summary;

    public List<MonthlyPayments> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<MonthlyPayments> paymentList) {
        this.paymentList = paymentList;
    }

    public boolean getIsEligible() {
        return isEligible;
    }

    public void setIsEligible(boolean eligible) {
        isEligible = eligible;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

}
