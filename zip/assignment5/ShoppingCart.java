package assignment5;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Customer customer;
    private Map<String, OrderItem> items;

    public ShoppingCart(Customer customer) {
        this.customer = customer;
        this.items = new HashMap<>();
    }

    public void addProduct(Product product, int quantity) {
        if (product.getStock() < quantity) {
            System.out.println("Not enough stock for " + product.getName());
            return;
        }

        String productID = product.getProductID();
        if (items.containsKey(productID)) {
            OrderItem existingItem = items.get(productID);
            int newQuantity = existingItem.getQuantity() + quantity;
            items.put(productID, new OrderItem(product, newQuantity));
        } else {
            items.put(productID, new OrderItem(product, quantity));
        }
        
        System.out.println(quantity + " " + product.getName() + "(s) added to cart");
    }

    public void removeProduct(String productID) {
        if (items.containsKey(productID)) {
            OrderItem removed = items.remove(productID);
            System.out.println(removed.getProduct().getName() + " removed from cart");
        } else {
            System.out.println("Product not found in cart");
        }
    }

    public void updateQuantity(String productID, int newQuantity) {
        if (items.containsKey(productID)) {
            OrderItem item = items.get(productID);
            Product product = item.getProduct();
            
            if (product.getStock() < newQuantity) {
                System.out.println("Not enough stock for " + product.getName());
                return;
            }
            
            items.put(productID, new OrderItem(product, newQuantity));
            System.out.println(product.getName() + " quantity updated to " + newQuantity);
        } else {
            System.out.println("Product not found in cart");
        }
    }

    public Map<String, OrderItem> getItems() {
        return items;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getTotalCartValue() {
        double total = 0;
        for (OrderItem item : items.values()) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public void clearCart() {
        items.clear();
        System.out.println("Cart cleared");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Shopping Cart for ").append(customer.getName()).append(":\n");
        
        if (items.isEmpty()) {
            sb.append("Cart is empty\n");
        } else {
            for (OrderItem item : items.values()) {
                Product product = item.getProduct();
                sb.append(item.getQuantity())
                  .append(" x ")
                  .append(product.getName())
                  .append(" ($")
                  .append(product.getPrice())
                  .append(" each) = $")
                  .append(item.getTotalPrice())
                  .append("\n");
            }
            sb.append("Total: $").append(getTotalCartValue());
        }
        
        return sb.toString();
    }
}