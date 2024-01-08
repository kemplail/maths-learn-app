package fr.utt.if26.projetif16kemplaire.Model.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ResultatEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "user_id")
    private int user_id;

    @ColumnInfo(name = "exercice_id")
    private int exercice_id;

    @ColumnInfo(name = "score")
    private int score;

    /*
     * Getters and Setters
     * */

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public void setUser_id(int id) {
        this.user_id = id;
    }

    public int getExercice_id() {
        return this.exercice_id;
    }

    public void setExercice_id(int id) {
        this.exercice_id = id;
    }

    public void setScore(int id) {
        this.score = id;
    }

    public int getScore() {
        return this.score;
    }
}
