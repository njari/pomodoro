package in.njari.pomodoro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class BeginOrModifyActivity extends AppCompatActivity {
    Button startSession;

    private static final String TAG = "BeginOrModifyActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_or_modify);
        getIntent().getLongExtra("SessionId", -1);
        final long id = getIntent().getLongExtra("SessionId", -1);
        Log.i(TAG, " Entered with sessionId : " + id );

        startSession = (Button) findViewById(R.id.startSession);
        startSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toTimer = new Intent(BeginOrModifyActivity.this, TimerActivity.class);
                toTimer.putExtra("SessionId", id);
                startActivity(toTimer);
                finish();
            }
        });
    }
}