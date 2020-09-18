package in.njari.pomodoro.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import in.njari.pomodoro.entity.Session;

@Database(entities = {Session.class}, version = 1)
public abstract class PomodoroDatabase extends RoomDatabase {

    private static final String DB_NAME = "pomodoro.db";
    private static volatile PomodoroDatabase instance;

    public static synchronized PomodoroDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    PomodoroDatabase() {};

    private static PomodoroDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                PomodoroDatabase.class,
                DB_NAME).allowMainThreadQueries().build();
    }
    public abstract SessionDAO SessionDAO();
}
