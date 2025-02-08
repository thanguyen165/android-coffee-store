package com.thanguyen.coffeestore.ui.beveragedetails;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thanguyen.coffeestore.data.model.BeverageOrder;

public class BeverageDetailsViewModel extends ViewModel {
    private MutableLiveData<BeverageOrder> beverageOrderMutableLiveData;

    public BeverageDetailsViewModel() {
        beverageOrderMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<BeverageOrder> getBeverageOrder() {
        return beverageOrderMutableLiveData;
    }

    public void setBeverageOrder(BeverageOrder beverageOrder) {
        beverageOrderMutableLiveData.setValue(beverageOrder);
    }

    public void setQuantity(int quantity) {
        BeverageOrder beverageOrder = beverageOrderMutableLiveData.getValue();
        if (beverageOrder != null && quantity >= 1) {
            beverageOrder.setQuantity(quantity);
            beverageOrderMutableLiveData.setValue(beverageOrder);
        }
    }

    public void setShotCount(int shotCount) {
        BeverageOrder beverageOrder = beverageOrderMutableLiveData.getValue();
        if (beverageOrder != null) {
            beverageOrder.setShotCount(shotCount);
            beverageOrderMutableLiveData.setValue(beverageOrder);
        }
    }

    public void setPreparationType(String preparationType) {
        BeverageOrder beverageOrder = beverageOrderMutableLiveData.getValue();
        if (beverageOrder != null) {
            beverageOrder.setPreparationType(preparationType);
            beverageOrderMutableLiveData.setValue(beverageOrder);
        }
    }

    public void setSize(int size) {
        BeverageOrder beverageOrder = beverageOrderMutableLiveData.getValue();
        if (beverageOrder != null) {
            beverageOrder.setSize(size);
            beverageOrderMutableLiveData.setValue(beverageOrder);
        }
    }

    public void setIceAmount(String iceAmount) {
        BeverageOrder beverageOrder = beverageOrderMutableLiveData.getValue();
        if (beverageOrder != null) {
            beverageOrder.setIceAmount(iceAmount);
            beverageOrderMutableLiveData.setValue(beverageOrder);
        }
    }
}