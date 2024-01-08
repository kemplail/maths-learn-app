package fr.utt.if26.projetif16kemplaire.View;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;

import fr.utt.if26.projetif16kemplaire.MyApplication;
import fr.utt.if26.projetif16kemplaire.R;
import fr.utt.if26.projetif16kemplaire.ViewModel.UserViewModel;

public class EspaceUtilisateurActivity extends MenuActivity {

    public final static int CAMERA_PIC_REQUEST = 0;

    private MyApplication myapp;

    private ImageView photo_profil;

    private EditText edit_pseudo, edit_code;
    private NumberPicker age;
    private Button btn_valider;
    private Button btn_retour;
    private Button btn_delete_compte;

    private Button btn_startcamera;

    private UserViewModel userViewModel;

    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_utilisateur);

        myapp = (MyApplication) getApplicationContext();

        edit_pseudo = findViewById(R.id.edit_pseudo);
        edit_code = findViewById(R.id.edit_password);
        age = findViewById(R.id.age_np);
        photo_profil = findViewById(R.id.photo_profil);

        btn_retour = findViewById(R.id.btn_retour);
        btn_valider = findViewById(R.id.btn_valider);
        btn_delete_compte = findViewById(R.id.btn_delete_compte);

        age.setMinValue(9);
        age.setMaxValue(99);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        btn_startcamera = findViewById(R.id.btn_startcamera);
        setCamera();

        setInfos();

        activity = this;

        btn_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retour();
            }
        });

        btn_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateInfos();
            }
        });

        btn_delete_compte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCompte();
            }
        });
    }

    public void setCamera() {
        btn_startcamera.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        //intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                        intent.putExtra("return-data", true);
                        startActivityForResult(intent,CAMERA_PIC_REQUEST);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_PIC_REQUEST && resultCode== Activity.RESULT_OK)
        {
            Bitmap bitmap=(Bitmap)data.getExtras().get("data");
            photo_profil.setImageBitmap(bitmap);
        }
    }

    public void setInfos() {
        age.setValue(myapp.getUser_connecte().getAge());
        edit_pseudo.setText(myapp.getUser_connecte().getPseudo());
        edit_code.setText("");
    }

    public void updateInfos() {
        final String pseudo = edit_pseudo.getText().toString().trim();
        final String password = edit_code.getText().toString().trim();

        if (pseudo.isEmpty()) {
            edit_pseudo.setError("Pseudo requis !");
            return;
        }

        if(password.isEmpty()) {
            edit_code.setError("Code requis !");
            return;
        }

        myapp.getUser_connecte().setAge(age.getValue());
        myapp.getUser_connecte().setPseudo(pseudo);
        myapp.getUser_connecte().setPassword(password);

        userViewModel.updateInfos(myapp.getUser_connecte());
    }

    public void deleteCompte() {
        userViewModel.deleteUser(myapp.getUser_connecte());
        myapp.resetUserConnecte();
    }

    public void retour() {
        finish();
        setResult(RESULT_OK);
    }
}