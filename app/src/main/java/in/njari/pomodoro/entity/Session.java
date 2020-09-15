package in.njari.pomodoro.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Session {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    @ColumnInfo(name = "focus")
    private String focus;
    @ColumnInfo
    private int hrs;
    @ColumnInfo
    private int reps = 4;
    @ColumnInfo
    private int work = 25;
    @ColumnInfo
    private int rest = 5;

    public int getId() {
        return id;
    }

    public String getFocus() {
        return focus;
    }

    public int getHrs() {
        return hrs;
    }

    public int getReps() {
        return reps;
    }

    public int getWork() {
        return work;
    }

    public int getRest() {
        return rest;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public void setHrs(int hrs) {
        this.hrs = hrs;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setWork(int work) {
        this.work = work;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }

    @Ignore
    public Session() {
    }
    @Ignore
    public Session(int id, String focus, int hrs) {
        this.id = id;
        this.focus = focus;
        this.hrs = hrs;
        this.reps = hrs * 2;
        this.work = 25;
        this.rest = 5;
    }

    public Session(int id, String focus, int hrs, int reps, int work, int rest) {
        this.id = id;
        this.focus = focus;
        this.hrs = hrs;
        this.reps = reps;
        this.work = work;
        this.rest = rest;
    }


    public String toDisplay() {
        return
                "Focus on : " + focus +
                " for " + hrs + " hrs";
    }
}
