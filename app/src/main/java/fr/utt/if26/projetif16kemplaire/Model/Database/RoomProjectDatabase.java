package fr.utt.if26.projetif16kemplaire.Model.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.utt.if26.projetif16kemplaire.Dao.ExerciceDAO;
import fr.utt.if26.projetif16kemplaire.Dao.ExerciceResultatDAO;
import fr.utt.if26.projetif16kemplaire.Dao.ResultatDAO;
import fr.utt.if26.projetif16kemplaire.Dao.UserDAO;

@Database(entities = {UserEntity.class, ExerciceEntity.class, ResultatEntity.class}, version = 2, exportSchema = false)
public abstract class RoomProjectDatabase extends RoomDatabase {

    public abstract UserDAO userDAO();
    public abstract ExerciceDAO exerciceDAO();
    public abstract ResultatDAO resultatDAO();
    public abstract ExerciceResultatDAO exerciceResultatDAO();

    private static volatile RoomProjectDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static RoomProjectDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomProjectDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    RoomProjectDatabase.class, "project_database")
                            .allowMainThreadQueries()
                            .addCallback(rdc)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    static RoomDatabase.Callback rdc = new RoomDatabase.Callback() {
        public void onCreate (SupportSQLiteDatabase db) {
            databaseWriteExecutor.execute(() -> {

                ExerciceDAO exerciceDAO = INSTANCE.exerciceDAO();

                ExerciceEntity exo_math_1 = new ExerciceEntity();
                exo_math_1.setNom("Randommaths Multiplication");
                exo_math_1.setType("Confirme");

                ExerciceEntity exo_math_2 = new ExerciceEntity();
                exo_math_2.setNom("Randommaths Addition");
                exo_math_2.setType("Confirme");

                ExerciceEntity exo_math_3 = new ExerciceEntity();
                exo_math_3.setNom("Randommaths Soustraction");
                exo_math_3.setType("Confirme");

                ExerciceEntity exo_math_4 = new ExerciceEntity();
                exo_math_4.setNom("Tables de multiplication");
                exo_math_4.setType("Debutant");

                ExerciceEntity exo_math_5 = new ExerciceEntity();
                exo_math_5.setNom("Additions classiques");
                exo_math_5.setType("Debutant");

                exerciceDAO.insert(exo_math_1);
                exerciceDAO.insert(exo_math_2);
                exerciceDAO.insert(exo_math_3);
                exerciceDAO.insert(exo_math_4);
                exerciceDAO.insert(exo_math_5);
            });
        }
        public void onOpen (SupportSQLiteDatabase db) {
            // do something every time database is open
        }
    };
}