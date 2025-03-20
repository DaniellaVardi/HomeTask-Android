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

    public UserRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://random-data-api.com/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public void fetchUsers() {
        apiService.getUsers(100).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    usersLiveData.postValue(response.body());
                    Log.d("UserRepository", "Fetched " + response.body().size() + " users");
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

    public LiveData<List<User>> getUsersLiveData() {
        return usersLiveData;
    }
}
