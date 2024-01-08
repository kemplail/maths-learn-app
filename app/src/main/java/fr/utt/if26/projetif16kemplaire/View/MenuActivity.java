package fr.utt.if26.projetif16kemplaire.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import fr.utt.if26.projetif16kemplaire.MyApplication;
import fr.utt.if26.projetif16kemplaire.R;

public class MenuActivity extends AppCompatActivity {

    Menu menu;
    MyApplication myapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myapp = (MyApplication)getApplicationContext();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        this.menu = menu;

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_accueil:
                startAccueil();
                break;
            case R.id.menu_deconnexion:
                deconnection();
                break;
            case R.id.menu_espace_user:
                startEspaceUser();
                break;
            case R.id.menu_statistiques:
                startStatistiques();
                break;
            case R.id.menu_exos_debutant:
                startExosDebutant();
                break;
            case R.id.menu_exos_confirme:
                startExosConfirme();
                break;
        }

        return true;
    }

    public void startExosConfirme() {
        Intent intent = new Intent(this, ListExercicesChoisiActivity.class);
        intent.putExtra(ListExercicesChoisiActivity.CATEGORIE, "Confirme");
        startActivityForResult(intent, AccueilJeuActivity.CODE_CONFIRME);
    }

    public void startExosDebutant() {
        Intent intent = new Intent(this, ListExercicesChoisiActivity.class);
        intent.putExtra(ListExercicesChoisiActivity.CATEGORIE, "Debutant");
        startActivityForResult(intent,AccueilJeuActivity.CODE_DEBUTANT);
    }

    public void startStatistiques() {
        Intent intent = new Intent(this, MesStatistiquesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void startAccueil() {
        Intent intent = new Intent(this, AccueilJeuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void startEspaceUser() {
        Intent intent = new Intent(this, EspaceUtilisateurActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void deconnection() {
        myapp.resetUserConnecte();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}