package com.company.hypothec_calculator.customers;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Customer {

    private int customerId;
    private String name;
    private String surname;
    private LocalDate birthDay;
    private Gender gender;
    private BigDecimal salary;


    public Customer(String name, String surname, BigDecimal salary, LocalDate birthDay, Gender gender) {
        this.name = name;
        this.surname = surname;
        this.salary = salary;
        this.birthDay = birthDay;
        this.gender = gender;

    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
