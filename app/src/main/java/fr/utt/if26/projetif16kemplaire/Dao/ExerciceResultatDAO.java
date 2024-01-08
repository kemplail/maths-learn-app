package fr.utt.if26.projetif16kemplaire.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import fr.utt.if26.projetif16kemplaire.Model.Database.ExerciceResultat;

@Dao
public interface ExerciceResultatDAO {

    @Query("SELECT ExerciceEntity.nom AS exerciceName, ResultatEntity.score AS score " +
            "FROM ExerciceEntity, ResultatEntity " +
            "WHERE ResultatEntity.exercice_id = ExerciceEntity.id AND ResultatEntity.user_id = :userId ORDER BY ResultatEntity.id DESC LIMIT :nb")
    public LiveData<List<ExerciceResultat>> getExerciceResultatOfUser(Integer userId, Integer nb);
}