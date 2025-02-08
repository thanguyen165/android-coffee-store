package com.thanguyen.coffeestore.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thanguyen.coffeestore.data.model.Profile;
import com.thanguyen.coffeestore.data.repository.ProfileRepository;

public class ProfileViewModel extends ViewModel {
    private final ProfileRepository profileRepository;
    private final MutableLiveData<Profile> profileMutableLiveData;


    public ProfileViewModel() {
        profileRepository = new ProfileRepository();
        profileMutableLiveData = new MutableLiveData<>();
    }

    public void fetchProfileData() {
        Profile profile = profileRepository.fetchProfileData();
        profileMutableLiveData.setValue(profile);
    }

    public LiveData<Profile> getProfile() {
        return profileMutableLiveData;
    }

    public void updateName(String name) {
        Profile currentProfile = profileMutableLiveData.getValue();
        if (currentProfile != null) {
            currentProfile.setFullName(name);
            profileMutableLiveData.setValue(currentProfile);
            profileRepository.updateProfileData(currentProfile);
        }
    }

    public void updateEmail(String email) {
        Profile currentProfile = profileMutableLiveData.getValue();
        if (currentProfile != null) {
            currentProfile.setEmail(email);
            profileMutableLiveData.setValue(currentProfile);
            profileRepository.updateProfileData(currentProfile);
        }
    }

    public void updatePhoneNumber(String phoneNumber) {
        Profile currentProfile = profileMutableLiveData.getValue();
        if (currentProfile != null) {
            currentProfile.setPhoneNumber(phoneNumber);
            profileMutableLiveData.setValue(currentProfile);
            profileRepository.updateProfileData(currentProfile);
        }
    }

    public void updateAddress(String address) {
        Profile currentProfile = profileMutableLiveData.getValue();
        if (currentProfile != null) {
            currentProfile.setAddress(address);
            profileMutableLiveData.setValue(currentProfile);
            profileRepository.updateProfileData(currentProfile);
        }
    }
}
