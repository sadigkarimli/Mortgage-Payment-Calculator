package com.company.hypothec_calculator.calculator;
import java.math.BigDecimal;
import java.time.LocalDate;

public class MonthlyPayments {

    private MonthlyPaymentType monthlyPaymentType;
    private int numberOfMonths;
    private LocalDate paymentDate;
    private BigDecimal monthlyPaymentAmmount;
    private BigDecimal mainPayment;
    private BigDecimal interestPayment;
    public MonthlyPayments() {

    }

    public MonthlyPayments(MonthlyPaymentType monthlyPaymentType) {
        this.monthlyPaymentType = monthlyPaymentType;
    }

    //setter methods
    public void setMonthlyPaymentType(MonthlyPaymentType monthlyPaymentType) {
        this.monthlyPaymentType = monthlyPaymentType;
    }

    public void setNumberOfMonths(int numberOfMonths) {
        this.numberOfMonths = numberOfMonths;
    }

    public void setMonthlyPaymentAmmount(BigDecimal monthlyPaymentAmmount) {
        this.monthlyPaymentAmmount = monthlyPaymentAmmount;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setMainPayment(BigDecimal mainPayment) {
        this.mainPayment = mainPayment;
    }

    public void setInterestPayment(BigDecimal interestPayment) {
        this.interestPayment = interestPayment;
    }

    // getter methods
    public MonthlyPaymentType getMonthlyPaymentType() {
        return monthlyPaymentType; }

    public  int getNumberOfMonthes() {
        return numberOfMonths;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public BigDecimal getMonthlyPaymentAmmount() {
        return monthlyPaymentAmmount;
    }

    public BigDecimal getMainPayment() {
        return mainPayment;
    }

    public BigDecimal getInterestPayment() {
        return interestPayment;
    }

    @Override
    public String toString() {
        return "com.company.hypothec_calculator.calculator.MonthlyPayments{" +
                "monthlyPaymentType=" + monthlyPaymentType +
                ", numberOfMonths=" + numberOfMonths +
                ", paymentDate=" + paymentDate +
                ", monthlyPaymentAmmount=" + monthlyPaymentAmmount +
                ", mainPayment=" + mainPayment +
                ", interestPayment=" + interestPayment +
                '}';
    }
}
