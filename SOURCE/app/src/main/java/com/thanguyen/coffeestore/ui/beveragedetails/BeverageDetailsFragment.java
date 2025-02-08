package com.thanguyen.coffeestore.ui.beveragedetails;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thanguyen.coffeestore.R;
import com.thanguyen.coffeestore.data.model.Beverage;
import com.thanguyen.coffeestore.data.model.BeverageOrder;
import com.thanguyen.coffeestore.databinding.FragmentBeverageDetailsBinding;
import com.thanguyen.coffeestore.ui.utils.OnSwipeTouchListener;

import java.util.HashMap;
import java.util.Map;

public class BeverageDetailsFragment extends Fragment {

    private BeverageDetailsViewModel mViewModel;
    private FragmentBeverageDetailsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(BeverageDetailsViewModel.class);

        binding = FragmentBeverageDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        // Hide the BottomNavigationView in the Activity when this fragment is displayed
        if (getActivity() != null) {
            BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
            navView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            handleDataFromBundle(bundle);
        }

        setupListeners();
    }

    private void handleDataFromBundle(Bundle bundle) {
        Beverage selectedBeverage = (Beverage) bundle.getSerializable("selected_beverage");

        if (selectedBeverage != null) {
            BeverageOrder beverageOrder = new BeverageOrder(selectedBeverage,
                    3, 1, "Hot", 2, "Full ice");
            mViewModel.setBeverageOrder(beverageOrder);

            binding.detailBeverageImageResId.setImageResource(selectedBeverage.getImageResId());
            binding.detailBeverageName.setText(selectedBeverage.getName());
        }
    }

    private void setupListeners() {
        setupBackButtonListener();
        setupCartButtonListener();
        setupQuantityListeners();
        setupShotCountListeners();
        setupPreparationTypeListeners();
        setupSizeListeners();
        setupIceAmountListeners();
        setupBeverageCostListeners();
        setupAddToCartListener();
    }

    private void setupBackButtonListener() {
        binding.back.setOnClickListener(view -> {
            // Show nav bar created in Activity, because this fragment turned it of above
            if (getActivity() != null) {
                BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
                navView.setVisibility(View.VISIBLE);
            }

            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();
        });
    }

    private void setupCartButtonListener() {
        binding.cart.setOnClickListener(view -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.navigation_cart);
        });
    }

    private void setupAddToCartListener() {
        binding.addToCart.setOnClickListener(view -> {
            BeverageOrder beverageOrder = mViewModel.getBeverageOrder().getValue();
            Bundle bundle = new Bundle();
            bundle.putSerializable("selected_beverageOrder", beverageOrder);

            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.navigation_cart, bundle);
        });
    }

    private void setupQuantityListeners() {
        mViewModel.getBeverageOrder().observe(getViewLifecycleOwner(), beverageOrder -> {
            binding.detailQuantity.setText(String.valueOf(beverageOrder.getQuantity()));
        });

        binding.decreaseOrderQuantity.setOnClickListener(view -> {
            int quantity = mViewModel.getBeverageOrder().getValue().getQuantity();
            if (quantity >= 2) {
                quantity--;
            }
            mViewModel.setQuantity(quantity);
        });

        binding.increaseOrderQuantity.setOnClickListener(view -> {
            int quantity = mViewModel.getBeverageOrder().getValue().getQuantity();
            quantity++;
            mViewModel.setQuantity(quantity);
        });
    }

    private void setupShotCountListeners() {
        Map<Object, Button> options = new HashMap<>();
        options.put(1, binding.detailShotSingle);
        options.put(2, binding.detailShotDouble);

        onShotClicked(options, binding.detailShotSingle, () -> mViewModel.setShotCount(1));
        for (Map.Entry<Object, Button> entry : options.entrySet()) {
            int shotCount = (int) entry.getKey();
            Button button = entry.getValue();

            button.setOnClickListener(view -> {
                onShotClicked(options, button, () -> mViewModel.setShotCount(shotCount));
            });
        }
    }

    private void onShotClicked(Map<Object, Button> options, Button selectedOption, Runnable runnable) {
        if (runnable != null) {
            runnable.run();
        }

        for (Map.Entry<Object, Button> entry : options.entrySet()) {
            Button button = (Button) entry.getValue();
            int colorResId = (button == selectedOption) ? R.color.light_silver : R.color.ghost_white;

            button.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), colorResId));
        }
    }

    private void setupPreparationTypeListeners() {
        Map<Object, ImageView> options = new HashMap<>();
        options.put("Hot", binding.detailHot);
        options.put("Iced", binding.detailIced);

        // Set initial state for "Hot"
        chooseItem(options, binding.detailIced, () -> mViewModel.setPreparationType("Iced"));

        // Set onClickListeners dynamically for each option
        for (Map.Entry<Object, ImageView> entry : options.entrySet()) {
            String preparationType = (String) entry.getKey();
            ImageView imageView = entry.getValue();

            imageView.setOnClickListener(view -> {
                chooseItem(options, imageView, () -> mViewModel.setPreparationType(preparationType));
            });
        }
    }

    private void setupSizeListeners() {
        Map<Object, ImageView> options = new HashMap<>();
        options.put(0, binding.detailSmall);
        options.put(1, binding.detailMedium);
        options.put(2, binding.detailBig);

        // Set initial state for "Big"
        chooseItem(options, binding.detailSmall, () -> mViewModel.setSize(2));

        // Set onClickListeners dynamically for each option
        for (Map.Entry<Object, ImageView> entry : options.entrySet()) {
            int size = (int) entry.getKey();
            ImageView imageView = entry.getValue();

            imageView.setOnClickListener(view -> {
                chooseItem(options, imageView, () -> mViewModel.setSize(size));
            });
        }
    }

    private void setupIceAmountListeners() {
        Map<Object, ImageView> options = new HashMap<>();
        options.put("Less ice", binding.detailIceLevelOne);
        options.put("Medium ice", binding.detailIceLevelTwo);
        options.put("Full ice", binding.detailIceLevelThree);

        // Set initial state for "Less ice"
        chooseItem(options, binding.detailIceLevelOne, () -> mViewModel.setIceAmount("Less ice"));

        // Set onClickListeners dynamically for each option
        for (Map.Entry<Object, ImageView> entry : options.entrySet()) {
            String iceAmount = (String) entry.getKey();
            ImageView imageView = entry.getValue();

            imageView.setOnClickListener(view -> {
                chooseItem(options, imageView, () -> mViewModel.setIceAmount(iceAmount));
            });
        }
    }

    private void setupBeverageCostListeners() {
        mViewModel.getBeverageOrder().observe(getViewLifecycleOwner(), beverageOrder -> {
            if (beverageOrder != null) {
                binding.detailCost.setText(String.format("$%.2f", beverageOrder.getPrice()));
            }
        });
    }

    /**
     * Sets the preparation type and updates button styles based on the selection.
     */
    private void chooseItem(Map<Object, ImageView> options, ImageView selectedImageView, Runnable runnable) {
        if (runnable != null) {
            runnable.run();
        }
        changeClickedStyle(options, selectedImageView);
    }

    /**
     * Change the style of the clicked button and the other button.
     */
    private void changeClickedStyle(Map<Object, ImageView> buttons, ImageView selectedImageView) {
        for (Map.Entry<Object, ImageView> entry : buttons.entrySet()) {
            int colorResId = (entry.getValue() == selectedImageView) ? R.color.dark_slate_gray : R.color.light_silver;
            applyButtonTint(entry.getValue(), colorResId);
        }
    }

    private void applyButtonTint(ImageView imageView, int colorResId) {
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof VectorDrawable) {
            VectorDrawable vectorDrawable = (VectorDrawable) drawable;
            vectorDrawable.setTint(ContextCompat.getColor(getContext(), colorResId));
            imageView.setImageDrawable(vectorDrawable);
        }
    }
}