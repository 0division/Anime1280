package say_desu.ru.anime1280;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn1,btn2,btn3,btn4;
    ImageButton btnBack;
    TextView scoreView, lifeView;
    int score;
    int life;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_play);

        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        btnBack = (ImageButton) findViewById(R.id.buttonBack);
        scoreView = (TextView) findViewById(R.id.scoreLabel);
        lifeView = (TextView) findViewById(R.id.lifeLabel);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        SharedPreferences sPref;
        sPref = getSharedPreferences("Scores", MODE_PRIVATE);
        score = sPref.getInt("CurrentScore",0);
        life = sPref.getInt("CurrentLife",3);

        scoreView.setText(getString(R.string.score)+score);
        lifeView.setText(getString(R.string.life)+life);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonBack:
                SharedPreferences sPref;
                sPref = getSharedPreferences("Scores", MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putInt("CurrentScore",score);
                ed.putInt("CurrentLife",life);
                if(score>sPref.getInt("HighScore",0)){
                    ed.putInt("HighScore",score);
                }
                ed.commit();
                finish();
                break;
            case R.id.button1:
                score++;
                break;
        }
        scoreView.setText(getString(R.string.score)+score);
        lifeView.setText(getString(R.string.life)+life);
    }
}
