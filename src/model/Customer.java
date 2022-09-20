package model;

import DAO.FirstLevelDivisionDaoImpl;

/**This class is the model for Customers. */
public class Customer {

    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionId;
    private String divisionName;
    private int totalAppointments;

    /**This method is the constructor for new customers. */
    public Customer(int customerId, String customerName, String address, String postalCode, String phone, int divisionId, String divisionName){
        this.id = customerId;
        this.name = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.totalAppointments = 0;
    }

    public int getTotalAppointments() {
        return totalAppointments;
    }

    public void setTotalAppointments(int totalAppointments) {
        this.totalAppointments = totalAppointments;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public int getId() {
        return id;
    }

    public void setCustomerId(int customerId) {
        this.id = customerId;
    }

    public String getName() {
        return name;
    }

    public void setCustomerName(String customerName) {
        this.name = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
}
