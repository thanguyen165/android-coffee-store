package com.thanguyen.coffeestore.data.repository;

import com.thanguyen.coffeestore.R;
import com.thanguyen.coffeestore.data.model.Beverage;
import com.thanguyen.coffeestore.data.model.BeverageOrder;

import java.util.ArrayList;
import java.util.List;

public class BeverageOrderRepository {
    List<BeverageOrder> beverageOrderList;

    public BeverageOrderRepository() {
        beverageOrderList = new ArrayList<>();
    }

    public List<BeverageOrder> fetchBeverageOrders() {
        List<BeverageOrder> beverageOrderList = new ArrayList<>();

        BeverageOrder beverageOrder = new BeverageOrder(
                new Beverage("Americano", 3.0, 1.0, R.drawable.beverage_americano),
                2, 1, "hot", 0, "full ice");
        beverageOrderList.add(beverageOrder);

        return beverageOrderList;
    }

    public List<BeverageOrder> getBeverageOrderList() {
        return beverageOrderList;
    }

    public void addBeverageOrders(List<BeverageOrder> beverageOrderList) {
        this.beverageOrderList.addAll(beverageOrderList);
    }


}
