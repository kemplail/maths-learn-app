package fr.utt.if26.projetif16kemplaire.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import fr.utt.if26.projetif16kemplaire.Model.Database.ExerciceResultat;
import fr.utt.if26.projetif16kemplaire.Model.Database.ResultatEntity;
import fr.utt.if26.projetif16kemplaire.Repository.ResultatRepository;

public class ResultatViewModel extends AndroidViewModel {

    private ResultatRepository rRepository;

    public ResultatViewModel (Application application) {
        super(application);
        rRepository = new ResultatRepository(application);
    }

    LiveData<List<ResultatEntity>> getLastScores(int userId, int nb) {
        return rRepository.getLastScores(userId, nb);
    }

    public LiveData<Integer> getScoreMoyenByExoCat(int userId, String cat) {
        return rRepository.getScoreMoyenByExoCat(userId, cat);
    }

    public void insert(ResultatEntity results) {
        System.out.println(results.getScore());
        rRepository.insert(results);
    }

    public LiveData<List<ExerciceResultat>> getExerciceResultatOfUser(Integer userId, Integer nb) {
        return this.rRepository.getExerciceResultatOfUser(userId, nb);
    }

}
