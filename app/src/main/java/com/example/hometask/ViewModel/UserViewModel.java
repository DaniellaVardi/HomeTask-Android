package com.example.hometask.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.hometask.Models.User;
import com.example.hometask.Repository.UserRepository;
import java.util.ArrayList;
import java.util.List;

public class UserViewModel extends ViewModel {
    private final UserRepository repository;
    private final LiveData<List<User>> users;
    private final MutableLiveData<List<User>> favorites = new MutableLiveData<>(new ArrayList<>());

    public UserViewModel() {
        repository = new UserRepository();
        repository.fetchUsers();  // ✅ Fetch users from API
        users = repository.getUsersLiveData();
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

    public LiveData<List<User>> getFavorites() {
        return favorites;
    }

    public void addToFavorites(User user) {
        List<User> favList = favorites.getValue();
        if (favList != null && favList.size() < 10 && !favList.contains(user)) {
            favList.add(user);
            favorites.setValue(favList);
        }
    }

    public void removeFromFavorites(User user) {
        List<User> favList = favorites.getValue();
        if (favList != null) {
            favList.remove(user);
            favorites.setValue(favList);
        }
    }
}
