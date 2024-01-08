package fr.utt.if26.projetif16kemplaire.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

import fr.utt.if26.projetif16kemplaire.Adapter.UserRecyclerAdapter;
import fr.utt.if26.projetif16kemplaire.Model.Database.UserEntity;
import fr.utt.if26.projetif16kemplaire.R;
import fr.utt.if26.projetif16kemplaire.View.Listener.RecyclerItemClickListener;
import fr.utt.if26.projetif16kemplaire.ViewModel.UserViewModel;

public class ListUsersActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        UserRecyclerAdapter recyclerAdapter = new UserRecyclerAdapter();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyler_list_users);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(recyclerAdapter);

        Button btn_retour = (Button) findViewById(R.id.list_back);

        btn_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retour();
            }
        });

        userViewModel.getAllUsers().observe(this, new Observer<List<UserEntity>>() {
            @Override
            public void onChanged(List<UserEntity> users) {
                recyclerAdapter.setUsers(users);
            }
        });

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        int userId = recyclerAdapter.getUser(position).getId();
                        startLogin(userId);
                    }

                    @Override public void onLongItemClick(View view, int position) { }
                })
        );
    }

    public void retour() {
        setResult(RESULT_OK);
        finish();
    }

    void startLogin(int userId) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("userId",userId);
        startActivity(intent);
    }
}