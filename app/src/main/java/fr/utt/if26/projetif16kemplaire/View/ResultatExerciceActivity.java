package fr.utt.if26.projetif16kemplaire.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fr.utt.if26.projetif16kemplaire.R;
import fr.utt.if26.projetif16kemplaire.View.AccueilJeuActivity;
import fr.utt.if26.projetif16kemplaire.View.MenuActivity;

public class ResultatExerciceActivity extends MenuActivity {

    public static final String NB_TOTAL = "nb_total";
    public static final String NB_JUSTE = "nb_juste";
    public static final String TEMPS = "temps";

    private TextView temps;
    private TextView resultats_text;
    private TextView score_text;

    private Button btn_retour_accueil;
    private Button btn_recommencer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat_exercice);

        Integer nbt = getIntent().getIntExtra(NB_TOTAL,0);
        Integer nbj = getIntent().getIntExtra(NB_JUSTE,0);
        String tps = getIntent().getStringExtra(TEMPS);

        score_text = findViewById(R.id.score);

        temps = findViewById(R.id.temps);
        temps.setText("Temps réalisé : "+tps);

        resultats_text = findViewById(R.id.resultats);

        if (nbj == nbt) {
            resultats_text.setText("BRAVO ! Tu as eu tout juste !");
        } else {
            resultats_text.setText("MINCE ! Quelques petites erreurs !");
        }

        score_text.setText("Score : "+nbj+"/"+nbt);

        btn_retour_accueil = (Button) findViewById(R.id.btn_retour_accueil);
        btn_recommencer = (Button) findViewById(R.id.btn_recommencer);

        btn_retour_accueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retour_accueil();
            }
        });

        btn_recommencer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restart();
            }
        });
    }

    public void retour_accueil() {
        Intent intent = new Intent(this, AccueilJeuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void restart() {
        setResult(RESULT_OK);
        super.finish();
    }
}