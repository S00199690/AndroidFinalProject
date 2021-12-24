package ryan.mcdonald.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;

public class GameScreen extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    boolean waitTimer = true;
    int counter = 0, answer, score;
    String Answer, selected = "";
    private Sensor mSensor;

    float [] history = new float[2];
    String [] direction = {"NONE","NONE"};
    TextView text1, TextAnswer, textConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        Intent mIntent = getIntent();
        Answer = mIntent.getStringExtra("answer");
        score = mIntent.getIntExtra("score", 0);
        TextAnswer = findViewById(R.id.textAnswer);
        textConfirm = findViewById(R.id.tvDirection);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor =mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this,mSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float xChange = history[0] - event.values[0];
        float yChange = history[1] - event.values[1];

        history[0] = event.values[0];
        history[1] = event.values[1];

        waitTimer = false;
        if (xChange > 0.25){
            direction[0] = "LEFT";
            textConfirm.setText(direction[0]);
            answer = 1;
        }
        else if (xChange < -0.25){
            direction[0] = "RIGHT";
            textConfirm.setText(direction[0]);
            answer = 4;

        }
        if (yChange > 0.25){
            direction[1] = "DOWN";
            textConfirm.setText(direction[1]);
            answer = 2;
        }
        else if (yChange < -0.25){
            direction[1] = "UP";
            textConfirm.setText(direction[1]);
            answer = 3;
        }

    }
    protected void onResume() {
        super.onResume();
        // turn on the sensor
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_GAME);
    }
    /*
     * App running but not on screen - in the background
     */
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int
            accuracy) {
        // not using
    }

    public void SetAnswer(){
        selected = selected + answer;
        TextAnswer.setText(selected);
    }

    public void doConfirm(View view) {
        SetAnswer();
    }

    public void doPlay(View view) {
        int num1,num2;
        num1 = Integer.parseInt(selected);
        num2 = Integer.parseInt(Answer);
        if(num1 == num2){
            score++;
            Intent myIntent = new Intent(GameScreen.this, MainActivity.class);
            myIntent.putExtra("score", score);
            startActivity(myIntent);
        }
        else {
            Intent myIntent = new Intent(GameScreen.this, GameOver.class);
            myIntent.putExtra("score", score);
            startActivity(myIntent);
        }
    }

}
