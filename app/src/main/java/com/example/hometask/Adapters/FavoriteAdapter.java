package com.example.hometask.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hometask.Models.User;
import com.example.hometask.R;
import com.example.hometask.ViewModel.FavoriteViewModel;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private List<User> favoriteList;
    private final FavoriteViewModel viewModel;

    public FavoriteAdapter(List<User> favoriteList, FavoriteViewModel viewModel) {
        this.favoriteList = favoriteList;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        User user = favoriteList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public void updateList(List<User> newList) {
        favoriteList = newList;
        notifyDataSetChanged();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtEmail, txtDob;
        ImageView imgAvatar;
        Button btnRemove;

        FavoriteViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtDob = itemView.findViewById(R.id.txtDob);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            btnRemove = itemView.findViewById(R.id.btnRemove);

            btnRemove.setOnClickListener(v -> {
                User user = favoriteList.get(getAdapterPosition());
                viewModel.removeFromFavorites(user);
            });
        }

        void bind(User user) {
            txtName.setText(user.getFirst_name() + " " + user.getLast_name());
            txtEmail.setText(user.getEmail());
            txtDob.setText("DOB: " + user.getDob());

            Glide.with(imgAvatar.getContext())
                    .load(user.getAvatar())
                    .into(imgAvatar);
        }
    }
}
