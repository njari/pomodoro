package in.njari.pomodoro.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import in.njari.pomodoro.entity.Session;

@Dao
public interface SessionDAO {

    @Insert
    public long create(Session session) ;

    @Query("SELECT * FROM session WHERE id = :id")
    public Session findById(long id);

}
