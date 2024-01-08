package fr.utt.if26.projetif16kemplaire.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fr.utt.if26.projetif16kemplaire.Model.Database.UserEntity;
import fr.utt.if26.projetif16kemplaire.MyApplication;
import fr.utt.if26.projetif16kemplaire.R;
import fr.utt.if26.projetif16kemplaire.View.AccueilJeuActivity;
import fr.utt.if26.projetif16kemplaire.ViewModel.UserViewModel;

public class LoginActivity extends AppCompatActivity {

    private int userId;
    private UserViewModel userViewModel;

    private Button btn_retour;
    private Button btn_valider;
    private EditText edit_password;
    private TextView code_invalide;

    MyApplication myapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myapp = (MyApplication)getApplicationContext();
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        btn_retour = (Button) findViewById(R.id.button_retour);
        btn_valider = (Button) findViewById(R.id.button_valider);
        edit_password = (EditText) findViewById(R.id.edit_password);
        code_invalide = (TextView) findViewById(R.id.tv_code_invalide);

        code_invalide.setVisibility(View.GONE);

        btn_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retour();
            }
        });

        btn_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valider();
            }
        });

        Bundle extras = getIntent().getExtras();
        this.userId = extras.getInt("userId");
    }

    public void valider() {

        final String password = edit_password.getText().toString().trim();
        if(password.isEmpty()) {
            edit_password.setError("Code requis !");
            return;
        }

        boolean isLoginChecked = userViewModel.checkValidLogin(userId, password);
        if(isLoginChecked) {

            code_invalide.setVisibility(View.GONE);
            UserEntity loggedUser = userViewModel.getUserById(userId);
            myapp.setUser_connecte(loggedUser);

            startExercices();

        } else {
            code_invalide.setVisibility(View.VISIBLE);
        }

    }

    public void retour() {
        finish();
    }

    public void startExercices() {
        Intent intent = new Intent(this, AccueilJeuActivity.class);
        startActivity(intent);
    }
}