package fr.utt.if26.projetif16kemplaire.Model.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ExerciceEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name="nom")
    private String nom;

    /*
     * Getters and Setters
     * */
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nm) { this.nom = nm; }

    public String getNom() { return this.nom; }

    public String getType() {
        return this.type;
    }

    public void setType(String t) {
        this.type = t;
    }

}
