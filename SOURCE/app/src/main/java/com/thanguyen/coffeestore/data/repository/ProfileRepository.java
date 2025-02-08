package com.thanguyen.coffeestore.data.repository;

import com.thanguyen.coffeestore.data.model.Profile;

public class ProfileRepository {

    private Profile currentProfile;

    public Profile fetchProfileData() {
        currentProfile = new Profile("Anderson",
                    "+60134589525",
                    "Anderson@email.com ",
                    "3 Addersion Court, Chino Hills, HO56824, United State");
        return currentProfile;
    }

    public void updateProfileData(Profile profile) {
        currentProfile = profile;
    }
}
