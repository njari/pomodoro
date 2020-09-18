package in.njari.pomodoro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import in.njari.pomodoro.db.PomodoroDatabase;
import in.njari.pomodoro.entity.Session;

public class DurationActivity extends AppCompatActivity {
    TextView hrsQuestion;
    EditText hrs;
    Button next;
    TextView message;
    private static final String TAG = "DurationActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hrsQuestion = (TextView) findViewById(R.id.focusQuestion);
        hrsQuestion.setText("For how long? (in hrs)");
        message = (TextView) findViewById(R.id.message);
        message.setText("Each Pomodoro cycle is for 30 mins : 25 minutes of work + 5 minutes of rest. " +
                "The recommended amount of time is 2 hrs i.e. 4 such cycles.");

        next = (Button) findViewById(R.id.next);
        next.setEnabled(false);

        hrs = (EditText) findViewById(R.id.focus);
        hrs.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_CLASS_NUMBER);

        final String focus = getIntent().getStringExtra("focus");
        Log.i(TAG, " user's goal is to : " + focus);

        hrs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                next.setEnabled(true);

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PomodoroDatabase db = PomodoroDatabase.getInstance(getApplicationContext());

                final Session session = new Session(0 ,focus, Integer.parseInt(hrs.getText().toString()));
               long  id = db.SessionDAO().create(session);
                Log.i(TAG,"Returning with the create method with id : " + id);
                Toast toast = Toast.makeText(DurationActivity.this, "You've created an entity!", Toast.LENGTH_SHORT);
                toast.show();
                Intent toStartPage = new Intent(DurationActivity.this, BeginOrModifyActivity.class);
                Log.i(TAG, "Session id is : " );
                toStartPage.putExtra("SessionId", id);

                startActivity(toStartPage);
            }
        });

    }
}
