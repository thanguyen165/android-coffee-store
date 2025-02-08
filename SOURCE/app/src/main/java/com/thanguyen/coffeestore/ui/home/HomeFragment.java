package com.thanguyen.coffeestore.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thanguyen.coffeestore.R;
import com.thanguyen.coffeestore.data.model.Beverage;
import com.thanguyen.coffeestore.databinding.FragmentHomeBinding;
import com.thanguyen.coffeestore.ui.loyaltycard.LoyaltyProgressFragment;
import com.thanguyen.coffeestore.viewmodel.ProfileViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    HomeViewModel homeViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupListeners();
        setupLoyaltyCard();
        setupBeverageMenu();
    }

    @Override
    public void onStart() {
        super.onStart();

        // Show nav bar created in Activity, because this fragment turned it of above
        if (getActivity() != null) {
            BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
            navView.setVisibility(View.VISIBLE);
        }
    }

    private void setupListeners() {
        setupGreetMessageListener();
        setupUsernameListener();
        setupProfileButtonListener();
        setupCartButtonListener();
    }

    private void setupLoyaltyCard() {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(binding.loyaltyCardFragmentHome.getId(), LoyaltyProgressFragment.newInstance());
        transaction.commit();
    }

    private void setupBeverageMenu() {
        GridLayout gridLayout = binding.beverageMenu;
        ArrayList<Beverage> beverageList = homeViewModel.getBeveragesList();

        for (Beverage beverage : beverageList) {
            View beverageItemView = setupBeverageMenuItemView(gridLayout, beverage);
            gridLayout.addView(beverageItemView);
        }
    }

    private View setupBeverageMenuItemView(GridLayout gridLayout, Beverage beverage) {
        // Inflate the layout for each beverage item
        View beverageItemView = LayoutInflater.from(getContext()).inflate(R.layout.beverage_menu_item_fragment_home, gridLayout, false);

        // Set up the ImageView and TextView from the inflated view
        ImageView beverageImageView = beverageItemView.findViewById(R.id.beverage_menu_item_image);
        TextView beverageTextView = beverageItemView.findViewById(R.id.beverage_menu_item_name);

        // Set the image and text for each beverage
        beverageImageView.setImageResource(beverage.getImageResId());
        beverageTextView.setText(beverage.getName());

        beverageItemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("selected_beverage", beverage);

            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.navigation_beverage_details, bundle);
        });

        return beverageItemView;
    }

    private void setupGreetMessageListener() {
        final TextView greetMessageTextView = binding.greetMessage;
        homeViewModel.getGreetMessage().observe(getViewLifecycleOwner(), greetMessageTextView::setText);
    }

    private void setupUsernameListener() {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        final TextView usernameTextView = binding.username;
        profileViewModel.getProfile().observe(requireActivity(), profile -> {
            usernameTextView.setText(profile.getFullName());
        });

    }

    private void setupProfileButtonListener() {
        final View profileViewIcon = binding.profile;
        profileViewIcon.setOnClickListener(view -> {
            NavController navController = Navigation.findNavController(view);
            // Create NavOptions to pop back to home if necessary
            NavOptions options = new NavOptions.Builder()
                    .setPopUpTo(R.id.navigation_home, true) // Clear the stack above Home
                    .setLaunchSingleTop(true) // Avoid stacking multiple ProfileFragment instances
                    .build();

            navController.navigate(R.id.navigation_profile, null, options);
        });
    }

    private void setupCartButtonListener() {
        binding.cart.setOnClickListener(view -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.navigation_cart);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}