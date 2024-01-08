package fr.utt.if26.projetif16kemplaire;

import android.app.Application;

import fr.utt.if26.projetif16kemplaire.Model.Database.UserEntity;

public class MyApplication extends Application {

    UserEntity user_connecte;

    public void setUser_connecte(UserEntity user_connecte) {
        this.user_connecte = user_connecte;
    }

    public UserEntity getUser_connecte() {
        return user_connecte;
    }

    public void startAsInvite() {
        UserEntity invite = new UserEntity();
        invite.setPseudo("Invité");
        invite.setAge(0);
        invite.setParties_jouees(0);
        invite.setParties_gagnees(0);

        setUser_connecte(invite);
    }

    public void resetUserConnecte() {
        this.user_connecte = null;
    }
}