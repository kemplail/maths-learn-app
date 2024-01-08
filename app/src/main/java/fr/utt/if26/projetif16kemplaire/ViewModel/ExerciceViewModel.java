package fr.utt.if26.projetif16kemplaire.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import fr.utt.if26.projetif16kemplaire.Model.Database.ExerciceEntity;
import fr.utt.if26.projetif16kemplaire.Repository.ExerciceRepository;

public class ExerciceViewModel extends AndroidViewModel {

    private ExerciceRepository eRepository;

    private final LiveData<List<ExerciceEntity>> allExercices;

    public ExerciceViewModel (Application application) {
        super(application);
        eRepository = new ExerciceRepository(application);
        allExercices = eRepository.getAllExercices();
    }

    LiveData<List<ExerciceEntity>> getAllExercices() { return allExercices; }

    ExerciceEntity getExerciceById(int exerciceId) {
        return eRepository.getExerciceById(exerciceId);
    }

    public int getExerciceIdByName(String name) {
        return this.eRepository.getExerciceIdByName(name);
    }

    public LiveData<List<ExerciceEntity>> getExercicesFromCategory(String cat) {
        return eRepository.getExercicesFromCategory(cat);
    }

}
