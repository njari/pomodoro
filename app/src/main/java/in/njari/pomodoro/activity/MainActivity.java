package in.njari.pomodoro.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import in.njari.pomodoro.entity.Session;

public class MainActivity extends AppCompatActivity {
    private String focus = "";
    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, GoalActivity.class);
         startActivityForResult(intent, Activity.RESULT_OK);

         if (focus != null || !focus.isEmpty()) {
             intent = new Intent(this, DurationActivity.class);
             startActivity(intent);
         }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK && resultCode == RESULT_OK && data != null) {
            focus = data.getStringExtra("focus");
        }
    }
}