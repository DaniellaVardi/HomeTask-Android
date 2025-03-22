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
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final UserRepository userRepository;
    private int currentPage = 1;  // Track the current page for loading
    private final int pageSize = 20; // The number of users per page


    public UserViewModel() {
        userRepository = new UserRepository();
        fetchUsers(currentPage,pageSize);  // Initial fetch
    }

    // Fetch users from the repository for the given page
    public void fetchUsers(int page, int pageSize) {
        userRepository.getUsersLiveData().observeForever(usersList -> {
            if (usersList != null) {
                if (page == 1) {
                    // First load
                    users.postValue(usersList);
                } else {
                    // Append the new users to the list
                    List<User> currentUsers = users.getValue();
                    if (currentUsers != null) {
                        currentUsers.addAll(usersList);
                        users.setValue(currentUsers);
                    }
                }
                Log.d("UserViewModel", "Fetched users: " + usersList.size());
            } else {
                Log.d("UserViewModel", "User list is null!");
                errorMessage.postValue("Failed to fetch users!");
            }
        });

        userRepository.fetchUsers(page);  // Pass the page number to repository
    }

    // Expose LiveData to the UI
    public LiveData<List<User>> getUsers() {
        return users;
    }

    // Expose error messages to the UI
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    // Sorting users by date of birth from youngest to oldest
    public void sortUsersByDateOfBirth() {
        List<User> usersList = users.getValue();
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

            // Update LiveData with sorted users
            users.setValue(new ArrayList<>(usersList));

            // Log the sorted list for verification
            for (User user : usersList) {
                Log.d("UserViewModel", "Sorted User: " + user.getFirst_name() + " " + user.getLast_name() + " - " + user.getDob());
            }
        } else {
            Log.d("UserViewModel", "User list is null or empty, cannot sort.");
        }
    }
}
