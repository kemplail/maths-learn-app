package fr.utt.if26.projetif16kemplaire.Model;

import java.util.ArrayList;

import fr.utt.if26.projetif16kemplaire.Model.Database.ExerciceEntity;
import fr.utt.if26.projetif16kemplaire.Model.Database.ResultatEntity;

public class TableMultiplication extends ExerciceEntity {

    private int numerotable;

    private ArrayList<Integer> reponses;
    private ResultatEntity results;

    public TableMultiplication(int valeur, int idexo, int iduser) {
        setId(idexo);
        setNom("Tables de multiplication");
        setType("Debutant");

        results = new ResultatEntity();
        results.setExercice_id(idexo);
        results.setUser_id(iduser);

        reset_reponses();

        setnumeroTable(valeur);
    }

    public int verif_resultats() {
        int nb_fautes = 0;

        for (int i = 0; i < reponses.size(); i++) {
            if (reponses.get(i) != 0) {
                if ((i+1)*numerotable != reponses.get(i)) {
                    nb_fautes++;
                }
            } else {
                nb_fautes++;
            }
        }
        return nb_fautes;
    }

    public void set_resultats() {
        Integer nb_fautes = verif_resultats();
        results.setScore(10-nb_fautes);
    }

    public void reset_reponses() {
        reponses = new ArrayList<Integer>();
    }

    public boolean is_exercice_reussi() {
        return (10-verif_resultats() == 10);
    }

    public int getNumerotable() {
        return numerotable;
    }

    public void setNumerotable(int numerotable) {
        this.numerotable = numerotable;
    }

    public ArrayList<Integer> getReponses() {
        return reponses;
    }

    public void setReponses(ArrayList<Integer> reponses) {
        this.reponses = reponses;
    }

    public void setnumeroTable(int num) {
        this.numerotable = num;
    }

    public int getnumeroTable() {
        return this.numerotable;
    }

    public void setResults(ResultatEntity results) { this.results = results; }

    public ResultatEntity getResults() { return this.results; }
}