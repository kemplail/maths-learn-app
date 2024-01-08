package fr.utt.if26.projetif16kemplaire.Repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import java.util.List;

import fr.utt.if26.projetif16kemplaire.Dao.ExerciceResultatDAO;
import fr.utt.if26.projetif16kemplaire.Dao.ResultatDAO;
import fr.utt.if26.projetif16kemplaire.Model.Database.ExerciceResultat;
import fr.utt.if26.projetif16kemplaire.Model.Database.ResultatEntity;
import fr.utt.if26.projetif16kemplaire.Model.Database.RoomProjectDatabase;

public class ResultatRepository {

    private ResultatDAO resultatDAO;
    private ExerciceResultatDAO exerciceResultatDao;

    Context context;

    public ResultatRepository(Application application) {
        RoomProjectDatabase db = RoomProjectDatabase.getDatabase(application);
        resultatDAO = db.resultatDAO();
        exerciceResultatDao = db.exerciceResultatDAO();

        context = application.getApplicationContext();
    }

    public LiveData<List<ResultatEntity>> getLastScores(int userId, int nb) {
        return resultatDAO.getLastScores(userId, nb);
    }

    public LiveData<Integer> getScoreMoyenByExoCat(int userId, String cat) {
        return resultatDAO.getScoreMoyenByExoCat(userId, cat);
    }

    public void insert (ResultatEntity results) {
        System.out.println(results.getScore());
        new insertAsyncTask(resultatDAO).execute(results);
    }

    private class insertAsyncTask extends AsyncTask<ResultatEntity, Void, Void> {

        private ResultatDAO rAsyncDao;

        insertAsyncTask(ResultatDAO dao) {
            rAsyncDao = dao;
        }

        @Override
        protected Void doInBackground(final ResultatEntity... params) {
            rAsyncDao.insert(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            Toast.makeText(context.getApplicationContext(), "Score ajouté !", Toast.LENGTH_LONG).show();
        }
    }

    public LiveData<List<ExerciceResultat>> getExerciceResultatOfUser(Integer userId, Integer nb) {
        return this.exerciceResultatDao.getExerciceResultatOfUser(userId, nb);
    }

}
