package assignment5;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order implements Comparable<Order> {
    private String orderID;
    private Customer customer;
    private List<OrderItem> items;
    private LocalDateTime orderTime;
    private int priority;

    public Order(String orderID, Customer customer, List<OrderItem> items, int priority) {
        this.orderID = orderID;
        this.customer = customer;
        this.items = new ArrayList<>(items);
        this.orderTime = LocalDateTime.now();
        this.priority = priority;
    }

    public String getOrderID() {
        return orderID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public int getPriority() {
        return priority;
    }

    public double getTotalOrderValue() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    @Override
    public int compareTo(Order other) {
        return Integer.compare(other.priority, this.priority);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID='" + orderID + '\'' +
                ", customer=" + customer.getName() +
                ", items=" + items.size() +
                ", orderTime=" + orderTime +
                ", priority=" + priority +
                ", total=" + getTotalOrderValue() +
                '}';
    }
}
