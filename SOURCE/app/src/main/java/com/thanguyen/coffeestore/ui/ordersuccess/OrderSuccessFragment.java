package com.thanguyen.coffeestore.ui.ordersuccess;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thanguyen.coffeestore.R;
import com.thanguyen.coffeestore.databinding.FragmentOrderSuccessBinding;

public class OrderSuccessFragment extends Fragment {

    private FragmentOrderSuccessBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Hide the BottomNavigationView in the Activity when this fragment is displayed
        if (getActivity() != null) {
            BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
            navView.setVisibility(View.GONE);
        }

        binding = FragmentOrderSuccessBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupListeners();
    }

    private void setupListeners() {
        binding.trackMyOrderButton.setOnClickListener(view -> {
            NavController navController = Navigation.findNavController(view);
            // Create NavOptions to pop back to home if necessary
            NavOptions options = new NavOptions.Builder()
                    .setPopUpTo(R.id.navigation_home, true) // Clear the stack above Home
                    .setLaunchSingleTop(true) // Avoid stacking multiple ProfileFragment instances
                    .build();

            navController.navigate(R.id.navigation_profile, null, options);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}