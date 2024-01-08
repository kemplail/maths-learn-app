package fr.utt.if26.projetif16kemplaire.View;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import fr.utt.if26.projetif16kemplaire.Model.ExerciceAdditionClassique;
import fr.utt.if26.projetif16kemplaire.MyApplication;
import fr.utt.if26.projetif16kemplaire.R;
import fr.utt.if26.projetif16kemplaire.ViewModel.ExerciceViewModel;
import fr.utt.if26.projetif16kemplaire.ViewModel.ResultatViewModel;
import fr.utt.if26.projetif16kemplaire.ViewModel.UserViewModel;

public class AdditionClassiqueActivity extends MenuActivity {

    public final static int ADD_RETOUR = 0;

    private MyApplication myapp;

    private TextView nombre1;
    private TextView nombre2;
    private TextView reponse_user;
    private TextView numero_round_additions;
    private Chronometer timer;

    private Button btn_retour;
    private Button btn_avance;

    private ExerciceAdditionClassique exo_addition;

    private ExerciceViewModel exerciceViewModel;
    private UserViewModel userViewModel;
    private ResultatViewModel resultatViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition_classique);

        myapp = (MyApplication) getApplicationContext();

        exerciceViewModel = new ViewModelProvider(this).get(ExerciceViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        resultatViewModel = new ViewModelProvider(this).get(ResultatViewModel.class);

        int exerciceId = exerciceViewModel.getExerciceIdByName("Additions classiques");
        int userId = myapp.getUser_connecte().getId();

        exo_addition = new ExerciceAdditionClassique(exerciceId,userId);

        nombre1 = findViewById(R.id.nombre1_additions);
        nombre2 = findViewById(R.id.nombre2_additions);
        reponse_user = findViewById(R.id.reponse_user);
        numero_round_additions = findViewById(R.id.numero_round_additions);

        btn_retour = findViewById(R.id.btn_retour);
        btn_avance = findViewById(R.id.btn_suivant);

        btn_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retour_round();
            }
        });

        btn_avance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avance_round();
            }
        });

        timer = findViewById(R.id.timer);
        timer.start();

        initRound();
    }

    public void initRound() {
        numero_round_additions.setText("Numéro du round : "+(exo_addition.getNumero_round()+1)+"/10");
        nombre1.setText(exo_addition.getNumeros1().get(exo_addition.getNumero_round()).toString());
        nombre2.setText(exo_addition.getNumeros2().get(exo_addition.getNumero_round()).toString());

        if (exo_addition.getReponses().size() > exo_addition.getNumero_round()) {
            reponse_user.setText(exo_addition.getReponses().get(exo_addition.getNumero_round()).toString());
        }
    }

    public void retour_round() {
        if (exo_addition.getNumero_round() > 0) {
            exo_addition.remove_round();
            initRound();
        } else {
            Toast.makeText(getApplicationContext(), "Impossible !", Toast.LENGTH_LONG).show();
        }
    }

    public void avance_round() {
        final String reponse = reponse_user.getText().toString();

        if (reponse.isEmpty()) {
            reponse_user.setError("Veuillez entrer un résultat !");
            reponse_user.requestFocus();
            return;

        } else {
            int intreponse = Integer.parseInt(reponse);

            exo_addition.set_reponse(intreponse);
            reponse_user.setText("");

            if (!exo_addition.is_exercice_finished()) {

                exo_addition.add_round();

                initRound();

            } else {

                timer.stop();

                exo_addition.set_resultats();

                myapp.getUser_connecte().addPartie_jouee();
                if (exo_addition.is_exercice_reussi()) {
                    myapp.getUser_connecte().addPartie_gagnee();
                }

                if (myapp.getUser_connecte().getPseudo() != "Invité") {
                    System.out.println("SAVE");
                    saveResultat();
                }

                goToResults();
            }
        }
    }

    public void saveResultat() {
        resultatViewModel.insert(exo_addition.getResults());
        userViewModel.update(myapp.getUser_connecte());
    }

    public void goToResults() {

        exo_addition.reset_reponses();

        Intent intent = new Intent(this, ResultatExerciceActivity.class);
        intent.putExtra(ResultatExerciceActivity.NB_TOTAL, exo_addition.getNumeros1().size());
        intent.putExtra(ResultatExerciceActivity.NB_JUSTE, exo_addition.getResults().getScore());
        intent.putExtra(ResultatExerciceActivity.TEMPS, timer.getText().toString());
        startActivityForResult(intent,ADD_RETOUR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // Vérification du retour à l'aide du code requête
        if (requestCode == ADD_RETOUR) {
            timer.setBase(SystemClock.elapsedRealtime());
            timer.start();

            initRound();
        }
    }

}