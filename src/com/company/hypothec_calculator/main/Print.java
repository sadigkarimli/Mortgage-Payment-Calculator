package com.company.hypothec_calculator.main;

import com.company.hypothec_calculator.calculator.CalculationResult;
import com.company.hypothec_calculator.calculator.MonthlyPayments;
import com.company.hypothec_calculator.calculator.Summary;

import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Print {
    public void printList(CalculationResult calculationResult){

        String alignFormat = "| %-8s | %-15s | %-15s | %-15s | %-15s |%n";
        System.out.format("+----------+-----------------+-----------------+-----------------+-----------------+%n");
        System.out.format("|  Months  |  Payment Date   | Monthly Payment |    Main Part    |  Interest Part  |%n");
        System.out.format("+----------+-----------------+-----------------+-----------------+-----------------+%n");

        List<MonthlyPayments> monthlyPaymentsList = calculationResult.getPaymentList();
        for (int i = 0; i < monthlyPaymentsList.size(); ++i){
            String monthNo = "\u004E\u00BA" + " "+ String.valueOf(monthlyPaymentsList.get(i).getNumberOfMonthes());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String paymentDate = monthlyPaymentsList.get(i).getPaymentDate().format(formatter);
            String monthlyPayment = monthlyPaymentsList.get(i).
                    getMonthlyPaymentAmmount().setScale(2, RoundingMode.HALF_UP) + " " + "AZN";

            String mainPayment = monthlyPaymentsList.get(i).
                    getMainPayment().setScale(2, RoundingMode.HALF_UP) + " " + "AZN";
            String interestPayment = monthlyPaymentsList.get(i).
                    getInterestPayment().setScale(2, RoundingMode.HALF_UP) + " " + "AZN";

            System.out.format(alignFormat,monthNo,paymentDate,monthlyPayment,mainPayment,interestPayment);
            System.out.format("+----------+-----------------+-----------------+-----------------+-----------------+%n");
        }
        Summary summary = calculationResult.getSummary();
        String totalMonthlyPayment = summary.getTotalMonthlyPayment().setScale(2,RoundingMode.HALF_UP) + " " + "AZN";
        String totalMainPayment = summary.getTotalMainMonthlyPayment().setScale(2,RoundingMode.HALF_UP) + " " + "AZN";
        String totalInterestPayment = summary.getTotalInterestMonthlyPayment().setScale(2,RoundingMode.HALF_UP) + " " + "AZN";
        String totalText = "       Total";
        String newAlignFormat = "| %-26s | %-15s | %-15s | %-15s |%n";
        System.out.format(newAlignFormat,totalText,totalMonthlyPayment,totalMainPayment,totalInterestPayment);
        System.out.format("+----------------------------+-----------------+-----------------+-----------------+%n");

    }

    public void printProgressBar(String message) {

        int i = 0;
        while(i < 21) {
            System.out.print(message + "[");
            for (int j=0;j<i;j++) {
                System.out.print("#");
            }

            for (int j=0;j<20-i;j++) {
                System.out.print(" ");
            }

            System.out.print("] "+  i * 5 + "%");
            if(i<20) {
                System.out.print("\r");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i++;
        }
        System.out.println();
    }


}
