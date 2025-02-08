package com.thanguyen.coffeestore;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.thanguyen.coffeestore.databinding.ActivityMainBinding;
import com.thanguyen.coffeestore.ui.loyaltycard.LoyaltyProgressViewModel;
import com.thanguyen.coffeestore.viewmodel.BeverageOrdersViewModel;
import com.thanguyen.coffeestore.viewmodel.ProfileViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadData();
    }

    private void loadData() {
        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.fetchProfileData();

        LoyaltyProgressViewModel loyaltyProgressViewModel = new ViewModelProvider(this).get(LoyaltyProgressViewModel.class);
        loyaltyProgressViewModel.fetchLoyaltyProgress();

        BeverageOrdersViewModel beverageOrdersViewModel = new ViewModelProvider(this).get(BeverageOrdersViewModel.class);
        beverageOrdersViewModel.fetchBeverageOrders();
    }
}