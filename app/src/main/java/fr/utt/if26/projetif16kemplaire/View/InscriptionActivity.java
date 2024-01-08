package fr.utt.if26.projetif16kemplaire.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import fr.utt.if26.projetif16kemplaire.Model.Database.UserEntity;
import fr.utt.if26.projetif16kemplaire.R;
import fr.utt.if26.projetif16kemplaire.ViewModel.UserViewModel;

public class InscriptionActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

    private EditText edit_pseudo, edit_password;
    private TextView tv_warning;
    private NumberPicker edit_age;
    private Button btn_save;
    private Button btn_retour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        edit_age = (NumberPicker) findViewById(R.id.edit_age);
        edit_pseudo = (EditText) findViewById(R.id.edit_pseudo);
        edit_password = (EditText) findViewById(R.id.edit_password);

        tv_warning = (TextView) findViewById(R.id.inscription_warning);
        tv_warning.setTextColor(Color.RED);

        btn_save = (Button) findViewById(R.id.button_save);
        btn_retour = (Button) findViewById(R.id.button_retour);

        edit_age.setMinValue(9);
        edit_age.setMaxValue(99);

        btn_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retour();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    public void retour() {
        setResult(RESULT_OK);
        finish();
    }

    public void save() {

        final String pseudo = edit_pseudo.getText().toString().trim();
        final String password = edit_password.getText().toString().trim();
        final int age = edit_age.getValue();

        if (pseudo.isEmpty()) {
            edit_pseudo.setError("Pseudo requis !");
            return;
        }

        if(password.isEmpty()) {
            edit_password.setError("Code requis !");
            return;
        }

        UserEntity newUser = new UserEntity();
        newUser.setPseudo(pseudo);
        newUser.setAge(age);
        newUser.setPassword(password);

        userViewModel.insert(newUser);

        setResult(RESULT_OK);
        finish();

    }
}