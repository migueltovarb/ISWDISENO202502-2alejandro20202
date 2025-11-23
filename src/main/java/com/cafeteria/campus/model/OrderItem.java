package com.cafeteria.campus.model;

public class OrderItem {

    private String productId;
    private String name;       // 🔥 AGREGADO
    private int quantity;
    private double unitPrice;

    public OrderItem() {}

    public OrderItem(String productId, String name, int quantity, double unitPrice) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {     // 🔥 AGREGADO
        return name;
    }

    public void setName(String name) {   // 🔥 AGREGADO
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
