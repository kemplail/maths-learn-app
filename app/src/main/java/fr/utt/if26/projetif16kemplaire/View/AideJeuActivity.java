package fr.utt.if26.projetif16kemplaire.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import fr.utt.if26.projetif16kemplaire.R;

public class AideJeuActivity extends MenuActivity {

    public static final String NOM_EXO = "nom";

    private Button jai_compris;
    private TextView aide_text;
    private ImageView image_aide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aide_jeu);

        aide_text = findViewById(R.id.aide_text);
        image_aide = findViewById(R.id.image_aide);
        jai_compris = findViewById(R.id.jaicompris_btn);

        if (getIntent().getStringExtra(NOM_EXO).equals("Randommaths Multiplication") || getIntent().getStringExtra(NOM_EXO).equals("Randommaths Addition") || getIntent().getStringExtra(NOM_EXO).equals("Randommaths Soustraction")) {
            aide_text.setText("Choisissez parmi 8 nombres la paire de nombre permettant d'obtenir le résultat affiché (en rouge). Il vous suffit de cliquer sur les cases pour sélectionner ou déselectionner un nombre.");

            jai_compris.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    start_exercice_configuration();
                }
            });

            image_aide.setImageResource(R.drawable.img_randomult);

        }
    }

    public void start_exercice_configuration() {
        Intent intent = new Intent(this, ExerciceParametersActivity.class);
        intent.putExtra(ExerciceParametersActivity.NOM_EXO, getIntent().getStringExtra(NOM_EXO));
        startActivity(intent);
    }
}