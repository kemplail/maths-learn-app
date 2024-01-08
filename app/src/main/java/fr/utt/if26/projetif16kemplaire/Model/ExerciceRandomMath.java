package fr.utt.if26.projetif16kemplaire.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import fr.utt.if26.projetif16kemplaire.Model.Database.ExerciceEntity;
import fr.utt.if26.projetif16kemplaire.Model.Database.ResultatEntity;

public class ExerciceRandomMath extends ExerciceEntity {

    private int operation;
    private int numero_round;

    private ArrayList<ArrayList> rounds;
    private ArrayList<Integer> reponses_justes;

    private int nombre1;
    private int nombre2;

    private int taillemin;
    private int taillemax;

    private ResultatEntity results;

    public static final int MULTIPLICATION = 0;
    public static final int ADDITION = 1;
    public static final int SOUSTRACTION = 2;

    public ExerciceRandomMath(int operation, int idexo, int iduser, int taillemin, int taillemax) {

        if(operation == MULTIPLICATION) {
            setNom("Randommaths Multiplication");
        } else if(operation == ADDITION) {
            setNom("Randommaths Addition");
        } else {
            setNom("Randommaths Soustraction");
        }

        results = new ResultatEntity();
        results.setExercice_id(idexo);
        results.setUser_id(iduser);

        setType("Confirme");

        setTaillemin(taillemin);
        setTaillemax(taillemax);

        setOperation(operation);

        reponses_justes = new ArrayList<Integer>();
        rounds = new ArrayList<>();

        reset_reponses();

        initExercice();
    }

    public void reset_reponses() {
        reset_nombres();

        numero_round = 0;
    }

    private int random(int high, int low) {
        return((int)(Math.random() * (high+1-low)) + low);
    }

    private void initExercice() {

        ArrayList<Integer> nombre_gen;

        for (int i = 0; i < 10; i++) {
            nombre_gen = new ArrayList<>();
            for(int k = 0; k < 8; k++) {
                if (getOperation() == MULTIPLICATION) {
                    nombre_gen.add(random(getTaillemin(),getTaillemax()));
                } else {
                    nombre_gen.add(random(getTaillemax(),getTaillemin()));
                }
            }

            ArrayList<Integer> choix = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7));
            Random rand = new Random();

            int indice1 = choix.get(rand.nextInt(choix.size()));
            choix.remove(indice1);

            int indice2 = choix.get(rand.nextInt(choix.size()));

            switch (getOperation()) {
                case MULTIPLICATION :
                    reponses_justes.add(nombre_gen.get(indice1)*nombre_gen.get(indice2));
                    break;
                case ADDITION :
                    reponses_justes.add(nombre_gen.get(indice1)+nombre_gen.get(indice2));
                    break;
                case SOUSTRACTION :
                    reponses_justes.add(nombre_gen.get(indice1)-nombre_gen.get(indice2));
                    break;
            }

            rounds.add(nombre_gen);
        }
    }

    public ArrayList<Integer> getRoundNumbers(int numeroround) {
        return this.rounds.get(numeroround);
    }

    public int getNumero_round() {
        return numero_round;
    }

    public void setNumero_round(int numero_round) {
        this.numero_round = numero_round;
    }

    public ResultatEntity getResults() {
        return results;
    }

    public void setResults(ResultatEntity results) {
        this.results = results;
    }

    public int getResultatActuel() {

        int res = 0;

        switch (getOperation()) {
            case MULTIPLICATION :
                res = getNombre1()*getNombre2();
                break;
            case ADDITION :
                res = getNombre1()+getNombre2();
                break;
            case SOUSTRACTION :
                res = getNombre1()-getNombre2();
                break;
        }

        return res;
    }


    public void validerResultatActuel() {
        if (verifyResultatRound(numero_round)) {
            results.setScore(results.getScore() + 1);
        }
    }

    public void reset_nombres() {
        setNombre1(0);
        setNombre2(0);
    }

    public boolean is_exercice_reussi() {
        return this.results.getScore() == 10;
    }

    public static Integer getCatExo(String nom_exo) {
        int op = 0;
        switch (nom_exo) {
            case "Randommaths Multiplication":
                op = ExerciceRandomMath.MULTIPLICATION;
                break;
            case "Randommaths Addition":
                op = ExerciceRandomMath.ADDITION;
                break;
            case "Randommaths Soustraction":
                op = ExerciceRandomMath.SOUSTRACTION;
                break;
        }
        return op;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public ArrayList<ArrayList> getRounds() {
        return rounds;
    }

    public void setRounds(ArrayList<ArrayList> rounds) {
        this.rounds = rounds;
    }

    public ArrayList<Integer> getReponses_justes() {
        return reponses_justes;
    }

    public int getReponseJusteOfRound(int numeroround) {
        return this.reponses_justes.get(numeroround);
    }

    public boolean verifyResultatRound(int numeroround) {
        if (getResultatActuel() == getReponseJusteOfRound(numeroround)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean is_exercice_finished() {
        return this.numero_round >= 9;
    }

    public void add_round() {
        this.numero_round += 1;
    }

    public void setReponses_justes(ArrayList<Integer> reponses) {
        this.reponses_justes = reponses;
    }

    public int getNombre1() {
        return nombre1;
    }

    public void setNombre1(int nombre1) {
        this.nombre1 = nombre1;
    }

    public int getNombre2() {
        return nombre2;
    }

    public void setNombre2(int nombre2) {
        this.nombre2 = nombre2;
    }

    public int getTaillemin() {
        return taillemin;
    }

    public void setTaillemin(int taillemin) {
        this.taillemin = taillemin;
    }

    public int getTaillemax() {
        return taillemax;
    }

    public void setTaillemax(int taillemax) {
        this.taillemax = taillemax;
    }

}
