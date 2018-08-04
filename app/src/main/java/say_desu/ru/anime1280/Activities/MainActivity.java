package say_desu.ru.anime1280.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import say_desu.ru.anime1280.Dialogs.StartDialog;
import say_desu.ru.anime1280.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnPlay;
    Button btnScore;
    Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        btnPlay = (Button) findViewById(R.id.buttonPlay);
        btnScore = (Button) findViewById(R.id.buttonScore);
        btnExit = (Button) findViewById(R.id.buttonExit);

        btnPlay.setOnClickListener(this);
        btnScore.setOnClickListener(this);
        btnExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonPlay:
                StartDialog startDialog = new StartDialog(this);
                startDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                startDialog.setCancelable(true);
                startDialog.show();
                break;
            case R.id.buttonScore:
                SharedPreferences sPref;
                sPref = getSharedPreferences("Scores", MODE_PRIVATE);
                String highScore = "Your best is " + Integer.toString(sPref.getInt("HighScore",0));
                Toast.makeText(this,highScore, Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonExit:
                finish();
                System.exit(0);
                break;
        }
    }

}