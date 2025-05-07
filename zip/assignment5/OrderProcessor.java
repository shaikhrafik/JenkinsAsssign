package assignment5;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class OrderProcessor {
    private PriorityQueue<Order> orderQueue;

    public OrderProcessor() {
        this.orderQueue = new PriorityQueue<>();
    }

    public Order createOrder(Customer customer, ShoppingCart cart, int priority) {
        if (cart.getItems().isEmpty()) {
            System.out.println("Cannot create an order with an empty cart");
            return null;
        }

        List<OrderItem> finalItems = new ArrayList<>();
        boolean allItemsAvailable = true;

        for (OrderItem item : cart.getItems().values()) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();

            if (product.getStock() < quantity) {
                System.out.println("Not enough stock for " + product.getName());
                allItemsAvailable = false;
                break;
            }

            finalItems.add(new OrderItem(product, quantity));
        }

        if (!allItemsAvailable) {
            return null;
        }

        for (OrderItem item : finalItems) {
            Product product = item.getProduct();
            product.updateStock(item.getQuantity());
        }

        String orderID = "ORD-" + System.currentTimeMillis();
        Order order = new Order(orderID, customer, finalItems, priority);
        
        orderQueue.add(order);
        
        cart.clearCart();
        
        System.out.println("Order created successfully: " + orderID);
        return order;
    }

    public Order processNextOrder() {
        return orderQueue.poll();
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>(orderQueue);
        return orders;
    }

    public int getPendingOrderCount() {
        return orderQueue.size();
    }
}