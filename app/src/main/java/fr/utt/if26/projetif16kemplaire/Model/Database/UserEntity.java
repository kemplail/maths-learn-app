package fr.utt.if26.projetif16kemplaire.Model.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "pseudo")
    private String pseudo;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "age")
    private int age;

    @ColumnInfo(name = "parties_jouees")
    private int parties_jouees;

    @ColumnInfo(name = "parties_gagnees")
    private int parties_gagnees;

    /*
     * Getters and Setters
     * */
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return this.pseudo;
    }

    public void setPseudo(String ps) {
        this.pseudo = ps;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String ps) {
        this.password = ps;
    }

    public void setAge(int ag) { this.age = ag; }

    public int getAge() { return this.age; }

    public int getParties_gagnees() {
        return parties_gagnees;
    }

    public void setParties_gagnees(int parties_gagnees) {
        this.parties_gagnees = parties_gagnees;
    }

    public int getParties_jouees() {
        return parties_jouees;
    }

    public void setParties_jouees(int parties_jouees) {
        this.parties_jouees = parties_jouees;
    }

    public void addPartie_jouee() {
        this.parties_jouees = this.parties_jouees+1;
    }

    public void addPartie_gagnee() {
        this.parties_gagnees = this.parties_gagnees+1;
    }

}
