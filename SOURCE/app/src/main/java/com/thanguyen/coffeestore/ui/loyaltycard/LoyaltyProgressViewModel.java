package com.thanguyen.coffeestore.ui.loyaltycard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thanguyen.coffeestore.data.model.LoyaltyProgress;
import com.thanguyen.coffeestore.data.repository.LoyaltyProgressRepository;

public class LoyaltyProgressViewModel extends ViewModel {
    private final LoyaltyProgressRepository loyaltyProgressRepository;
    private MutableLiveData<LoyaltyProgress> loyaltyProgress;

    public LoyaltyProgressViewModel() {
        loyaltyProgressRepository = new LoyaltyProgressRepository();
        loyaltyProgress = new MutableLiveData<>();
    }

    public void fetchLoyaltyProgress() {
        LoyaltyProgress progress = loyaltyProgressRepository.fetchLoyaltyProgress();
        loyaltyProgress.setValue(progress);
    }

    public LiveData<LoyaltyProgress> getLoyaltyProgress() {
        return loyaltyProgress;
    }

    public void increaseLoyaltyPoints(int points) {
        LoyaltyProgress currentProgress = loyaltyProgress.getValue();
        if (currentProgress != null) {
            currentProgress.increaseCompletedCups(points);
            loyaltyProgress.setValue(currentProgress);
        }
    }
}
