package fr.utt.if26.projetif16kemplaire.View;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import fr.utt.if26.projetif16kemplaire.Model.ExerciceRandomMath;
import fr.utt.if26.projetif16kemplaire.MyApplication;
import fr.utt.if26.projetif16kemplaire.R;
import fr.utt.if26.projetif16kemplaire.ViewModel.ExerciceViewModel;
import fr.utt.if26.projetif16kemplaire.ViewModel.ResultatViewModel;
import fr.utt.if26.projetif16kemplaire.ViewModel.UserViewModel;

public class RandomMathActivity extends MenuActivity {

    public final static int RAN_RETOUR = 0;

    public static final String CAT_EXO = "cat_exo";
    public static final String NOM_EXO = "nom_exo";

    public static final String TAILLE_MIN = "taille_min";
    public static final String TAILLE_MAX = "taille_max";

    private Integer[] numberIds = new Integer[] {R.id.number0, R.id.number1, R.id.number2, R.id.number3, R.id.number4, R.id.number5, R.id.number6, R.id.number7 };

    private HashMap<String, TextView> numeros;

    //Titre + num round
    private TextView titre;
    private TextView numeroround_text;

    //Résultat à obtenir (au milieu)
    private TextView multencours;

    private Chronometer timer;

    //Infos en bas
    private TextView numero1;
    private TextView numero2;
    private TextView operande;
    private TextView res;

    private ExerciceRandomMath exo_randommath;

    private ExerciceViewModel exerciceViewModel;
    private UserViewModel userViewModel;
    private ResultatViewModel resultatViewModel;

