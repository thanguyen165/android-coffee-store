package com.thanguyen.coffeestore.ui.cart;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thanguyen.coffeestore.R;
import com.thanguyen.coffeestore.data.model.BeverageOrder;
import com.thanguyen.coffeestore.databinding.FragmentCartBinding;
import com.thanguyen.coffeestore.ui.loyaltycard.LoyaltyProgressViewModel;
import com.thanguyen.coffeestore.ui.utils.OnSwipeTouchListener;
import com.thanguyen.coffeestore.viewmodel.BeverageOrdersViewModel;

import java.util.List;

public class CartFragment extends Fragment {

    private CartViewModel mViewModel;
    private FragmentCartBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // CartViewModel is shared across multiple fragments/activities, so use 'requireActivity()'
        // to ensure the ViewModel is scoped to the Activity and not just this fragment
        mViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);

        binding = FragmentCartBinding.inflate(inflater, container, false);
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

        setupCartItemsList();
        setupListeners();
    }

    private void handleDataFromBundle(Bundle bundle) {
        BeverageOrder selectedBeverageOrder = (BeverageOrder) bundle.getSerializable("selected_beverageOrder");

        if (selectedBeverageOrder != null) {
            mViewModel.addBeverageOrder(selectedBeverageOrder);
        }
    }

    private void setupCartItemsList() {
        mViewModel.getBeverageOrdersList().observe(getViewLifecycleOwner(), beverageOrders -> {
            for (BeverageOrder beverageOrder : beverageOrders) {
                View view = createCartItemView(beverageOrder);
                binding.cartItemsList.addView(view);
            }
        });
    }

    private void setupListeners() {
        setupBackButtonListener();
        setupTotalPriceListener();
        setupCheckoutButtonListener();
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

    private void setupTotalPriceListener() {
        mViewModel.getBeverageOrdersList().observe(getViewLifecycleOwner(), beverageOrders -> {
            binding.cartTotalPrice.setText(String.format("$%.2f", mViewModel.getTotalPrice()));
        });
    }

    private void setupCheckoutButtonListener() {
        binding.cartCheckout.setOnClickListener(view -> {
            List<BeverageOrder> beverageOrderList = mViewModel.getBeverageOrdersList().getValue();
            if (beverageOrderList != null) {
                BeverageOrdersViewModel beverageOrdersViewModel = new ViewModelProvider(requireActivity()).get(BeverageOrdersViewModel.class);
                beverageOrdersViewModel.addBeverageOrders(beverageOrderList);

                LoyaltyProgressViewModel loyaltyProgressViewModel = new ViewModelProvider(requireActivity()).get(LoyaltyProgressViewModel.class);
                loyaltyProgressViewModel.increaseLoyaltyPoints(beverageOrderList.size());

                mViewModel.clearCart();
                binding.cartItemsList.removeAllViews();
            }

            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.navigation_order_success);
        });
    }

    private View createCartItemView(BeverageOrder beverageOrder) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_cart_fragment_cart, binding.getRoot(), false);

        ImageView beverageImageView = view.findViewById(R.id.cart_item_beverage_image_res_id);
        beverageImageView.setImageResource(beverageOrder.getSelectedBeverage().getImageResId());

        // Set image resource
        TextView beverageNameView = view.findViewById(R.id.cart_item_beverage_name);
        beverageNameView.setText(beverageOrder.getSelectedBeverage().getName());

        TextView beverageDetailsView = view.findViewById(R.id.cart_item_beverage_order_details);
        beverageDetailsView.setText(beverageOrder.getDetails());

        TextView beverageQuantityView = view.findViewById(R.id.cart_item_beverage_order_quantity);
        beverageQuantityView.setText(String.format("x%d", beverageOrder.getQuantity()));

        TextView beveragePriceView = view.findViewById(R.id.cart_item_price);
        beveragePriceView.setText(String.format("%.2f", beverageOrder.getPrice()));

        view.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                Toast.makeText(getContext(), "Beverage deleted!", Toast.LENGTH_SHORT).show();
                mViewModel.removeBeverageOrder(beverageOrder);
                binding.cartItemsList.removeView(view);
            }
        });

        return view;
    }
}