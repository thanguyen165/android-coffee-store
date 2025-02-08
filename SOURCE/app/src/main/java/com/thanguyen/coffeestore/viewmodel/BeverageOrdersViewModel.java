package com.thanguyen.coffeestore.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thanguyen.coffeestore.data.model.BeverageOrder;
import com.thanguyen.coffeestore.data.repository.BeverageOrderRepository;

import java.util.List;

public class BeverageOrdersViewModel extends ViewModel {
    private final MutableLiveData<List<BeverageOrder>> recentBeverageOrdersList;
    private final MutableLiveData<List<BeverageOrder>> allBeverageOrdersList;
    private final BeverageOrderRepository beverageOrderRepository;

    public BeverageOrdersViewModel() {
        recentBeverageOrdersList = new MutableLiveData<>();
        allBeverageOrdersList = new MutableLiveData<>();
        beverageOrderRepository = new BeverageOrderRepository();
    }

    public void fetchBeverageOrders() {
        List<BeverageOrder> allOrders = beverageOrderRepository.fetchBeverageOrders();
        allBeverageOrdersList.setValue(allOrders);
    }

    public MutableLiveData<List<BeverageOrder>> getRecentBeverageOrdersList() {
        return recentBeverageOrdersList;
    }

    public MutableLiveData<List<BeverageOrder>> getAllBeverageOrdersList() {
        return allBeverageOrdersList;
    }

    public void addBeverageOrders(List<BeverageOrder> beverageOrders) {
        for (BeverageOrder beverageOrder : beverageOrders) {
            beverageOrder.setOrderTime();
        }

        List<BeverageOrder> currentList = allBeverageOrdersList.getValue();
        if (currentList != null) {
            recentBeverageOrdersList.setValue(currentList);
            currentList.addAll(beverageOrders);
            allBeverageOrdersList.setValue(currentList);
            beverageOrderRepository.addBeverageOrders(beverageOrders);
        }
    }
}
