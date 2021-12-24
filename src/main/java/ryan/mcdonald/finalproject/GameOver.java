package ryan.mcdonald.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import android.os.Bundle;

public class GameOver extends AppCompatActivity {
    int score;
    TextView textScore;
    Button btn;
    EditText edt;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Intent mIntent = getIntent();
        score = mIntent.getIntExtra("score", 0);
        score = score * 2;

        btn = findViewById(R.id.btnHighScore);
        edt = findViewById(R.id.editTextName);
        textScore = findViewById(R.id.tvScoreResult);
        textScore.setText("" + score);

        int dbScore;
        db = new DatabaseHandler(this);
        List<HighScore> top5HiScores = db.getTopFiveScores();
        for (HighScore hs : top5HiScores) {
            dbScore = hs.getScore();
            if(score > dbScore){
                btn.setVisibility(View.VISIBLE);
                edt.setVisibility((View.VISIBLE));
            }
        }

    }

    public void doScoreBoard(View view) {
        Intent myIntent = new Intent(GameOver.this, ScoreBoard.class);
        startActivity(myIntent);
    }

    public void doPlayAgain(View view) {
        int dbCheck = 1;
        Intent myIntent = new Intent(GameOver.this, MainActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myIntent.putExtra("dbCheck", dbCheck);
        startActivity(myIntent);
    }

    public void HighScore(View view) {
        String Name = edt.getText().toString();

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        db.addHiScore(new HighScore(formattedDate, Name, score));

    }
}