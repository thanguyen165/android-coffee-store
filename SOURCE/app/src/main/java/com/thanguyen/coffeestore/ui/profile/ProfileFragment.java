package com.thanguyen.coffeestore.ui.profile;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thanguyen.coffeestore.R;
import com.thanguyen.coffeestore.databinding.FragmentProfileBinding;
import com.thanguyen.coffeestore.viewmodel.ProfileViewModel;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProfileViewModel profileViewModel;
    Map<String, Consumer<String>> updateViewModelActions = new HashMap<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        profileViewModel =
                new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupListeners();
    }

    @Override
    public void onStart() {
        super.onStart();

        // Show the BottomNavigationView in the Activity when this fragment is displayed
        if (getActivity() != null) {
            BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
            navView.setVisibility(View.VISIBLE);
        }
    }

    private void setupListeners() {
        TextView profileFullNameTextView = binding.profileFullName;
        TextView profilePhoneNumberTextView = binding.profilePhoneNumber;
        TextView profileEMailTextView = binding.profileEMail;
        TextView profileAddressTextView = binding.profileAddress;

        profileViewModel.getProfile().observe(requireActivity(), profile -> {
            profileFullNameTextView.setText(profile.getFullName());
            profilePhoneNumberTextView.setText(profile.getPhoneNumber());
            profileEMailTextView.setText(profile.getEmail());
            profileAddressTextView.setText(profile.getAddress());
        });

        initializeUpdateViewModelActions();

        View editFullName = binding.editFullName;
        View editPhoneNumber = binding.editPhoneNumber;
        View editEMail = binding.editEMail;
        View editAddress = binding.editAddress;

        editFullName.setOnClickListener(view -> showEditDialog("Full Name", profileFullNameTextView));
        editPhoneNumber.setOnClickListener(view -> showEditDialog("Phone Number", profilePhoneNumberTextView));
        editEMail.setOnClickListener(view -> showEditDialog("Email", profileEMailTextView));
        editAddress.setOnClickListener(view -> showEditDialog("Address", profileAddressTextView));
    }

    private void initializeUpdateViewModelActions() {
        updateViewModelActions.put("Full Name", (value) -> profileViewModel.updateName(value));
        updateViewModelActions.put("Phone Number", (value) -> profileViewModel.updatePhoneNumber(value));
        updateViewModelActions.put("Email", (value) -> profileViewModel.updateEmail(value));
        updateViewModelActions.put("Address", (value) -> profileViewModel.updateAddress(value));
    }

    private void showEditDialog(String title, TextView textView) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());
        dialogBuilder.setTitle("Edit " + title);

        final EditText inputBox = new EditText(requireContext());
        inputBox.setText(textView.getText().toString());
        dialogBuilder.setView(inputBox);

        dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        dialogBuilder.setPositiveButton("Save", (dialog, which) -> {
            String newValue = inputBox.getText().toString().trim();
            if (!newValue.isEmpty()) {
                Consumer<String> updateAction = updateViewModelActions.get(title);
                if (updateAction != null) {
                    updateAction.accept(newValue);
                }
            }
        });

        dialogBuilder.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}