package ryan.mcdonald.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

public class ScoreBoard extends AppCompatActivity {
    TextView player1,player2,player3,player4,player5;
    TextView[] textViews = new TextView[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        player1 = findViewById(R.id.tvPlayer1);
        player2= findViewById(R.id.tvPlayer2);
        player3= findViewById(R.id.tvPlayer3);
        player4= findViewById(R.id.tvPlayer4);
        player5= findViewById(R.id.tvPlayer5);
        textViews[0] = player1;
        textViews[1] = player2;
        textViews[2] = player3;
        textViews[3] = player4;
        textViews[4] = player5;
        String log = "";
        int counter = 0;

        DatabaseHandler db = new DatabaseHandler(this);
        List<HighScore> top5HiScores = db.getTopFiveScores();

        for (HighScore hs : top5HiScores) {
            log = hs.getPlayer_name() +
                            "    " + hs.getScore();

            textViews[counter].setText(log);
            counter++;
        }
    }

    public void doPlayAgain(View view) {
        int dbCheck = 1;

        Intent myIntent = new Intent(ScoreBoard.this, MainActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myIntent.putExtra("dbCheck", dbCheck);
        startActivity(myIntent);

    }
}