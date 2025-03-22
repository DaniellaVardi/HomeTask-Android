package com.example.hometask.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hometask.Models.User;
import com.example.hometask.R;
import com.example.hometask.ViewModel.FavoriteViewModel;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList;
    private final FavoriteViewModel favoriteViewModel;

    public UserAdapter(List<User> userList, FavoriteViewModel favoriteViewModel) {
        this.userList = userList;
        this.favoriteViewModel = favoriteViewModel;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    // To update the list of users in the adapter
    public void updateList(List<User> newList) {
        userList = newList;
        notifyDataSetChanged();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtEmail, txtDob;
        ImageView imgAvatar;
        Button btnFavorite;

        UserViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtDob = itemView.findViewById(R.id.txtDob);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);

            btnFavorite.setOnClickListener(v -> {
                User user = userList.get(getAdapterPosition());
                if (favoriteViewModel.isUserFavorite(user)) {
                    favoriteViewModel.removeFromFavorites(user);
                } else {
                    favoriteViewModel.addToFavorites(user);
                }
            });
        }

        void bind(User user) {
            txtName.setText(user.getFirst_name() + " " + user.getLast_name());
            txtEmail.setText(user.getEmail());
            txtDob.setText("DOB: " + user.getDob());

            Glide.with(imgAvatar.getContext())
                    .load(user.getAvatar())
                    .into(imgAvatar);

            // Observe favorite status
            favoriteViewModel.getFavoritesLiveData().observeForever(favorites -> {
                // Update button text and color based on if the user is in the favorites list
                if (favoriteViewModel.isUserFavorite(user)) {
                    btnFavorite.setText("UNFAVORITE");
                    btnFavorite.setBackgroundColor(itemView.getContext().getResources().getColor(android.R.color.holo_red_dark));
                } else {
                    btnFavorite.setText("Favorite");
                    btnFavorite.setBackgroundColor(itemView.getContext().getResources().getColor(android.R.color.holo_green_dark));
                }
            });
        }
    }
}
