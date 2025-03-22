package com.example.hometask.Views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hometask.Adapters.UserAdapter;
import com.example.hometask.Models.User;
import com.example.hometask.R;
import com.example.hometask.ViewModel.FavoriteViewModel;
import com.example.hometask.ViewModel.UserViewModel;

import java.util.ArrayList;

public class UserListFragment extends Fragment {

    private UserViewModel userViewModel;
    private FavoriteViewModel favoriteViewModel;
    private UserAdapter adapter;

    public UserListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        favoriteViewModel = new ViewModelProvider(requireActivity()).get(FavoriteViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        // Initialize adapter with an empty list and FavoriteViewModel
        adapter = new UserAdapter(new ArrayList<>(), favoriteViewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Observe user list data and update the adapter
        userViewModel.getUsers().observe(getViewLifecycleOwner(), users -> {
            if (users != null) {
                adapter.updateList(users);  // Update the list without removing users
            }
        });

        // Observe the favorite list full event to show a message when favorites list is full
        favoriteViewModel.getFavoriteListFullEvent().observe(getViewLifecycleOwner(), message -> {
            if (message != null) {
                // Show a Toast when the user is already in the favorites list or the list is full
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

                // Reset the event after it has been consumed
                favoriteViewModel.getFavoriteListFullEvent().setValue(null); // Clear the message after consumption
            }
        });

        // Fetch users when fragment is created
        userViewModel.fetchUsers();

        // Sort Button
        Button btnSort = view.findViewById(R.id.sort_button);
        btnSort.setOnClickListener(v -> {
            userViewModel.sortUsersByDateOfBirth();  // Sort users by date of birth
        });

        // Button to navigate to FavoritesFragment
        Button btnGoToFavorites = view.findViewById(R.id.btnGoToFavorites);
        btnGoToFavorites.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_userListFragment_to_favoriteFragment)
        );
    }
}
