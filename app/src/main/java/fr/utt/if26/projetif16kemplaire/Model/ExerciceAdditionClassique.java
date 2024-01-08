package fr.utt.if26.projetif16kemplaire.Model;

import java.util.ArrayList;

import fr.utt.if26.projetif16kemplaire.Model.Database.ExerciceEntity;
import fr.utt.if26.projetif16kemplaire.Model.Database.ResultatEntity;

public class ExerciceAdditionClassique extends ExerciceEntity {

    private ArrayList<Integer> numeros1;
    private ArrayList<Integer> numeros2;

    private ArrayList<Integer> reponses;
    private ResultatEntity results;

    private int numero_round;

    public ExerciceAdditionClassique(int idexo, int iduser) {
        setId(idexo);
        setNom("Additions classiques");
        setType("Debutant");

        results = new ResultatEntity();
        results.setExercice_id(idexo);
        results.setUser_id(iduser);

        numeros1 = new ArrayList<>();
        numeros2 = new ArrayList<>();

        reset_reponses();

        createAdditions();
    }

    private int random(int high, int low) {
        return((int)(Math.random() * (high+1-low)) + low);
    }

    public void createAdditions() {

        for (int i = 0; i < 10; i++) {

            int nb1 = random(10,1);
            int nb2 = random(10,1);

            numeros1.add(nb1);
            numeros2.add(nb2);
        }
    }

    public int verif_resultats() {
        int nbfautes = 0;

        for (int i = 0; i < 10; i++) {
            if (numeros1.get(i)+numeros2.get(i) != reponses.get(i)) {
                nbfautes++;
            }
        }

        return nbfautes;
    }

    public void set_reponse(Integer reponse) {
        if(numero_round == reponses.size()) {
            reponses.add(reponse);
        } else {
            reponses.set(numero_round,reponse);
        }
    }

    public void reset_reponses() {
        reponses = new ArrayList<Integer>();
        numero_round = 0;
    }

    public void set_resultats() {
        Integer nb_fautes = verif_resultats();
        results.setScore(10-nb_fautes);
    }

    public void add_round() {
        this.numero_round = this.numero_round + 1;
    }

    public boolean is_exercice_finished() {
        return this.numero_round >= 9;
    }

    public void remove_round() {
        this.numero_round = this.numero_round - 1;
    }

    public int getNumero_round() {
        return numero_round;
    }

    public void setNumero_round(int numero_round) {
        this.numero_round = numero_round;
    }

    public boolean is_exercice_reussi() {
        return (10-verif_resultats() == 10);
    }

    public ArrayList<Integer> getNumeros1() {
        return numeros1;
    }

    public void setNumeros1(ArrayList<Integer> numeros1) {
        this.numeros1 = numeros1;
    }

    public ArrayList<Integer> getNumeros2() {
        return numeros2;
    }

    public void setNumeros2(ArrayList<Integer> numeros2) {
        this.numeros2 = numeros2;
    }

    public ArrayList<Integer> getReponses() {
        return reponses;
    }

    public void setReponses(ArrayList<Integer> reponses) {
        this.reponses = reponses;
    }

    public ResultatEntity getResults() {
        return results;
    }

    public void setResults(ResultatEntity results) {
        this.results = results;
    }

}
