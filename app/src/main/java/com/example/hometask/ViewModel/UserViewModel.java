package com.example.hometask.ViewModel;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.hometask.Models.User;
import com.example.hometask.Repository.UserRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserViewModel extends ViewModel {

    private final MutableLiveData<List<User>> users = new MutableLiveData<>();
    private final MutableLiveData<List<User>> favoritesLiveData = new MutableLiveData<>(new ArrayList<>());
    private final UserRepository userRepository;


    public UserViewModel() {
        userRepository = new UserRepository();
        fetchUsers();  // Initial load of users
    }
    public LiveData<List<User>> getFavoritesLiveData() {
        return favoritesLiveData;
    }

    public void addToFavorites(User user) {
        List<User> currentFavorites = new ArrayList<>(favoritesLiveData.getValue());
        if (currentFavorites.size() < 10 && !currentFavorites.contains(user)) {
            currentFavorites.add(user);
            favoritesLiveData.setValue(currentFavorites);
        }
    }

    public void removeFromFavorites(User user) {
        List<User> currentFavorites = new ArrayList<>(favoritesLiveData.getValue());
        if (currentFavorites.contains(user)) {
            currentFavorites.remove(user);
            favoritesLiveData.setValue(currentFavorites);
        }
    }

    // Fetch users and observe changes
    public void fetchUsers() {
        userRepository.getUsersLiveData().observeForever(usersList -> {
            if (usersList != null) {
                Log.d("UserViewModel", "Fetched users: " + usersList.size());
                users.postValue(usersList);
            } else {
                Log.d("UserViewModel", "User list is null!");
            }
        });

        // Ensure repository fetches users
        userRepository.fetchUsers();
    }

    // Expose LiveData to the UI
    public LiveData<List<User>> getUsers() {
        return users;
    }

    public void sortUsersByDateOfBirth() {
        List<User> usersList = users.getValue(); // Use 'users' instead of 'usersLiveData'
        if (usersList != null && !usersList.isEmpty()) {
            Log.d("UserViewModel", "Sorting users by date of birth (youngest to oldest)...");

            Collections.sort(usersList, (u1, u2) -> {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Date dob1 = sdf.parse(u1.getDob());
                    Date dob2 = sdf.parse(u2.getDob());
                    return dob2.compareTo(dob1); // Reverse order for youngest first
                } catch (ParseException e) {
                    Log.e("UserViewModel", "Error parsing date of birth", e);
                    return 0;
                }
            });

            users.setValue(new ArrayList<>(usersList)); // Update LiveData with sorted list

            // Log the sorted list for verification
            for (User user : usersList) {
                Log.d("UserViewModel", "Sorted User: " + user.getFirst_name() + " " + user.getLast_name() + " - " + user.getDob());
            }
        } else {
            Log.d("UserViewModel", "User list is null or empty, cannot sort.");
        }
    }

}
