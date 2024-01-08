package fr.utt.if26.projetif16kemplaire.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.utt.if26.projetif16kemplaire.R;
import fr.utt.if26.projetif16kemplaire.Model.Database.UserEntity;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.UserHolder> {

    List<UserEntity> users;

    public UserRecyclerAdapter() {
        this.users = new ArrayList<UserEntity>();
    }

    class UserHolder extends RecyclerView.ViewHolder {

        private TextView tv_user_pseudo;

        UserHolder(View itemView) {
            super(itemView);
            tv_user_pseudo = (TextView) itemView.findViewById(R.id.tv_user_pseudo);
        }

        void display(UserEntity user) {
            tv_user_pseudo.setText(user.getPseudo());
        }
    }


    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.user_item, parent, false);

        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        holder.display(users.get(position));
    }

    public void setUsers(List<UserEntity> users){
        this.users = users;
        notifyDataSetChanged();
    }

    public UserEntity getUser(int position) {
        return users.get(position);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

}
