package com.thanguyen.coffeestore.ui.loyaltycard;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thanguyen.coffeestore.R;
import com.thanguyen.coffeestore.databinding.FragmentLoyaltyProgressBinding;

public class LoyaltyProgressFragment extends Fragment {

    private FragmentLoyaltyProgressBinding binding;

    public static LoyaltyProgressFragment newInstance() {
        return new LoyaltyProgressFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoyaltyProgressBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get references to the views
        LoyaltyProgressViewModel viewModel = new ViewModelProvider(requireActivity()).get(LoyaltyProgressViewModel.class);
        TextView loyaltyProgressTextView = binding.loyaltyProgress;
        LinearLayout cardCupList = binding.cardCupList;

        // Observe the loyalty progress and update the UI
        viewModel.getLoyaltyProgress().observe(getViewLifecycleOwner(), loyaltyProgress -> {
            int completedCups = loyaltyProgress.getCompletedCups();
            int totalCups = loyaltyProgress.getTotalCups();
            updateProgressText(loyaltyProgressTextView, completedCups, totalCups);
            updateCupImages(cardCupList, completedCups, totalCups);
        });
    }

    /**
     * Updates the loyalty progress text in the TextView.
     */
    private void updateProgressText(TextView textView, int completedCups, int totalCups) {
        String progressText = String.format("%d / %d", completedCups, totalCups);
        textView.setText(progressText);
    }

    /**
     * Dynamically updates the coffee cup images in the LinearLayout.
     */
    private void updateCupImages(LinearLayout cardCupList, int completedCups, int totalCups) {
        // Clear the previous cup views
        cardCupList.removeAllViews();

        // Loop through the total cups and create ImageViews for each
        for (int i = 0; i < completedCups; i++) {
            ImageView cupImage = createCupImage(R.drawable.ic_coffee_full);
            cardCupList.addView(cupImage);
        }
        for (int i = completedCups; i < totalCups; i++) {
            ImageView cupImage = createCupImage(R.drawable.ic_coffee_empty);
            cardCupList.addView(cupImage);
        }
    }

    private ImageView createCupImage(int cupImageResId) {
        // Create a new ImageView for the cup
        ImageView cupImage = new ImageView(getContext());

        cupImage.setImageResource(cupImageResId);

        // Set layout parameters for the ImageView
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                0, // Width is determined by layout_weight
                ViewGroup.LayoutParams.MATCH_PARENT, // Match height
                1); // Weight of 1 for even distribution
        layoutParams.setMargins(0, 0, 8, 0); // Optional margin between cups

        cupImage.setLayoutParams(layoutParams);

        return cupImage;
    }
}