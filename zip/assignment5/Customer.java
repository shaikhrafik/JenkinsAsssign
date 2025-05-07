package assignment5;

public class Customer {
    private String customerID;
    private String name;
    private String address;

    public Customer(String customerID, String name, String address) {
        this.customerID = customerID;
        this.name = name;
        this.address = address;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerID='" + customerID + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}