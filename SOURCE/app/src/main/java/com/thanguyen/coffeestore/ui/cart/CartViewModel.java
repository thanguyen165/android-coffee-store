package com.thanguyen.coffeestore.ui.cart;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thanguyen.coffeestore.data.model.BeverageOrder;

import java.util.ArrayList;
import java.util.List;

public class CartViewModel extends ViewModel {
    private final MutableLiveData<List<BeverageOrder>> mutableLiveData;

    public CartViewModel() {
        mutableLiveData = new MutableLiveData<>(new ArrayList<>());
    }

    public MutableLiveData<List<BeverageOrder>> getBeverageOrdersList() {
        return mutableLiveData;
    }

    public void addBeverageOrder(BeverageOrder order) {
        List<BeverageOrder> currentList = mutableLiveData.getValue();
        if (currentList != null) {
            currentList.add(order);
            mutableLiveData.setValue(currentList);
        }
    }

    public void removeBeverageOrder(BeverageOrder order) {
        List<BeverageOrder> currentList = mutableLiveData.getValue();
        if (currentList != null) {
            currentList.remove(order);
            mutableLiveData.setValue(currentList);
        }
    }

    public void clearCart() {
        ArrayList<BeverageOrder> emptyList = new ArrayList<>();
        mutableLiveData.setValue(emptyList);
    }

    public double getTotalPrice() {
        List<BeverageOrder> currentList = mutableLiveData.getValue();
        if (currentList != null) {
            double totalPrice = 0;
            for (BeverageOrder beverageOrder : currentList) {
                totalPrice += beverageOrder.getPrice();
            }

            return totalPrice;
        }
        return 0;
    }
}
