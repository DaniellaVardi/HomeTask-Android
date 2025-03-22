package com.example.hometask.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.hometask.Models.User;
import com.example.hometask.R;
import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList = new ArrayList<>();
    private final OnUserClickListener onUserClickListener;

    public interface OnUserClickListener {
        void onUserClick(User user);
    }

    public UserAdapter(OnUserClickListener listener) {
        this.onUserClickListener = listener;
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

    public void updateList(List<User> newList) {
        userList = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtEmail, txtDob;
        ImageView imgAvatar;

        UserViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtDob = itemView.findViewById(R.id.txtDob);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);

            itemView.setOnClickListener(v -> onUserClickListener.onUserClick(userList.get(getAdapterPosition())));
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
