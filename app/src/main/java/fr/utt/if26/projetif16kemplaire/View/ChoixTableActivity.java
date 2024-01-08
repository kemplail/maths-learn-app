package fr.utt.if26.projetif16kemplaire.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import fr.utt.if26.projetif16kemplaire.R;

public class ChoixTableActivity extends MenuActivity {

    private NumberPicker edit_table;
    private Button btn_valider;

    public final static int MULT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_table);

        edit_table = findViewById(R.id.edit_table);
        edit_table.setMinValue(1);
        edit_table.setMaxValue(9);

        btn_valider = (Button) findViewById(R.id.btn_valider);

        btn_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMultiplication();
            }
        });
    }

    public void startMultiplication() {
        Integer valeur = edit_table.getValue();
        Intent intent = new Intent(this, TableMultiplicationActivity.class);
        intent.putExtra(TableMultiplicationActivity.TABLE_KEY, valeur.toString());
        startActivityForResult(intent,MULT);
    }
}