package fr.utt.if26.projetif16kemplaire.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import fr.utt.if26.projetif16kemplaire.R;

public class ExerciceParametersActivity extends MenuActivity {

    private RadioGroup choix_taille;
    public static final String NOM_EXO = "nom_exo";

    private Button btn_valider;
    private Button btn_retour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercice_parameters);

        choix_taille = findViewById(R.id.choix_taille);
        btn_valider = findViewById(R.id.btn_valider);
        btn_retour = findViewById(R.id.btn_retour);

        btn_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valide();
            }
        });

        btn_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retour();
            }
        });
    }

    public void valide() {
        RadioButton radio_checked = findViewById(choix_taille.getCheckedRadioButtonId());
        String reponse = radio_checked.getText().toString();

        int taillemin = 0;
        int taillemax = 0;

        switch(reponse) {
            case "1 - 10" : taillemin = 1; taillemax = 10;
                break;
            case "10 - 100" : taillemin = 10; taillemax = 100;
                break;
            case "100 - 1000" : taillemin = 100; taillemax = 1000;
                break;
        }

        Intent intent = new Intent(this, RandomMathActivity.class);
        intent.putExtra(RandomMathActivity.NOM_EXO, getIntent().getStringExtra(NOM_EXO));
        intent.putExtra(RandomMathActivity.TAILLE_MIN, Integer.toString(taillemin));
        intent.putExtra(RandomMathActivity.TAILLE_MAX, Integer.toString(taillemax));
        startActivity(intent);

    }

    public void retour() {
        setResult(RESULT_OK);
        finish();
    }
}