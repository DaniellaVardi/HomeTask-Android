package com.example.hometask.Network;

import com.example.hometask.Models.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("users")
    Call<List<User>> getUsers(
            @Query("size") int size,
            @Query("page") int page  // Added the page parameter for pagination
    );
}
