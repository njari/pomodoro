package in.njari.pomodoro.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import in.njari.pomodoro.entity.Session;

@Database(entities = {Session.class}, version = 1)
public abstract class PomodoroDatabase extends RoomDatabase {
    public abstract SessionDAO SessionDAO();
}