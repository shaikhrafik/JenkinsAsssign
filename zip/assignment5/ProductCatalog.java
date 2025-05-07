package assignment5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductCatalog {
    private Map<String, Product> products;

    public ProductCatalog() {
        this.products = new HashMap<>();
    }

    public void addProduct(Product product) {
        products.put(product.getProductID(), product);
    }

    public Product getProduct(String productID) {
        return products.get(productID);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public List<Product> getProductsSortedByPrice() {
        List<Product> productList = new ArrayList<>(products.values());
        Collections.sort(productList);
        return productList;
    }

    public List<Product> getProductsSortedByName() {
        List<Product> productList = new ArrayList<>(products.values());
        Collections.sort(productList, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getName().compareTo(p2.getName());
            }
        });
        return productList;
    }

    public List<Product> getProductsSortedByStock() {
        List<Product> productList = new ArrayList<>(products.values());
        Collections.sort(productList, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return Integer.compare(p2.getStock(), p1.getStock());
            }
        });
        return productList;
    }
}
