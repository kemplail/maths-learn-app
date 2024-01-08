package fr.utt.if26.projetif16kemplaire.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.utt.if26.projetif16kemplaire.Model.Database.UserEntity;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM UserEntity")
    LiveData<List<UserEntity>> getAll();

    @Query("SELECT * FROM UserEntity WHERE id = :i")
    UserEntity getUserById(int i);

    @Query("DELETE FROM ResultatEntity WHERE user_id = :userId")
    public void deleteResultatOfUser(Integer userId);

    @Insert
    void insert(UserEntity user);

    @Insert
    long[] insertAll(UserEntity... user);

    @Delete
    void delete(UserEntity user);

    @Update
    void update(UserEntity user);

}