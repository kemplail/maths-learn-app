package fr.utt.if26.projetif16kemplaire.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.utt.if26.projetif16kemplaire.Model.Database.ExerciceEntity;

@Dao
public interface ExerciceDAO {

    @Query("SELECT * FROM ExerciceEntity")
    public LiveData<List<ExerciceEntity>> getAll();

    @Query("SELECT id FROM ExerciceEntity WHERE nom =:na")
    public int getExerciceIdByName(String na);

    @Query("SELECT * FROM ExerciceEntity WHERE id =:i")
    public ExerciceEntity getAnExerciceById(int i);

    @Query("SELECT * FROM ExerciceEntity WHERE type =:cat")
    public LiveData<List<ExerciceEntity>> getExercicesFromCategory(String cat);

    @Insert
    public void insert(ExerciceEntity ex);

    @Insert
    long[] insertAll(ExerciceEntity... ex);

    @Delete
    void delete(ExerciceEntity ex);

    @Update
    public void update(ExerciceEntity ex);

}
