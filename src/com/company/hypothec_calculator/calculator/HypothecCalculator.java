package com.company.hypothec_calculator.calculator;

import com.company.hypothec_calculator.banks.Bank;
import com.company.hypothec_calculator.customers.Customer;
import com.company.hypothec_calculator.properties.Home;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HypothecCalculator {
    private BigDecimal principalDebt;
    private BigDecimal monthlyInterest;
    private BigDecimal initialPayment;
    private int paymentPeriod; // is a period with months which will be used in some calculations

    public HypothecCalculator() {

    }

    // getter methods
    public BigDecimal getPrincipalDebt() {
        return principalDebt;
    }

    public BigDecimal getMonthlyInterest() {
        return monthlyInterest;
    }

    public BigDecimal getInitialPayment() {
        return initialPayment;
    }

    public int getPaymentPeriod() {
        return paymentPeriod;
    }

    //end of getter methods;

    // setter methods
    private void setPrincipalDebt(BigDecimal principalDebt) {
        this.principalDebt = principalDebt;
    }

    private void setMonthlyInterest(BigDecimal monthlyInterest) {
        this.monthlyInterest = monthlyInterest;
    }

    private void setInitialPayment(BigDecimal initialPayment) {
        this.initialPayment = initialPayment;
    }

    private void setPaymentPeriod(int paymentPeriod) {
        this.paymentPeriod = paymentPeriod;
    }
    // end of setter methods

    public int getPeriodOfPayment() {
        return paymentPeriod;
    }

    // Main menthods<>
    public boolean isEligible(Customer customer, Bank bank) {
        int creditPeriod = bank.getCreditPeriod();
        LocalDate birthday = customer.getBirthDay();
        LocalDate presentDate = LocalDate.now();
        LocalDate pensionDate = birthday.plusYears(customer.getGender().getPensionAges());
        LocalDate lastPaymentDate = presentDate.plusYears(creditPeriod);
        if (lastPaymentDate.isBefore(pensionDate)) {
            return true;
        } else {
            return false;
        }
    }


    private List<MonthlyPayments> calculateMonthlyPayments(Home home, Bank bank, MonthlyPaymentType monthlyPaymentType) {

        List<MonthlyPayments> monthlyPaymentsList = new ArrayList<>();

        if (monthlyPaymentType.equals(MonthlyPaymentType.ANNUITET)) {
            BigDecimal homePrice = home.getHomePrice();
            BigDecimal firstlyPaymentInterest = bank.getFirstPaymentInterest();

            initialPayment = homePrice.multiply(firstlyPaymentInterest).divide(new BigDecimal("100"));
            principalDebt = homePrice.subtract(initialPayment);
            BigDecimal exactMonthlyInterest = new BigDecimal("8").divide(new BigDecimal("12"), 12, RoundingMode.HALF_UP);
            monthlyInterest = exactMonthlyInterest.setScale(2, RoundingMode.HALF_UP);
            paymentPeriod = bank.getCreditPeriod() * 12;


            // calculation of  monthly payment amount
            int exponent = paymentPeriod * (-1);
            BigDecimal valueOfMonthlyInterest = exactMonthlyInterest.divide(new BigDecimal("100"));
            double value = valueOfMonthlyInterest.doubleValue();
            double devisor = 1.0 - Math.pow((1.0 + value), exponent);
            BigDecimal expression = BigDecimal.valueOf(value).divide(BigDecimal.valueOf(devisor), 12, RoundingMode.HALF_UP);
            BigDecimal monthlyPaymentAmmount = principalDebt.multiply(expression);
            //

            // Calculation of Main and Interest part of Principal Debt
            BigDecimal interestPayment;
            BigDecimal mainPayment = BigDecimal.ZERO;
            BigDecimal valueOfPrincipalDebt = principalDebt;
            for (int monthNo = 1; monthNo <= paymentPeriod; ++monthNo) {
                MonthlyPayments monthlyPayments = new MonthlyPayments();
                monthlyPayments.setNumberOfMonths(monthNo);
                monthlyPayments.setPaymentDate(LocalDate.now().plusMonths(monthNo));
                monthlyPayments.setMonthlyPaymentAmmount(monthlyPaymentAmmount);

                valueOfPrincipalDebt = valueOfPrincipalDebt.subtract(mainPayment);
                interestPayment = (valueOfPrincipalDebt).multiply(valueOfMonthlyInterest);
                mainPayment = monthlyPaymentAmmount.subtract(interestPayment);

                monthlyPayments.setMainPayment(mainPayment);
                monthlyPayments.setInterestPayment(interestPayment);
                monthlyPaymentsList.add(monthlyPayments);
            }
        }
        return monthlyPaymentsList;
    }

    private Summary calculateSummary(List<MonthlyPayments> monthlyPaymentsList) {
        Summary summary = new Summary();
        BigDecimal totalMonthlyPayment = BigDecimal.ZERO;
        BigDecimal totalMainPayment = BigDecimal.ZERO;
        BigDecimal totalInterestPayment = BigDecimal.ZERO;
        for (int i = 0; i < monthlyPaymentsList.size(); ++i) {
             totalMonthlyPayment = totalMonthlyPayment.add(monthlyPaymentsList.get(i).getMonthlyPaymentAmmount());
             totalMainPayment = totalMainPayment.add(monthlyPaymentsList.get(i).getMainPayment());
             totalInterestPayment = totalInterestPayment.add(monthlyPaymentsList.get(i).getInterestPayment());
        }
        summary.setTotalMonthlyPayment(totalMonthlyPayment);
        summary.setTotalMainMonthlyPayment(totalMainPayment);
        summary.setTotalInterestMonthlyPayment(totalInterestPayment);
        return summary;
    }

    public CalculationResult calculateResult(Customer customer, Home home, Bank bank, MonthlyPaymentType monthlyPaymentType){
        CalculationResult calculationResult = new CalculationResult();
        calculationResult.setIsEligible(isEligible(customer, bank));
        calculationResult.setPaymentList(calculateMonthlyPayments(home, bank, monthlyPaymentType));
        calculationResult.setSummary(calculateSummary(calculateMonthlyPayments(home, bank, monthlyPaymentType)));
        return calculationResult;
    }

}