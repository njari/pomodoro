package in.njari.pomodoro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class BeginOrModifyActivity extends AppCompatActivity {
    Button startSession;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_or_modify);
        final int sessionId = getIntent().getIntExtra("sessionId", -1);
        if (sessionId == -1) {
            Intent intent = new Intent(BeginOrModifyActivity.this, MainActivity.class);
            startActivity(intent);
        }

        startSession = (Button) findViewById(R.id.startSession);
        startSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toTimer = new Intent(BeginOrModifyActivity.this, TimerActivity.class);
                startActivity(toTimer);
                finish();
            }
        });
    }
}