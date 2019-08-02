package com.company.hypothec_calculator.main;

import com.company.hypothec_calculator.calculator.CalculationResult;
import com.company.hypothec_calculator.calculator.MonthlyPaymentType;
import com.company.hypothec_calculator.banks.Bank;
import com.company.hypothec_calculator.calculator.HypothecCalculator;
import com.company.hypothec_calculator.customers.Customer;
import com.company.hypothec_calculator.customers.Gender;
import com.company.hypothec_calculator.databases.OracleDataBase;
import com.company.hypothec_calculator.properties.Home;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MainClass {

    public static void main(String[] args) {
        System.out.println();
        System.out.println("  HYPOTHEC CALCULATOR v1.0\n");

        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println(">>>>>>>>>> OPTIONS <<<<<<<<<<");
            System.out.println("[1] - Start Calculation");
            System.out.println("[2] - Exit Program");
            System.out.println("<<<<<<<<<<<<<<>>>>>>>>>>>>>>>");
            System.out.print("Enter Option Number[1 - 2]: ");
            int option = scan.nextInt();

            switch (option) {

                case 1:
                    Print print = new Print();
                    System.out.println("\n>>>>>>>>>> Inputs <<<<<<<<<<");
                    String name;
                    System.out.print("First Name: ");
                    while (true) {
                        name = scan.next();

                        if (!name.isEmpty() && name.length() >= 2) {
                            break;
                        } else{
                            System.out.println(">> Wrong, input for Name");
                            System.out.print("Enter first name again: ");
                        }
                    }

                    String surName;
                    System.out.print("Surname: ");
                    while (true) {
                        surName = scan.next();

                        if (!surName.isEmpty() && surName.length() >= 2) {
                            break;
                        } else{
                            System.out.println(">> Wrong, input for surname");
                            System.out.print("Enter surname again: ");
                        }
                    }

                    System.out.print("Gender(M for men, W for women): ");
                    Gender gender;
                    while (true) {
                        String genderStr = scan.next();
                        if (genderStr.equals("m") || genderStr.equals("M")) {
                            gender = Gender.MEN;
                            break;
                        } else if (genderStr.equals("W") || genderStr.equals("w")) {
                            gender = Gender.WOMAN;
                            break;
                        } else {
                            System.out.println(">> Wrong input, content must be one of these [m, M, w, W]");
                            System.out.print("Enter gender again: ");
                        }
                    }

                    BigDecimal salary;
                    System.out.print("Salary(AZN): ");
                    while (true) {
                        salary = scan.nextBigDecimal();
                        if (salary.compareTo(BigDecimal.ZERO) == 1) {
                            break;
                        } else {
                            System.out.println(">> Wrong input for salary.");
                            System.out.print("Enter salary again: ");
                        }
                    }

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    System.out.print("Birthday(Exp:01.12.2014): ");
                    String birthdayStr;
                    LocalDate birthday;
                    while (true) {
                        birthdayStr = scan.next();
                        if (birthdayStr.length() == 10 && !birthdayStr.isEmpty()) {
                            birthday = LocalDate.parse(birthdayStr, formatter);
                            break;
                        } else{
                            System.out.println(">> Wrong input, Birthday has to be this format [day.month.year]");
                            System.out.print("Enter birthday again: ");
                        }
                    }

                    System.out.print("Home Price(AZN): ");
                    BigDecimal homePrice;
                    while (true) {
                         homePrice = scan.nextBigDecimal();
                        if (!(homePrice == null) && !homePrice.equals(BigDecimal.ZERO)){
                            break;
                        } else {
                            System.out.println(">> Wrong input, home price mut be greater than zero.");
                            System.out.print("Enter Home price again: ");
                        }
                    }

                    System.out.print("Home Address: ");
                    Scanner scanner = new Scanner(System.in);
                    String homeAddress;
                    while (true) {
                         homeAddress = scanner.nextLine();
                        if (!homeAddress.isEmpty() && homeAddress.length() >= 3){
                            break;
                        } else {
                            System.out.println(">> Wrong input, home address must be greater than zero.");
                            System.out.print("Enter Home Address again: ");
                        }
                    }

                    System.out.print("Bank Initial Payment Interest(Exp:25%): ");
                    BigDecimal initialPaymentInterest;
                    while (true ) {
                        initialPaymentInterest = scan.nextBigDecimal();
                        if (!(initialPaymentInterest == null) && !homePrice.equals(BigDecimal.ZERO)){
                            break;
                        } else {
                            System.out.println(">> Wrong input, Bank Initial Payment Interest must be greater than zero.");
                            System.out.print("Enter Bank Initial Payment Interest again: ");
                        }
                    }

                    System.out.print("Credit period(with years): ");
                    int creditPeriod;
                    while (true) {
                        creditPeriod = scan.nextInt();
                        if (creditPeriod > 0) {
                            break;
                        } else {
                            System.out.println(">> Wrong input, Credit period  must be greater than zero.");
                            System.out.print("Enter Credit period again: ");
                        }
                    }
                    System.out.println();
                    print.printProgressBar("Result is calculating ");

                    Customer customer = new Customer(name, surName, salary, birthday, gender);
                    Bank bank = new Bank(initialPaymentInterest, creditPeriod);
                    Home home = new Home(homeAddress, homePrice);

                    HypothecCalculator hypothecCalculator = new HypothecCalculator();
                    CalculationResult calculationResult = hypothecCalculator.calculateResult(customer, home, bank, MonthlyPaymentType.ANNUITET);
                    if (!hypothecCalculator.isEligible(customer,bank)) {
                        System.out.println("You don't get a "+bank.getCreditPeriod()+"-year credit.");
                        System.out.println("Returning Options..");
                        break;
                    } else {
                        System.out.print("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> PAYMENT TABLE <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
                        print.printList(calculationResult);

                        System.out.println("\nCan this credit be approved?");
                        while (true) {
                            System.out.println("--------------------------------");
                            System.out.println("[1] - Yes, Approve this credit");
                            System.out.println("[2] - Return Options");
                            System.out.println("--------------------------------");
                            System.out.print("Enter order number [1 - 2]: ");
                            int approvingOption = scan.nextInt();

                            if (approvingOption == 1) {

                                OracleDataBase oracleDataBase = new OracleDataBase();
                                String propertiesFile = "DataBase.properties";
                                try {

                                    oracleDataBase.createConnection(propertiesFile);
                                    oracleDataBase.insertCustomerDataToDataBase(customer, home, calculationResult);
                                    customer.setCustomerId(oracleDataBase.getCustomerIdFromDb());

                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                } finally {

                                    try {
                                        oracleDataBase.closeConnection(oracleDataBase);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                                break;
                            } else if (approvingOption == 2) {
                                System.out.println();
                                break;
                            } else {
                                System.out.println(">> Wrong order, try again.");
                            }
                        }
                        break;
                    }

                case 2:
                    System.out.println(">> Program closed successfully");
                    System.exit(0);

            }

        }
    }
}

