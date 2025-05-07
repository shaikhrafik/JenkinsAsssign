package assignment5;

public class ECommerceApp {
    public static void main(String[] args) {
        ProductCatalog catalog = new ProductCatalog();
        
        catalog.addProduct(new Product("P001", "Laptop", 999.99, 10));
        catalog.addProduct(new Product("P002", "Smartphone", 499.99, 20));
        catalog.addProduct(new Product("P003", "Headphones", 99.99, 50));
        catalog.addProduct(new Product("P004", "Tablet", 299.99, 15));
        catalog.addProduct(new Product("P005", "Smart Watch", 199.99, 30));
        
        Customer customer1 = new Customer("C001", "John Doe", "123 Main St");
        Customer customer2 = new Customer("C002", "Jane Smith", "456 Oak Ave");
        
        ShoppingCart cart1 = new ShoppingCart(customer1);
        ShoppingCart cart2 = new ShoppingCart(customer2);
        
        cart1.addProduct(catalog.getProduct("P001"), 1);
        cart1.addProduct(catalog.getProduct("P003"), 2);
        
        cart2.addProduct(catalog.getProduct("P002"), 1);
        cart2.addProduct(catalog.getProduct("P005"), 1);
        
        System.out.println("\nShopping Carts:");
        System.out.println(cart1);
        System.out.println("\n" + cart2);
        
        OrderProcessor orderProcessor = new OrderProcessor();
        
        Order order1 = orderProcessor.createOrder(customer1, cart1, 2);
        Order order2 = orderProcessor.createOrder(customer2, cart2, 1);
        
        System.out.println("\nProcessing Orders:");
        while (orderProcessor.getPendingOrderCount() > 0) {
            Order processedOrder = orderProcessor.processNextOrder();
            System.out.println("Processed: " + processedOrder);
        }
        
        System.out.println("\nProducts sorted by price:");
        for (Product p : catalog.getProductsSortedByPrice()) {
            System.out.println(p);
        }
        
        System.out.println("\nProducts sorted by name:");
        for (Product p : catalog.getProductsSortedByName()) {
            System.out.println(p);
        }
        
        System.out.println("\nProducts sorted by stock:");
        for (Product p : catalog.getProductsSortedByStock()) {
            System.out.println(p);
        }
    }
}
