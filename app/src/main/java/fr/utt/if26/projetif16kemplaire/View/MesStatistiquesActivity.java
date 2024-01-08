package fr.utt.if26.projetif16kemplaire.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import fr.utt.if26.projetif16kemplaire.Model.Database.ExerciceResultat;
import fr.utt.if26.projetif16kemplaire.MyApplication;
import fr.utt.if26.projetif16kemplaire.R;
import fr.utt.if26.projetif16kemplaire.ViewModel.ResultatViewModel;

public class MesStatistiquesActivity extends MenuActivity {

    private MyApplication myapp;

    private Button btn_tous_scores;
    private Button btn_retour;

    private TextView titre;
    private TextView partiesjouees;
    private TextView partiesgagnees;
    private TextView pourcvictoire;

    private ResultatViewModel resultatViewModel;

    public final static int CODE_RETOUR = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_statistiques);

        titre = findViewById(R.id.titre_mestsats);
        partiesjouees = findViewById(R.id.partiesjouees);
        partiesgagnees = findViewById(R.id.partiesgagnees);
        pourcvictoire = findViewById(R.id.pourcentagevictoire);

        btn_tous_scores = (Button) findViewById(R.id.btn_tous_scores);
        btn_retour = (Button) findViewById(R.id.btn_retour);

        resultatViewModel = new ViewModelProvider(this).get(ResultatViewModel.class);

        myapp = (MyApplication) getApplicationContext();

        if (myapp.getUser_connecte().getPseudo() == "Invité") {
            titre.setText("Données de la session");
        } else {
            titre.setText("Mes statistiques");
        }

        partiesgagnees.setText("Parties gagnées : "+myapp.getUser_connecte().getParties_gagnees());
        partiesjouees.setText("Parties jouées : "+myapp.getUser_connecte().getParties_jouees());
        pourcvictoire.setText("Pourcentage de victoire : "+(double) Math.round(((double)myapp.getUser_connecte().getParties_gagnees()/myapp.getUser_connecte().getParties_jouees())*100)+"%");

        if (myapp.getUser_connecte().getPseudo() != "Invité") {
            afficheScoresUtilisateur();
        } else {
            afficheScoresInvite();
        }

        btn_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retour();
            }
        });

        btn_tous_scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startVoirTousScores();
            }
        });
    }

    public void afficheScoresUtilisateur() {
        LinearLayout linear_scoresmoyen = findViewById(R.id.scoresmoyen);
        LinearLayout linear_scores = findViewById(R.id.score);

        TextView txt = new TextView(this);
        txt.setText("Score moyen exercices débutant : ");
        txt.setTextSize(20);
        txt.setGravity(Gravity.CENTER);

        TextView txt2 = new TextView(this);
        txt2.setText("Score moyen exercices confirmé : ");
        txt2.setTextSize(20);
        txt2.setGravity(Gravity.CENTER);

        linear_scoresmoyen.addView(txt);
        linear_scoresmoyen.addView(txt2);

        resultatViewModel.getScoreMoyenByExoCat(myapp.getUser_connecte().getId(), "Debutant").observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer scoreMoyen) {
                if(scoreMoyen != null) {
                    txt.setText(txt.getText()+" "+scoreMoyen);
                } else {
                    txt.setText(txt.getText()+" N.C");
                }
            }
        });

        resultatViewModel.getScoreMoyenByExoCat(myapp.getUser_connecte().getId(), "Confirme").observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer scoreMoyen) {
                if(scoreMoyen != null) {
                    txt2.setText(txt2.getText()+" "+scoreMoyen);
                } else {
                    txt2.setText(txt2.getText()+" N.C");
                }
            }
        });

        resultatViewModel.getExerciceResultatOfUser(myapp.getUser_connecte().getId(), 5).observe(this, new Observer<List<ExerciceResultat>>() {
            @Override
            public void onChanged(List<ExerciceResultat> resultats) {

                Context context = getApplicationContext();

                for (int i = 0; i < resultats.size(); i++) {
                    TextView txt = new TextView(context);

                    txt.setTextColor(Color.BLACK);
                    txt.setText("Exercice : "+resultats.get(i).exerciceName+" - Score : "+resultats.get(i).score);
                    txt.setGravity(Gravity.CENTER);

                    linear_scores.addView(txt);
                }
            }
        });
    }

    public void afficheScoresInvite() {
        LinearLayout linear_scores = findViewById(R.id.score);
        LinearLayout linear_scoresmoyen = findViewById(R.id.scoresmoyen);

        btn_tous_scores.setVisibility(View.INVISIBLE);

        TextView txt = new TextView(this);
        txt.setTextSize(20);
        txt.setTextColor(Color.RED);
        txt.setText("5 derniers scores INDISPONIBLES en mode INVITE");
        txt.setGravity(Gravity.CENTER);

        TextView txt2 = new TextView(this);
        txt2.setTextSize(20);
        txt2.setTextColor(Color.RED);
        txt2.setText("Scores moyens INDISPONIBLES en mode INVITE");
        txt2.setGravity(Gravity.CENTER);

        linear_scoresmoyen.addView(txt2);
        linear_scores.addView(txt);
    }

    public void startVoirTousScores() {
        Intent intent = new Intent(this, ListResultatsActivity.class);
        startActivityForResult(intent, CODE_RETOUR);
    }

    public void retour() {
        setResult(RESULT_OK);
        finish();
    }
}