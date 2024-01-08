package fr.utt.if26.projetif16kemplaire.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.utt.if26.projetif16kemplaire.Model.Database.ResultatEntity;

@Dao
public interface ResultatDAO {

    @Query("SELECT * FROM ResultatEntity")
    LiveData<List<ResultatEntity>> getAll();

    @Query("SELECT * FROM ResultatEntity WHERE user_id = :userId ORDER BY id DESC LIMIT :nb")
    LiveData<List<ResultatEntity>> getLastScores(int userId, int nb);

    @Query("SELECT AVG(score) FROM ResultatEntity WHERE user_id = :userId AND exercice_id IN (SELECT id FROM ExerciceEntity WHERE type = :cat)")
    LiveData<Integer> getScoreMoyenByExoCat(int userId, String cat);

    @Insert
    void insert(ResultatEntity res);

    @Insert
    long[] insertAll(ResultatEntity... res);

    @Delete
    void delete(ResultatEntity res);

    @Update
    void update(ResultatEntity res);

}
