package com.company.hypothec_calculator.databases;

import com.company.hypothec_calculator.calculator.CalculationResult;
import com.company.hypothec_calculator.calculator.MonthlyPayments;
import com.company.hypothec_calculator.calculator.Summary;
import com.company.hypothec_calculator.customers.Customer;
import com.company.hypothec_calculator.properties.Home;

import java.io.FileReader;
import java.io.IOException;
import java.math.RoundingMode;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class OracleDataBase {
    Connection connection;
    PreparedStatement ps;
    ResultSet rs;

    public OracleDataBase() {
        connection = null;
        ps = null;
        rs = null;
    }

    public Connection getConnection() {
        return connection;
    }

    public PreparedStatement getPs() {
        return ps;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void createConnection(String propertiesFile) throws IOException, ClassNotFoundException, SQLException {
        Properties config = new Properties();
        config.load(new FileReader(propertiesFile));

        Class.forName(config.getProperty("driver"));
        System.out.println("Driver has loaded to VM.");
        String url = config.getProperty("url");
        String username = config.getProperty("username");
        String password = config.getProperty("password");

        connection = DriverManager.getConnection(url,username,password);
        System.out.println("Database connection has done.");
        connection.setAutoCommit(false);
    }

    public int getCustomerIdFromDb() throws SQLException {
        String sql = "select max(customer_id) from com.company.hypothec_calculator.properties";
        ps = connection.prepareStatement(sql);
        rs = ps.executeQuery();
        return rs.getInt(sql);
    }

    private void insertToCustumersTable(Customer customer) throws SQLException {
        String sql = "insert into customers(customer_id, first_name, last_name, salary)" +
                "values (customer_seq.nextval, ?, ?, ?)";
        ps = connection.prepareStatement(sql);
        ps.setString(1,customer.getName());
        ps.setString(2,customer.getSurname());
        ps.setBigDecimal(3,customer.getSalary());
        connection.commit();
    }

    private void insertToPropertiesTable(Home home) throws SQLException {
        String sql = "insert into com.company.hypothec_calculator.properties(property_id, adress, property_value)" +
                "values(property_seq.nextval, ?, ?)";
        ps = connection.prepareStatement(sql);
        ps.setString(1,home.getHomeAddress());
        ps.setBigDecimal(2,home.getHomePrice());
        connection.commit();

    }

    private void insertToMonthlyPaymentsTable(CalculationResult calculationResult) throws SQLException {

        List<MonthlyPayments> monthlyPaymentsList = calculationResult.getPaymentList();
        String sql = "insert into monthly_payments(payment_id, customer_id, property_id, month_no, payment_date, total, base, interest)" +
                "values(payment_seq.nextval,(select max(customer_id) from customers), (select max(property_id) from com.company.hypothec_calculator.properties), ?, TO_DATE(?), ?, ?, ?)";
        for (int i = 0; i < monthlyPaymentsList.size(); ++i) {

            ps = connection.prepareStatement(sql);
            ps.setInt(1,monthlyPaymentsList.get(i).getNumberOfMonthes());
            Date date = Date.valueOf(monthlyPaymentsList.get(i).getPaymentDate());
            ps.setDate(2, date);
            ps.setBigDecimal(3,monthlyPaymentsList.get(i).getMonthlyPaymentAmmount().setScale(2, RoundingMode.HALF_UP));
            ps.setBigDecimal(4,monthlyPaymentsList.get(i).getMainPayment().setScale(2,RoundingMode.HALF_UP));
            ps.setBigDecimal(5,monthlyPaymentsList.get(i).getInterestPayment().setScale(2,RoundingMode.HALF_UP));
            connection.commit();
        }
    }

    private void insertToSummaryTable(CalculationResult calculationResult) throws SQLException {
        String sql = "insert into summary (summary_id, customer_id, property_id, total_amount, total_base, total_interest)" +
                "    values(SUMMARY_SEQ.nextval, (select max(customer_id) from customers), (select max(property_id) from com.company.hypothec_calculator.properties)," +
                "    ?, ? , ?)";
        Summary summary = calculationResult.getSummary();
        ps = connection.prepareStatement(sql);
        ps.setBigDecimal(1,summary.getTotalMonthlyPayment());
        ps.setBigDecimal(2,summary.getTotalMainMonthlyPayment());
        ps.setBigDecimal(3, summary.getTotalInterestMonthlyPayment());
        connection.commit();
    }

    public void insertCustomerDataToDataBase (Customer customer, Home home, CalculationResult calculationResult) throws SQLException {
        insertToCustumersTable(customer);
        insertToPropertiesTable(home);
        insertToMonthlyPaymentsTable(calculationResult);
        insertToSummaryTable(calculationResult);
        System.out.println("com.company.hypothec_calculator.customers.Customer approved successfully.");
    }

    public void closeConnection(OracleDataBase oracleDataBase) throws SQLException {
            if (oracleDataBase.getRs() != null) {
                oracleDataBase.getRs().close();
            }

            if (oracleDataBase.getPs() != null) {
                oracleDataBase.getPs().close();
            }

            if (oracleDataBase.getConnection() != null) {
                oracleDataBase.getConnection().close();
            }
    }
}
