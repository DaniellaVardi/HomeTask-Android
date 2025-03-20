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

import com.example.hometask.Adapters.UserAdapter;
import com.example.hometask.R;
import com.example.hometask.ViewModel.UserViewModel;

public class UserListFragment extends Fragment {
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private UserViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Observe data from ViewModel
        viewModel.getUsers().observe(getViewLifecycleOwner(), users -> {
            if (users != null && !users.isEmpty()) {
                adapter = new UserAdapter(users, user -> {
                    viewModel.addToFavorites(user);
                });
                recyclerView.setAdapter(adapter);
            } else {
                Log.d("UserListFragment", "No users received!");
            }
        });

        // Button to navigate to FavoritesFragment
        Button btnGoToFavorites = view.findViewById(R.id.btnGoToFavorites);
        btnGoToFavorites.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_userListFragment_to_favoriteFragment)
        );
    }
}
