package fr.utt.if26.projetif16kemplaire.Repository;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import java.util.List;

import fr.utt.if26.projetif16kemplaire.Dao.UserDAO;
import fr.utt.if26.projetif16kemplaire.Model.Database.RoomProjectDatabase;
import fr.utt.if26.projetif16kemplaire.Model.Database.UserEntity;
import fr.utt.if26.projetif16kemplaire.View.EspaceUtilisateurActivity;
import fr.utt.if26.projetif16kemplaire.View.ListUsersActivity;
import fr.utt.if26.projetif16kemplaire.View.MainActivity;

public class UserRepository {

    private UserDAO userDAO;
    private LiveData<List<UserEntity>> allUsers;

    Context context;

    public UserRepository(Application application) {
        RoomProjectDatabase db = RoomProjectDatabase.getDatabase(application);
        userDAO = db.userDAO();
        allUsers = userDAO.getAll();

        context = application.getApplicationContext();
    }

    public LiveData<List<UserEntity>> getAllUsers() {
        return allUsers;
    }

    public UserEntity getUserById(int userId) { return this.userDAO.getUserById(userId); }

    public boolean isValidAccount(int userId, String password) {
        UserEntity user = userDAO.getUserById(userId);
        return user.getPassword().equals(password);
    }

    public void insert (UserEntity user) {
        new insertAsyncTask(userDAO).execute(user);
    }

    private class insertAsyncTask extends AsyncTask<UserEntity, Void, Void> {

        private UserDAO uAsyncTaskDao;

        insertAsyncTask(UserDAO dao) {
            uAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final UserEntity... params) {
            uAsyncTaskDao.insert(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            Toast.makeText(context.getApplicationContext(), "Vous êtes bien inscrit !", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context.getApplicationContext(), ListUsersActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public void update (UserEntity user) {
        new updateAsyncTask(userDAO).execute(user);
    }

    public void updateInfos (UserEntity user) {
        new updateInfosAsyncTask(userDAO).execute(user);
    }

    public void deleteUser (UserEntity user) {
        new deleteAsyncTask(userDAO).execute(user);
    }

    private class updateAsyncTask extends AsyncTask<UserEntity, Void, Void> {

        private UserDAO uAsyncTaskDao;

        updateAsyncTask(UserDAO dao) {
            uAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final UserEntity... params) {
            uAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    private class updateInfosAsyncTask extends updateAsyncTask {

        updateInfosAsyncTask(UserDAO dao) {
            super(dao);
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            Toast.makeText(context.getApplicationContext(), "Informations modifiées !", Toast.LENGTH_LONG).show();
            ((Activity) EspaceUtilisateurActivity.activity).setResult(Activity.RESULT_OK);
            ((Activity) EspaceUtilisateurActivity.activity).finish();
        }

    }

    private class deleteAsyncTask extends AsyncTask<UserEntity, Void, Void> {

        private UserDAO uAsyncTaskDao;

        deleteAsyncTask(UserDAO dao) {
            uAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final UserEntity... params) {
            uAsyncTaskDao.deleteResultatOfUser(params[0].getId());
            uAsyncTaskDao.delete(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            Toast.makeText(context.getApplicationContext(), "Le compte a bien été supprimé !", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
