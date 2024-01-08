package fr.utt.if26.projetif16kemplaire.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.utt.if26.projetif16kemplaire.Model.Database.ExerciceEntity;
import fr.utt.if26.projetif16kemplaire.R;

public class ExerciceRecyclerAdapter extends RecyclerView.Adapter<ExerciceRecyclerAdapter.ExerciceHolder> {

    List<ExerciceEntity> exercices;

    public ExerciceRecyclerAdapter() {
        this.exercices = new ArrayList<ExerciceEntity>();
    }

    class ExerciceHolder extends RecyclerView.ViewHolder {

        private Button btn_exercice;

        ExerciceHolder(View itemView) {
            super(itemView);
            btn_exercice = (Button) itemView.findViewById(R.id.btn_exercice);
        }

        void display(ExerciceEntity exercice) {
            btn_exercice.setText(exercice.getNom());

            if(exercice.getType().equals("Maths")) {
                if(exercice.getNom().equals("Tables de multiplication") || exercice.getNom().equals("Additions classiques")) {
                    btn_exercice.setTextColor(Color.parseColor("#41FF0E"));
                } else {
                    btn_exercice.setTextColor(Color.parseColor("#F3FF00"));
                }
            }
        }
    }

    @NonNull
    @Override
    public ExerciceRecyclerAdapter.ExerciceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.exercice_item, parent, false);

        return new ExerciceRecyclerAdapter.ExerciceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciceRecyclerAdapter.ExerciceHolder holder, int position) {
        holder.display(exercices.get(position));
    }

    public void setExercices(List<ExerciceEntity> exercices){
        this.exercices = exercices;
        notifyDataSetChanged();
    }

    public ExerciceEntity getExercice(int position) {
        return exercices.get(position);
    }

    @Override
    public int getItemCount() {
        return exercices.size();
    }

}