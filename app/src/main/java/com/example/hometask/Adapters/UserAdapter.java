package com.example.hometask.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hometask.R;
import com.example.hometask.Models.User;
import com.squareup.picasso.Picasso;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList;
    private OnUserClickListener listener;

    public interface OnUserClickListener {
        void onFavoriteClick(User user);
    }

    public UserAdapter(List<User> users, OnUserClickListener listener) {
        this.userList = users;
        this.listener = listener;
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
        holder.txtName.setText(user.getFirst_name() + " " + user.getLast_name());
        holder.txtEmail.setText(user.getEmail());
        holder.txtDob.setText(user.getDate_of_birth());
        Picasso.get().load(user.getAvatar()).into(holder.imgAvatar);

        holder.btnFavorite.setOnClickListener(v -> listener.onFavoriteClick(user));
    }

    @Override
    public int getItemCount() { return userList.size(); }

    public void updateUsers(List<User> newUsers) {
        this.userList = newUsers;
        notifyDataSetChanged();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtEmail, txtDob;
        ImageView imgAvatar;
        Button btnFavorite;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtDob = itemView.findViewById(R.id.txtDob);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
        }
    }
}
