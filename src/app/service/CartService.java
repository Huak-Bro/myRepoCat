// src/main/java/app/service/CartService.java
package app.service;

import app.model.Product;
import java.util.LinkedHashMap;
import java.util.Map;

public class CartService {
    private final Map<Product, Integer> items = new LinkedHashMap<>();

    public void add(Product p, int qty) { items.put(p, items.getOrDefault(p, 0) + qty); }
    public void remove(Product p) { items.remove(p); }
    public double total() {
        return items.entrySet().stream()
                .mapToDouble(e -> e.getKey().getPrice() * e.getValue()).sum();
    }
    public Map<Product, Integer> getItems() { return items; }
    public void clear() { items.clear(); }
}