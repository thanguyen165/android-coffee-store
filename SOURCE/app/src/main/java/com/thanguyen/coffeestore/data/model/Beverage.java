package com.thanguyen.coffeestore.data.model;

import java.io.Serializable;

/*
 * Implements Serializable to allow instances of Beverage to be passed
 * between Android components (e.g., Activities or Fragments) via Bundles.
 * This enables the transfer of complex data objects through Intents or Fragment arguments.
 */

public class Beverage implements Serializable {
    private String name;
    private double basePrice;
    private double priceIncrementPerSize;
    private int imageResourceId;

    public Beverage(String name, double basePrice, double priceIncrementPerSize, int imageResourceId) {
        this.name = name;
        this.basePrice = basePrice;
        this.priceIncrementPerSize = priceIncrementPerSize;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public double getPriceIncrementPerSize() {
        return priceIncrementPerSize;
    }

    public int getImageResId() {
        return imageResourceId;
    }
}
