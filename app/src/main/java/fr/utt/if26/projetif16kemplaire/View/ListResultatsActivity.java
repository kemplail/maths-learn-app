package fr.utt.if26.projetif16kemplaire.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

import fr.utt.if26.projetif16kemplaire.Adapter.ResultatRecyclerAdapter;
import fr.utt.if26.projetif16kemplaire.Model.Database.ExerciceResultat;
import fr.utt.if26.projetif16kemplaire.MyApplication;
import fr.utt.if26.projetif16kemplaire.R;
import fr.utt.if26.projetif16kemplaire.ViewModel.ResultatViewModel;

public class ListResultatsActivity extends MenuActivity {

    private ResultatViewModel resultatViewModel;
    private MyApplication myapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_resultats);

        myapp = (MyApplication) getApplicationContext();

        resultatViewModel = new ViewModelProvider(this).get(ResultatViewModel.class);

        ResultatRecyclerAdapter recyclerAdapter = new ResultatRecyclerAdapter();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyler_list_resultats);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(recyclerAdapter);

        Button btn_retour = (Button) findViewById(R.id.list_back);

        btn_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retour();
            }
        });

        resultatViewModel.getExerciceResultatOfUser(myapp.getUser_connecte().getId(), 100).observe(this, new Observer<List<ExerciceResultat>>() {
            @Override
            public void onChanged(List<ExerciceResultat> resultats) {
                recyclerAdapter.setResultats(resultats);
            }
        });
    }

    public void retour() {
        setResult(RESULT_OK);
        finish();
    }
}