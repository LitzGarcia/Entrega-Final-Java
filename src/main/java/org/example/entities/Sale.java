package org.example.entities;

import java.util.List;
import java.util.UUID;

public class Sale {
    private UUID uuid;
    private UUID userId;
    private List<UUID> productIds;
    private double total;
    private String paymentMethod;

    public Sale(UUID userId, List<UUID> productIds, double total, String paymentMethod) {
        this.uuid = UUID.randomUUID();
        this.userId = userId;
        this.productIds = productIds;
        this.total = total;
        this.paymentMethod = paymentMethod;
    }


    public UUID getUuid() { return uuid; }
    public UUID getUserId() { return userId; }
    public List<UUID> getProductIds() { return productIds; }
    public double getTotal() { return total; }
    public String getPaymentMethod() { return paymentMethod; }
}

