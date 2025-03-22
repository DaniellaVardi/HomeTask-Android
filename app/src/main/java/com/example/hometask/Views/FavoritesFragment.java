package com.example.hometask.Views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hometask.Adapters.FavoriteAdapter;
import com.example.hometask.R;
import com.example.hometask.ViewModel.FavoriteViewModel;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {
    private FavoriteViewModel favoriteViewModel;
    private FavoriteAdapter adapter;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("FavoritesFragment", "FavoritesFragment opened");

        favoriteViewModel = new ViewModelProvider(requireActivity()).get(FavoriteViewModel.class);

        // Set up RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewFavorites);

        // Initialize adapter with an empty list to avoid null issues
        adapter = new FavoriteAdapter(new ArrayList<>(), favoriteViewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Observe favorite users and update the adapter when data changes
        favoriteViewModel.getFavoritesLiveData().observe(getViewLifecycleOwner(), users -> {
            if (users != null) {
                adapter.updateList(users);
                Log.d("FavoritesFragment", "Favorites List Updated: " + users.size());
            } else {
                Log.d("FavoritesFragment", "Favorites List is NULL");
            }
        });

    }
}
