package com.company.hypothec_calculator.properties;

import java.math.BigDecimal;

public class Home {

    private String homeAddress;
    private BigDecimal homePrice;


    public Home() {

    }

    public Home(String homeAdress, BigDecimal homePrice) {
        this.homeAddress = homeAdress;
        this.homePrice = homePrice;
    }


    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAdress(String homeAdress) {
        this.homeAddress = homeAdress;
    }

    public BigDecimal getHomePrice() {
        return homePrice;
    }

    public void setHomePrice(BigDecimal homePrice) {
        this.homePrice = homePrice;
    }

    @Override
    public String toString() {
        return "com.company.hypothec_calculator.properties.Home{" +
                ", homeAdress='" + homeAddress + '\'' +
                ", homePrice=" + homePrice +
                '}';
    }
}
