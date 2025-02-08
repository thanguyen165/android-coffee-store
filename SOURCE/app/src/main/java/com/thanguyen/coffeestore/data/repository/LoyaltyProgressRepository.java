package com.thanguyen.coffeestore.data.repository;

import com.thanguyen.coffeestore.data.model.LoyaltyProgress;

public class LoyaltyProgressRepository {
    public LoyaltyProgress fetchLoyaltyProgress() {
        return new LoyaltyProgress(4, 8);
    }
}
