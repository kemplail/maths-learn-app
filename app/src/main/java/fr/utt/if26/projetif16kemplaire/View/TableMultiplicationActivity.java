package fr.utt.if26.projetif16kemplaire.View;

import static java.lang.Integer.parseInt;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fr.utt.if26.projetif16kemplaire.Model.TableMultiplication;
import fr.utt.if26.projetif16kemplaire.MyApplication;
import fr.utt.if26.projetif16kemplaire.R;
import fr.utt.if26.projetif16kemplaire.ViewModel.ExerciceViewModel;
import fr.utt.if26.projetif16kemplaire.ViewModel.ResultatViewModel;
import fr.utt.if26.projetif16kemplaire.ViewModel.UserViewModel;

public class TableMultiplicationActivity extends MenuActivity {

    private MyApplication myapp;
    public static final String TABLE_KEY = "table_key";
    public final static int MULT_RETOUR = 0;

    private TableMultiplication table;

    private TextView titre;
    private LinearLayout linear_principal;
    private Chronometer timer;
    private Button btn_valider;

    private ArrayList<EditText> textviews;

    private ExerciceViewModel exerciceViewModel;
    private UserViewModel userViewModel;
    private ResultatViewModel resultatViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_multiplication);

        exerciceViewModel = new ViewModelProvider(this).get(ExerciceViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        resultatViewModel = new ViewModelProvider(this).get(ResultatViewModel.class);

        titre = findViewById(R.id.titre);
        linear_principal = findViewById(R.id.linear_principal);
        btn_valider = findViewById(R.id.btn_valider);

        btn_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validerReponses();
            }
        });

        Integer valeur = parseInt(getIntent().getStringExtra(TABLE_KEY));
        myapp = (MyApplication) getApplicationContext();

        timer = findViewById(R.id.timer);

        int exerciceId = exerciceViewModel.getExerciceIdByName("Tables de multiplication");
        int userId = myapp.getUser_connecte().getId();

        table = new TableMultiplication(valeur,exerciceId,userId);

        initLayout();
    }

    public void initLayout() {

        textviews = new ArrayList<EditText>();
        titre.setText("Table du "+ table.getnumeroTable());

        for(int i = 1; i <= 10; i++) {
            LinearLayout linear_reponse = new LinearLayout(this);
            linear_reponse.setOrientation(LinearLayout.HORIZONTAL);
            linear_reponse.setGravity(Gravity.CENTER);

            TextView txt = new TextView(this);
            txt.setText(table.getnumeroTable()+" x "+i+" = ");
            txt.setTextSize(18);
            linear_reponse.addView(txt);

            EditText ed_reponse = new EditText(this);
            ed_reponse.setInputType(2);
            ed_reponse.setWidth(100);
            ed_reponse.setTextSize(18);

            textviews.add(ed_reponse);
            linear_reponse.addView(ed_reponse);

            linear_principal.addView(linear_reponse);
        }

        timer.start();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == MULT_RETOUR) {
            timer.setBase(SystemClock.elapsedRealtime());
            timer.start();
        }
    }

    public void validerReponses() {

        int nbReponses = 0;

        for (int i = 0; i < textviews.size(); i++) {
            if (((EditText)textviews.get(i)).getText().toString().equals("")) {
                nbReponses++;
            } else {
                table.getReponses().add(parseInt(((EditText)textviews.get(i)).getText().toString()));
            }
        }

        if (nbReponses == 0) {

            timer.stop();

            table.set_resultats();

            myapp.getUser_connecte().addPartie_jouee();
            if (table.is_exercice_reussi()) {
                myapp.getUser_connecte().addPartie_gagnee();
            }

            if (myapp.getUser_connecte().getPseudo() != "Invité") {
                saveResultat();
            }

            goToResults();

        } else {
            Toast.makeText(getApplicationContext(), "Erreur ! Veuillez remplir tous les champs !", Toast.LENGTH_LONG).show();
        }
    }

    public void saveResultat() {
        resultatViewModel.insert(table.getResults());
        userViewModel.update(myapp.getUser_connecte());
    }

    public void goToResults() {

        table.reset_reponses();

        Intent intent = new Intent(this, ResultatExerciceActivity.class);
        intent.putExtra(ResultatExerciceActivity.NB_JUSTE, table.getResults().getScore());
        intent.putExtra(ResultatExerciceActivity.NB_TOTAL, 10);
        intent.putExtra(ResultatExerciceActivity.TEMPS, timer.getText().toString());
        startActivityForResult(intent, MULT_RETOUR);
    }
}