package com.example.hometask.Repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.hometask.Models.User;
import com.example.hometask.Network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepository {

    private final MutableLiveData<List<User>> usersLiveData = new MutableLiveData<>();
    private final ApiService apiService;
    private static final int USERS_PER_PAGE = 20;  // Define how many users to load per page
    private int currentPage = 1;  // Track the current page

    public UserRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://random-data-api.com/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    // Fetch users with pagination (page number)
    public void fetchUsers(int page) {
        apiService.getUsers(USERS_PER_PAGE, page).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> newUsers = response.body();
                    if (page == 1) {
                        // First page load, set new list
                        usersLiveData.postValue(newUsers);
                    } else {
                        // Subsequent page loads, append to existing users list
                        List<User> currentUsers = usersLiveData.getValue();
                        if (currentUsers != null) {
                            currentUsers.addAll(newUsers);
                            usersLiveData.setValue(currentUsers);
                        }
                    }

                    Log.d("UserRepository", "Fetched " + newUsers.size() + " users from page " + page);
                    currentPage = page;  // Update the current page after a successful fetch
                } else {
                    Log.e("UserRepository", "API Response Failed");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("UserRepository", "API Call Failed: " + t.getMessage());
            }
        });
    }

    // Expose the LiveData to be observed
    public LiveData<List<User>> getUsersLiveData() {
        return usersLiveData;
    }
}
