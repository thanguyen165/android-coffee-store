package com.thanguyen.coffeestore.data.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/*
 * Implements Serializable to allow instances of BeverageOrder to be passed
 * between Android components (e.g., Activities or Fragments) via Bundles.
 * This enables the transfer of complex data objects through Intents or Fragment arguments.
 */

public class BeverageOrder implements Serializable {
    private Beverage selectedBeverage; // The specific beverage ordered (e.g., coffee, tea)
    private int quantity; // Number of cups ordered (e.g., 1, 2)
    private int shotCount; // Number of espresso shots (e.g., 1 for single shot, 2 for double shot)
    private String preparationType; // Type of preparation: "hot" or "iced"
    private int size; // Size of the beverage: "small", "medium", or "large"
    private String iceAmount; // Ice level: "less ice", "medium ice", or "full ice"
    private LocalDateTime orderTime;

    public BeverageOrder(Beverage selectedBeverage, int quantity, int shotCount,
                         String preparationType, int size, String iceAmount) {
        this.selectedBeverage = selectedBeverage;
        this.quantity = quantity;
        this.shotCount = shotCount;
        this.preparationType = preparationType;
        this.size = size;
        this.iceAmount = iceAmount;
        this.orderTime = LocalDateTime.now();
    }

    public Beverage getSelectedBeverage() {
        return selectedBeverage;
    }

    public void setSelectedBeverage(Beverage selectedBeverage) {
        this.selectedBeverage = selectedBeverage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getShotCount() {
        return shotCount;
    }

    public void setShotCount(int shotCount) {
        this.shotCount = shotCount;
    }

    public String getPreparationType() {
        return preparationType;
    }

    public void setPreparationType(String preparationType) {
        this.preparationType = preparationType;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getIceAmount() {
        return iceAmount;
    }

    public void setIceAmount(String iceAmount) {
        this.iceAmount = iceAmount;
    }

    public double getPrice() {
        double priceOneCup = (selectedBeverage.getBasePrice() + selectedBeverage.getPriceIncrementPerSize() * size);
        return priceOneCup * quantity;
    }

    public String getDetails() {
        String details = "";
        details += this.shotCount == 1 ? "Single" : "Double";
        details += " | ";
        details += this.preparationType;
        details += " | ";
        details += this.size == 0 ? "small" : this.size == 1 ? "medium" : this.size == 2 ? "large" : "weird";
        details += " | ";
        details += this.iceAmount;
        return details;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime() {
        orderTime = LocalDateTime.now();
    }
}
