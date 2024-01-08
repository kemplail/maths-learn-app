package fr.utt.if26.projetif16kemplaire.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fr.utt.if26.projetif16kemplaire.MyApplication;
import fr.utt.if26.projetif16kemplaire.R;

public class MainActivity extends AppCompatActivity {

    public final static int CODE_INSCRIPTION = 0;
    public final static int CODE_LIST_USERS = 1;
    public final static int CODE_MODE_INVITE = 2;

    MyApplication myapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myapp = (MyApplication)getApplicationContext();

        Button btn_mode_invite = (Button) findViewById(R.id.btn_invite);
        Button btn_inscription = (Button) findViewById(R.id.btn_inscription);
        Button btn_user_choice = (Button) findViewById(R.id.btn_user_choice);

        btn_mode_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_mode_invite();
            }
        });

        btn_inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_inscription();
            }
        });

        btn_user_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_user_choice();
            }
        });
    }

    public void start_mode_invite() {
        myapp.startAsInvite();

        Intent intent = new Intent(this, AccueilJeuActivity.class);
        startActivityForResult(intent,CODE_MODE_INVITE);
    }

    public void start_inscription() {
        Intent intent = new Intent(this, InscriptionActivity.class);
        startActivityForResult(intent,CODE_INSCRIPTION);
    }

    public void start_user_choice() {
        Intent intent = new Intent(this, ListUsersActivity.class);
        startActivityForResult(intent,CODE_LIST_USERS);
    }

}