package com.example.hometask.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
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
        return new UserViewHolder(view, parent.getContext()); // Pass the context here
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
        private Context context;

        UserViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context; // Save the context here

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

            // Observe LiveData on the main thread
            favoriteViewModel.getFavoritesLiveData().observe((LifecycleOwner) context, favorites -> {
                // Perform UI updates only on the main thread
                if (favoriteViewModel.isUserFavorite(user)) {
                    btnFavorite.setText("UNFAVORITE");
                    btnFavorite.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.primary_color));
                } else {
                    btnFavorite.setText("Favorite");
                    btnFavorite.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.secondary_color));
                }
            });
        }
    }
}
