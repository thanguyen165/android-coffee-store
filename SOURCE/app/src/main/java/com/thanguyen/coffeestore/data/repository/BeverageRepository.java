package com.thanguyen.coffeestore.data.repository;

import com.thanguyen.coffeestore.R;
import com.thanguyen.coffeestore.data.model.Beverage;

import java.util.ArrayList;

public class BeverageRepository {
    ArrayList<Beverage> beveragesList;

    public ArrayList<Beverage> fetchBeverages() {
        ArrayList<Beverage> beveragesList = new ArrayList<>();

        Beverage americano = new Beverage("Americano", 3.0, 1.0, R.drawable.beverage_americano);
        beveragesList.add(americano);

        Beverage cappuccino = new Beverage("Cappuccino", 3.5, 1.2, R.drawable.beverage_cappuccino);
        beveragesList.add(cappuccino);

        Beverage mocha = new Beverage("Mocha", 4.0, 1.5, R.drawable.beverage_mocha);
        beveragesList.add(mocha);

        Beverage flatWhite = new Beverage("Flat White", 3.8, 1.3, R.drawable.beverage_flat_white);
        beveragesList.add(flatWhite);

        this.beveragesList = beveragesList;
        return this.beveragesList;
    }
}
