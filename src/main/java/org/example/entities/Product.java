package org.example.entities;
import java.util.UUID;

public class Product {
    private UUID uuid;
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(UUID uuid, String name, double price, int quantity) {
        this.uuid = uuid;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters e setters
    public UUID getUuid() { return uuid; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public void setUuid(UUID uuid) { this.uuid = uuid; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}