    private MyApplication myapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_math);

        myapp = (MyApplication) getApplicationContext();

        exerciceViewModel = new ViewModelProvider(this).get(ExerciceViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        resultatViewModel = new ViewModelProvider(this).get(ResultatViewModel.class);

        Intent intent = getIntent();
        String nom_exo = intent.getStringExtra(this.NOM_EXO);
        Integer cat_exo = ExerciceRandomMath.getCatExo(nom_exo);

        int exerciceId = exerciceViewModel.getExerciceIdByName(nom_exo);
        int userId = myapp.getUser_connecte().getId();
        int tailleMin = Integer.parseInt(intent.getStringExtra(TAILLE_MIN));
        int tailleMax = Integer.parseInt(intent.getStringExtra(TAILLE_MAX));

        exo_randommath = new ExerciceRandomMath(cat_exo, exerciceId, userId, tailleMin, tailleMax);

        titre = findViewById(R.id.titre);
        numeroround_text = findViewById(R.id.numero_round);
        multencours = findViewById(R.id.multencours);

        numero1 = findViewById(R.id.nombre1_additions);
        numero2 = findViewById(R.id.numero2);
        operande = findViewById(R.id.operande);
        res = findViewById(R.id.res);

        initTitle(cat_exo);
        initNumeros();
        initButtonsHandlers();

        timer = findViewById(R.id.timer);
        timer.start();

        initRound(exo_randommath.getNumero_round());
    }

    public void initNumeros() {
        numeros = new HashMap<>();

        numeros.put("number0", (TextView) findViewById(R.id.number0));
        numeros.put("number1", (TextView) findViewById(R.id.number1));
        numeros.put("number2", (TextView) findViewById(R.id.number2));
        numeros.put("number3", (TextView) findViewById(R.id.number3));
        numeros.put("number4", (TextView) findViewById(R.id.number4));
        numeros.put("number5", (TextView) findViewById(R.id.number5));
        numeros.put("number6", (TextView) findViewById(R.id.number6));
        numeros.put("number7", (TextView) findViewById(R.id.number7));
    }

    public void initTitle(Integer cat_exo) {
        switch(cat_exo) {
            case ExerciceRandomMath.SOUSTRACTION:
                titre.setText("Soustractions");
                break;
            case ExerciceRandomMath.MULTIPLICATION:
                titre.setText("Multiplications");
                break;
            case ExerciceRandomMath.ADDITION:
                titre.setText("Additions");
                break;
        }
    }

    public void initRound(int num) {

        numeroround_text.setText("Numéro du round : " + (num + 1) + " /10");

        ArrayList<Integer> numbers = exo_randommath.getRoundNumbers(num);
        res.setText(((Integer) exo_randommath.getReponseJusteOfRound(num)).toString());

        if (exo_randommath.getOperation() == ExerciceRandomMath.MULTIPLICATION) {
            operande.setText(" X ");
        } else if (exo_randommath.getOperation() == ExerciceRandomMath.ADDITION) {
            operande.setText(" + ");
        } else {
            operande.setText(" - ");
        }

        numero1.setText("???");
        numero2.setText("???");

        multencours.setText(((Integer) exo_randommath.getReponseJusteOfRound(num)).toString());
        multencours.setBackgroundColor(Color.RED);

        for (int i = 0; i < 8; i++) {
            numeros.get("number" + i).setText(((Integer) numbers.get(i)).toString());
            numeros.get("number" + i).setBackgroundColor(0x00000000);
        }
    }

    public void initButtonsHandlers() {
        for(int i = 0; i < 8; i++) {
            findViewById(numberIds[i]).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    choix_numero(view);
                }
            });
        }
    }

    public void choix_numero(View view) {

        if (((ColorDrawable) numeros.get(getResources().getResourceEntryName(view.getId())).getBackground()).getColor() != Color.GREEN && (exo_randommath.getNombre1() == 0 && exo_randommath.getNombre2() == 0)) {
            numeros.get(getResources().getResourceEntryName(view.getId())).setBackgroundColor(Color.GREEN);
            exo_randommath.setNombre1(Integer.parseInt(numeros.get(getResources().getResourceEntryName(view.getId())).getText().toString()));
            numero1.setText(numeros.get(getResources().getResourceEntryName(view.getId())).getText().toString());
        } else if (((ColorDrawable) numeros.get(getResources().getResourceEntryName(view.getId())).getBackground()).getColor() != Color.GREEN && (exo_randommath.getNombre1() == 0 || exo_randommath.getNombre2() == 0)) {
            if (exo_randommath.getNombre1() == 0) {
                exo_randommath.setNombre1(Integer.parseInt(numeros.get(getResources().getResourceEntryName(view.getId())).getText().toString()));
                numero1.setText(numeros.get(getResources().getResourceEntryName(view.getId())).getText().toString());
            } else {
                exo_randommath.setNombre2(Integer.parseInt(numeros.get(getResources().getResourceEntryName(view.getId())).getText().toString()));
                numero2.setText(numeros.get(getResources().getResourceEntryName(view.getId())).getText().toString());
            }
            numeros.get(getResources().getResourceEntryName(view.getId())).setBackgroundColor(Color.GREEN);
        } else if (((ColorDrawable) numeros.get(getResources().getResourceEntryName(view.getId())).getBackground()).getColor() == Color.GREEN) {
            if (exo_randommath.getNombre1() == Integer.parseInt(numeros.get(getResources().getResourceEntryName(view.getId())).getText().toString())) {
                exo_randommath.setNombre1(0);
                numero1.setText("???");
            } else {
                exo_randommath.setNombre2(0);
                numero2.setText("???");
            }
            numeros.get(getResources().getResourceEntryName(view.getId())).setBackgroundColor(0x00000000);
        }

    }

    public void valide_round(View view) {
        if (exo_randommath.getNombre1() == 0 || exo_randommath.getNombre2() == 0) {
            Toast.makeText(this, "Veuillez choisir deux numéros à multiplier !", Toast.LENGTH_SHORT).show();
        } else {
            exo_randommath.validerResultatActuel();

            if(!exo_randommath.is_exercice_finished()) {
                exo_randommath.add_round();
                exo_randommath.reset_nombres();

                initRound(exo_randommath.getNumero_round());
            } else {
                timer.stop();

                myapp.getUser_connecte().addPartie_jouee();
                if (exo_randommath.is_exercice_reussi()) {
                    myapp.getUser_connecte().addPartie_gagnee();
                }

                if (myapp.getUser_connecte().getPseudo() != "Invité") {
                    saveResultat();
                }

                goToResults();
            }
        }
    }

    public void saveResultat() {
        resultatViewModel.insert(exo_randommath.getResults());
        userViewModel.update(myapp.getUser_connecte());
    }

    public void goToResults() {

        Intent intent = new Intent(this, ResultatExerciceActivity.class);
        intent.putExtra(ResultatExerciceActivity.NB_TOTAL, exo_randommath.getReponses_justes().size());
        intent.putExtra(ResultatExerciceActivity.NB_JUSTE, exo_randommath.getResults().getScore());
        intent.putExtra(ResultatExerciceActivity.TEMPS, timer.getText().toString());

        exo_randommath.reset_reponses();

        startActivityForResult(intent,RAN_RETOUR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // Vérification du retour à l'aide du code requête
        if (requestCode == RAN_RETOUR) {
            timer.setBase(SystemClock.elapsedRealtime());
            timer.start();

            initRound(exo_randommath.getNumero_round());
        }
    }
}