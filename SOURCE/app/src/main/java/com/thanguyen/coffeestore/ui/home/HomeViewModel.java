package com.thanguyen.coffeestore.ui.home;

import android.icu.util.Calendar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thanguyen.coffeestore.data.model.Beverage;
import com.thanguyen.coffeestore.data.repository.BeverageRepository;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mGreetMessage;
    private final ArrayList<Beverage> beveragesList;

    public HomeViewModel() {
        mGreetMessage = new MutableLiveData<>();
        BeverageRepository beverageRepository = new BeverageRepository();
        beveragesList = beverageRepository.fetchBeverages();
        setGreetMessage();
    }

    private void setGreetMessage() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        if (hour >= 5 && hour < 12) {
            mGreetMessage.setValue("Good morning");
        } else if (hour >= 12 && hour < 18) {
            mGreetMessage.setValue("Good afternoon");
        } else {
            mGreetMessage.setValue("Good evening");
        }
    }

    public LiveData<String> getGreetMessage() {
        return mGreetMessage;
    }

    public ArrayList<Beverage> getBeveragesList() {
        return beveragesList;
    }
}