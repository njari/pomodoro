package in.njari.pomodoro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;


import in.njari.pomodoro.db.PomodoroDatabase;
import in.njari.pomodoro.entity.Session;

public class TimerActivity extends AppCompatActivity {
    Runnable timer;
    Handler handler;
    TextView workTimer;
    TextView sessionState;

    private static final String TAG = "TimerActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        long sessionId = getIntent().getLongExtra("SessionId", -1);
        Log.i(TAG, "Entered with sessionId : " + sessionId);
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
       int work = session.getWork();
       int rest = session.getRest();
        Log.i(TAG, session.toDisplay());
        sessionState = (TextView) findViewById(R.id.sessionDetailDisplay);
        sessionState.setText(session.toDisplay());
        workTimer = (TextView) findViewById(R.id.workTimer);
        for (int i = 0; i < reps; i++) {
            countDownStart(work, workTimer);
        }
    }

    public void countDownStart(final int mins, final TextView workTimer) {

        handler = new Handler();
        timer = (new Runnable() {
            @Override
            public void run() {
                int timePassed = 0 ;

                while (mins - timePassed > 0 ) {
                    workTimer.setText((mins-timePassed));

                }

            }
        });

        handler.postDelayed(timer , 1 * 1000 * 60);

    }



//    public void countDownStannrt(Session session) {
//        handler = new Handler();
//        workTimer = (TextView) findViewById(R.id.workTimer);
//        sessionState = (TextView) findViewById(R.id.sessionDetailDisplay);
//        runnable = new Runnable() {
//            @Override
//            public void run() {
//                //handler.postDelayed(this, 1000);
//                try {
//                    int passedTime = 0 ;
//                    while  (W - passedTime> 0) {
//
//                        workTimer.setText(String.format("%02d", W-passedTime));
//                    } else {
//                        sessionState.setText("Take a break!");
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        handler.postDelayed(runnable, 1 * 1000 * 60);
//
//    }
}
