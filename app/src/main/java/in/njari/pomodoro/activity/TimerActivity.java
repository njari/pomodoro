package in.njari.pomodoro.activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import in.njari.pomodoro.db.PomodoroDatabase;
import in.njari.pomodoro.entity.Session;
import static java.lang.Boolean.TRUE;

public class TimerActivity extends AppCompatActivity {
    TextView timer;
    TextView sessionState;
    ProgressBar progress;
    TextView aux_text1;

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
        PomodoroDatabase db = PomodoroDatabase.getInstance(getApplicationContext());
        Session session = db.SessionDAO().findById(sessionId);
        startSession(session);
    }

    private void startSession(final Session session) {
        Log.i(TAG, "Printing the session object : " + session.toString());
        progress = (ProgressBar) findViewById(R.id.sessionProgress);
        progress.setMax(session.getReps());
        progress.setProgress(0);
        Log.i(TAG, " Progress.max : " + progress.getMax() + " Progress.progress : " + progress.getProgress());

        Log.i(TAG, session.getFocus());
        setUpForWork(session);

        CountDownTimer countDownWork = new CountDownTimer(session.getWork()*60000 , 60000) {
            int i = session.getReps();
            @Override
            public void onTick(long l)
            {
                Log.i(TAG, "Work Timer running  ...");
                updateTimer(l);
            }

            @Override
            public void onFinish() {
                progress.setProgress(progress.getProgress()+1);
                Log.i(TAG, " Progress.max : " + progress.getMax() + " Progress.progress : " + progress.getProgress());
                aux_text1 = (TextView) findViewById(R.id.aux_text1);
                aux_text1.setText("");

                i-- ;
                if ( i <= 0 ) {
                    Log.i(TAG, "Ending session... rep = " + i );
                    endSession(session);
                }
                else {
                    Log.i(TAG, "Initiating Rest ... rep = " + i );
             initiateRest(session);
                }
            }
        }.start();
    }

   private void updateTimer(long l) {
        Integer mins = (int) l / 60000 ;
        timer = (TextView) findViewById(R.id.workTimer);
        Log.i(TAG, "updating timer to " + mins.toString());
        timer.setText( mins.toString());
    }

    private void setUpForWork(Session session) {
        Log.i(TAG, "Setting up for work ... ");
        sessionState = (TextView) findViewById(R.id.sessionDetailDisplay);
        sessionState.setText(session.getFocus());
        sessionState.setBackgroundColor(getResources().getColor(R.color.SkyBlue));
        timer = (TextView) findViewById(R.id.workTimer);
        aux_text1 = (TextView) findViewById(R.id.aux_text1);
        aux_text1.setText("Focus on");

    }

    private void initiateRest( final Session session ) {
        TextView sessionState = (TextView) findViewById(R.id.sessionDetailDisplay);
        sessionState.setText("Take a break!");
        sessionState.setBackgroundColor(getResources().getColor(R.color.Yellow));
        // setup a timer
        CountDownTimer countDownRest = new CountDownTimer(session.getRest()*60*1000, 60000) {
            @Override
            public void onTick(long l) {
                updateTimer(l);
                if (l < 1200000) {
                    // beep thrice
                    MediaPlayer mediaPlayer =  MediaPlayer.create(TimerActivity.this, R.raw.goes_without_saying);
                        for (int i = 0 ; i < 3  ; i++) { mediaPlayer.start(); }
                }
            }

            @Override
            public void onFinish() {
                TextView sessionState = (TextView) findViewById(R.id.sessionDetailDisplay);
                setUpForWork(session);
            }
        }.start();


    }

    private void endSession(Session session) {
        TextView sessionState = (TextView) findViewById(R.id.sessionDetailDisplay);
        sessionState.setText("Your Session is complete! Congratulations!");
        session.setCompleted(TRUE);
        sessionState.setBackgroundColor(getResources().getColor(R.color.SuccessBlue));
        PomodoroDatabase db = PomodoroDatabase.getInstance(getApplicationContext());
        db.SessionDAO().updateSession(session);

    }

}



