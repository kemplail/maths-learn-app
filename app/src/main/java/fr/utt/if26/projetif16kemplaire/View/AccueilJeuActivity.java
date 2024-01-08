package fr.utt.if26.projetif16kemplaire.View;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import fr.utt.if26.projetif16kemplaire.MyApplication;
import fr.utt.if26.projetif16kemplaire.R;

public class AccueilJeuActivity extends MenuActivity {

    MyApplication myapp;
    private TextView user_co;
    private Button btn_deconnection;

    private Button btn_exos_debutant;
    private Button btn_exos_confirme;
    private Button btn_espace_user;
    private Button btn_statistiques;

    public final static int CODE_DEBUTANT = 0;
    public final static int CODE_CONFIRME = 1;
    public final static int CODE_ESPACEUSER = 2;
    public final static int CODE_STATS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_jeu);

        myapp = (MyApplication)getApplicationContext();
        user_co = findViewById(R.id.user_co);

        updateAffichageUserConnecte();

        btn_deconnection = (Button) findViewById(R.id.btn_deconnection);

        btn_deconnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deconnection();
            }
        });

        btn_exos_debutant = (Button) findViewById(R.id.btn_exos_debutant);
        btn_exos_confirme = (Button) findViewById(R.id.btn_exos_confirme);
        btn_espace_user = (Button) findViewById(R.id.btn_espace_user);
        btn_statistiques = (Button) findViewById(R.id.btn_statistiques);

        btn_exos_debutant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startExosDebutant();
            }
        });

        btn_exos_confirme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startExosConfirme();
            }
        });

        btn_espace_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startEspaceUser();
            }
        });

        btn_statistiques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStatistiques();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODE_DEBUTANT) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this,"Retour Débutant", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == CODE_CONFIRME) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this,"Retour Confirmé", Toast.LENGTH_SHORT).show();
            }
        } else if(requestCode == CODE_ESPACEUSER) {
            if(resultCode == RESULT_OK) {
                updateAffichageUserConnecte();
            }
        }
    }

    public void updateAffichageUserConnecte() {
        if (myapp.getUser_connecte() != null) {
            user_co.setText("Utilisateur connecté : "+myapp.getUser_connecte().getPseudo());
        } else {
            user_co.setText("Utilisateur connecté : ERROR");
        }
    }

    public void startExosDebutant() {
        Intent intent = new Intent(this, ListExercicesChoisiActivity.class);
        intent.putExtra(ListExercicesChoisiActivity.CATEGORIE, "Debutant");
        startActivityForResult(intent,CODE_DEBUTANT);
    }

    public void startExosConfirme() {
        Intent intent = new Intent(this, ListExercicesChoisiActivity.class);
        intent.putExtra(ListExercicesChoisiActivity.CATEGORIE, "Confirme");
        startActivityForResult(intent,CODE_CONFIRME);
    }

    public void startEspaceUser() {
        if (myapp.getUser_connecte().getPseudo() != "Invité") {
            Intent intent = new Intent(this, EspaceUtilisateurActivity.class);
            startActivityForResult(intent,CODE_ESPACEUSER);
        } else {
            Toast.makeText(this,"ERREUR ! Vous devez avoir un compte pour accéder à cette rubrique.", Toast.LENGTH_SHORT).show();
        }
    }

    public void startStatistiques() {
        Intent intent = new Intent(this, MesStatistiquesActivity.class);
        startActivityForResult(intent,CODE_STATS);
    }

    public void deconnection() {
        myapp.resetUserConnecte();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}