package fr.utt.if26.projetif16kemplaire.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import fr.utt.if26.projetif16kemplaire.Adapter.ExerciceRecyclerAdapter;
import fr.utt.if26.projetif16kemplaire.Model.Database.ExerciceEntity;
import fr.utt.if26.projetif16kemplaire.R;
import fr.utt.if26.projetif16kemplaire.View.Listener.RecyclerItemClickListener;
import fr.utt.if26.projetif16kemplaire.ViewModel.ExerciceViewModel;

public class ListExercicesChoisiActivity extends MenuActivity {

    private ExerciceViewModel exerciceViewModel;
    public static final String CATEGORIE = "categorie";

    public final static int CODE_RETOUR = 0;

    private TextView tv_exercice_choisi;
    private Button btn_retour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_exercices_choisi);

        Intent intent = getIntent();
        String category = intent.getStringExtra(this.CATEGORIE);

        tv_exercice_choisi = (TextView) findViewById(R.id.tv_exercice_choisi);
        btn_retour = (Button) findViewById(R.id.btn_retour);

        tv_exercice_choisi.setText(category);

        exerciceViewModel = new ViewModelProvider(this).get(ExerciceViewModel.class);

        ExerciceRecyclerAdapter recyclerAdapter = new ExerciceRecyclerAdapter();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyler_list_exercices);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(recyclerAdapter);

        btn_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retour();
            }
        });

        exerciceViewModel.getExercicesFromCategory(category).observe(this, new Observer<List<ExerciceEntity>>() {
            @Override
            public void onChanged(List<ExerciceEntity> exercices) {
                recyclerAdapter.setExercices(exercices);
            }
        });

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        ExerciceEntity exerciceChoisi = recyclerAdapter.getExercice(position);

                        if(exerciceChoisi.getNom().equals("Tables de multiplication")) {
                            startChoixTable();
                        } else if(exerciceChoisi.getNom().equals("Additions classiques")) {
                            startChoixAddition();
                        } else {
                            startExercice(exerciceChoisi);
                        }
                    }

                    @Override public void onLongItemClick(View view, int position) { }
                })
        );
    }

    public void retour() {
        setResult(RESULT_OK);
        finish();
    }

    public void startChoixTable() {
        Intent intent = new Intent(this, ChoixTableActivity.class);
        startActivityForResult(intent, CODE_RETOUR);
    }

    public void startChoixAddition() {
        Intent intent = new Intent(this, AdditionClassiqueActivity.class);
        startActivityForResult(intent, CODE_RETOUR);
    }

    public void startExercice(ExerciceEntity exerciceChoisi) {
        Intent intent = new Intent(this, AideJeuActivity.class);
        intent.putExtra(AideJeuActivity.NOM_EXO, exerciceChoisi.getNom());
        startActivityForResult(intent,CODE_RETOUR);
    }
}