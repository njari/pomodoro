package in.njari.pomodoro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import in.njari.pomodoro.db.PomodoroDatabase;
import in.njari.pomodoro.entity.Session;

public class TimerActivity extends AppCompatActivity {
    TextView timer;
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

    private void startSession(final Session session) {
        long work = session.getWork()*60000;
        Log.i(TAG, session.toDisplay());
        final long rest = session.getRest()*60000;
        setUpForWork(session);

        CountDownTimer countDownWork = new CountDownTimer(work , 60000) {
            int i = session.getReps();
            @Override
            public void onTick(long l) {
                updateTimer(l);
            }

            @Override
            public void onFinish() {
                i-- ;
                if ( i == 0 ) {
                    endSession();
                }
                else {
             initiateRest(session);
                }
            }
        }.start();
    }

   private void updateTimer(long l) {
        Integer mins = (int) l / 60000 ;
        timer = (TextView) findViewById(R.id.workTimer);
        timer.setText( mins.toString());
    }

    private void setUpForWork(Session session) {
        sessionState = (TextView) findViewById(R.id.sessionDetailDisplay);
        sessionState.setText(session.toDisplay());
    }

    private void initiateRest( final Session session ) {
        TextView sessionState = (TextView) findViewById(R.id.sessionDetailDisplay);
        sessionState.setText("Take a break!");
        // setup a timer
        CountDownTimer countDownRest = new CountDownTimer(session.getRest()*60*1000, 60000) {
            @Override
            public void onTick(long l) {
                updateTimer(l);
            }
            @Override
            public void onFinish() {
                TextView sessionState = (TextView) findViewById(R.id.sessionDetailDisplay);
                setUpForWork(session);
            }
        }.start();


    }

    private void endSession() {
        TextView sessionState = (TextView) findViewById(R.id.sessionDetailDisplay);
        sessionState.setText("Your Session is complete! Congratulations!");
    }

}



