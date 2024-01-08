package fr.utt.if26.projetif16kemplaire.Repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import fr.utt.if26.projetif16kemplaire.Dao.ExerciceDAO;
import fr.utt.if26.projetif16kemplaire.Model.Database.ExerciceEntity;
import fr.utt.if26.projetif16kemplaire.Model.Database.RoomProjectDatabase;

public class ExerciceRepository {

    private ExerciceDAO exerciceDAO;

    private LiveData<List<ExerciceEntity>> allExercices;

    Context context;

    public ExerciceRepository(Application application) {
        RoomProjectDatabase db = RoomProjectDatabase.getDatabase(application);
        exerciceDAO = db.exerciceDAO();

        allExercices = exerciceDAO.getAll();

        context = application.getApplicationContext();
    }

    public LiveData<List<ExerciceEntity>> getAllExercices() {
        return allExercices;
    }

    public ExerciceEntity getExerciceById(int exerciceId) {
        return this.exerciceDAO.getAnExerciceById(exerciceId);
    }

    public int getExerciceIdByName(String name) {
        return this.exerciceDAO.getExerciceIdByName(name);
    }

    public LiveData<List<ExerciceEntity>> getExercicesFromCategory(String cat) {
        return this.exerciceDAO.getExercicesFromCategory(cat);
    }

}
