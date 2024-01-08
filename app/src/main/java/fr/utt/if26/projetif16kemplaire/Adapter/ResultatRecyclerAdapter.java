package fr.utt.if26.projetif16kemplaire.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.utt.if26.projetif16kemplaire.Model.Database.ExerciceResultat;
import fr.utt.if26.projetif16kemplaire.R;

public class ResultatRecyclerAdapter extends RecyclerView.Adapter<ResultatRecyclerAdapter.ResultatHolder> {

    List<ExerciceResultat> resultats;

    public ResultatRecyclerAdapter() {
        this.resultats = new ArrayList<ExerciceResultat>();
    }

    class ResultatHolder extends RecyclerView.ViewHolder {

        private TextView tv_exercice_nom;
        private TextView tv_score;

        ResultatHolder(View itemView) {
            super(itemView);
            tv_exercice_nom = (TextView) itemView.findViewById(R.id.tv_exercice_nom);
            tv_score = (TextView) itemView.findViewById(R.id.tv_score);
        }

        void display(ExerciceResultat resultat) {
            tv_exercice_nom.setText(resultat.exerciceName);
            tv_score.setText(Integer.toString(resultat.score));
        }
    }

    @NonNull
    @Override
    public ResultatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.resultat_item, parent, false);

        return new ResultatRecyclerAdapter.ResultatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultatRecyclerAdapter.ResultatHolder holder, int position) {
        holder.display(resultats.get(position));
    }

    public void setResultats(List<ExerciceResultat> resultats){
        this.resultats = resultats;
        notifyDataSetChanged();
    }

    public ExerciceResultat getResultat(int position) {
        return resultats.get(position);
    }

    @Override
    public int getItemCount() {
        return resultats.size();
    }
}
