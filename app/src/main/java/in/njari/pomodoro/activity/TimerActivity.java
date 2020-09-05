package in.njari.pomodoro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;


import in.njari.pomodoro.db.PomodoroDatabase;
import in.njari.pomodoro.entity.Session;

public class TimerActivity extends AppCompatActivity {
    Runnable runnable;
    Handler handler;
    TextView workTimer;
    TextView sessionState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        int sessionId = getIntent().getIntExtra("sessionId", -1);

        if (sessionId == -1) {
            Intent intent = new Intent(TimerActivity.this, MainActivity.class);
            startActivity(intent);
        }

        PomodoroDatabase db = Room.databaseBuilder(getApplicationContext(),
                PomodoroDatabase.class, "pomodoro").allowMainThreadQueries().build();

        Session session = db.SessionDAO().findById(sessionId);
        startSession(session);
    }

    public void startSession(Session session) {
        int reps = session.getReps();

        for (int i = 0; i < reps; i++) {
            countDownStart(session);
        }
    }


    public void countDownStart(Session session) {
        handler = new Handler();
        workTimer = (TextView) findViewById(R.id.workTimer);
        sessionState = (TextView) findViewById(R.id.sessionDetailDisplay);
        final int[] D = {1};


        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {

                    if (D[0] > 0) {

                        D[0] -= 1;
                        workTimer.setText(String.format("%02d", D[0]));
                    } else {
                        sessionState.setText("Take a break!");


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1 * 1000 * 60);

    }
}
