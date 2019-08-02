package com.company.hypothec_calculator.customers;

public enum Gender {
    MEN(64), WOMAN(62);

    private int pensionAge;
    Gender() {

    }

    Gender(int pensionAge) {
        this.pensionAge = pensionAge;
    }

    public int getPensionAges() {
        return pensionAge;
    }
}
