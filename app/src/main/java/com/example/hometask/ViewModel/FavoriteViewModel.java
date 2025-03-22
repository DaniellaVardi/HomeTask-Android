package com.example.hometask.ViewModel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hometask.Models.User;

import java.util.ArrayList;
import java.util.List;

public class FavoriteViewModel extends ViewModel {

    private final MutableLiveData<List<User>> favoriteUsers = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<String> favoriteListFullEvent = new MutableLiveData<>();
    private final int MAX_FAVORITES = 10;

    // Get the list of favorite users
    public LiveData<List<User>> getFavoritesLiveData() {
        return favoriteUsers;
    }

    // Get the event for when the favorites list is full
    public MutableLiveData<String> getFavoriteListFullEvent() {
        return favoriteListFullEvent;
    }

    // Add a user to favorites
    public void addToFavorites(User user) {
        List<User> currentFavorites = favoriteUsers.getValue();

        if (currentFavorites != null) {
            boolean isUserAlreadyFavorite = false;

            // Check if the user is already in the favorites list
            for (User favoriteUser : currentFavorites) {
                if (favoriteUser.getId() == user.getId()) {
                    isUserAlreadyFavorite = true;
                    break;
                }
            }

            if (isUserAlreadyFavorite) {
                // Trigger the event if the user is already in the favorites list
                favoriteListFullEvent.setValue("This user is already in your favorites.");
            } else if (currentFavorites.size() < MAX_FAVORITES) {
                // Add the user if there is space in the favorites list
                currentFavorites.add(user);
                favoriteUsers.setValue(currentFavorites);
            } else {
                // Trigger the event when the list is full
                favoriteListFullEvent.setValue("Your favorites list is full. You can only have " + MAX_FAVORITES + " favorites.");
            }
        }
    }

    // Remove a user from favorites
    public void removeFromFavorites(User user) {
        List<User> currentFavorites = favoriteUsers.getValue();
        if (currentFavorites != null) {
            currentFavorites.remove(user);
            favoriteUsers.setValue(currentFavorites);
        }
    }

    // Check if a user is already in the favorites list
    public boolean isUserFavorite(User user) {
        List<User> currentFavorites = favoriteUsers.getValue();
        return currentFavorites != null && currentFavorites.contains(user);
    }
}
