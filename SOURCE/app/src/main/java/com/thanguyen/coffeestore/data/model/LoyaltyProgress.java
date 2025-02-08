package com.thanguyen.coffeestore.data.model;

public class LoyaltyProgress {
    private int completedCups; // Represents the current number of completed cups
    private int totalCups; // Represents the maximum number of cups for the loyalty goal

    public LoyaltyProgress(int completedCups, int totalCups) {
        this.completedCups = completedCups;
        this.totalCups = totalCups;
    }

    public int getCompletedCups() {
        return completedCups;
    }

    public void increaseCompletedCups(int completedCups) {
        this.completedCups += completedCups;
    }

    public void resetCompletedCups() {
        this.completedCups = 0;
    }

    public int getTotalCups() {
        return totalCups;
    }

    public void setTotalCups(int totalCups) {
        this.totalCups = totalCups;
    }
}
