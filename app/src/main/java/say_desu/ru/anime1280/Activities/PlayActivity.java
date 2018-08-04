package say_desu.ru.anime1280.Activities;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import say_desu.ru.anime1280.Application.GameManager;
import say_desu.ru.anime1280.Domain.AnimeInfo;
import say_desu.ru.anime1280.R;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn1,btn2,btn3,btn4;
    ImageButton btnBack;
    TextView scoreView, lifeView;
    ImageView imgView;
    int score;
    int life;
    GameManager gameManager;
    int count = 0;
    AnimeInfo anims;


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
        imgView = (ImageView) findViewById(R.id.imageView);

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

        gameManager = new GameManager(this);
        Next(count);
    }

    void Next(int count){
        if(count<gameManager.getTitlesCount()) {
            anims = gameManager.getRandomAnimes(count);
            btn1.setEnabled(true);
            btn2.setEnabled(true);
            btn3.setEnabled(true);
            btn4.setEnabled(true);
            btn1.setText(anims.getVariants()[0]);
            btn2.setText(anims.getVariants()[1]);
            btn3.setText(anims.getVariants()[2]);
            btn4.setText(anims.getVariants()[3]);
            imgView.setImageBitmap(anims.getImage());
            if(anims.getImageTextColor()==anims.TEXTCOLOR_BLACK){
                scoreView.setTextColor(Color.BLACK);
                lifeView.setTextColor(Color.BLACK);
                btnBack.setBackground(getResources().getDrawable(R.drawable.ic_arrow_black_32px));
            }else if(anims.getImageTextColor()==anims.TEXTCOLOR_WHITE){
                scoreView.setTextColor(Color.WHITE);
                lifeView.setTextColor(Color.WHITE);
                btnBack.setBackground(getResources().getDrawable(R.drawable.ic_arrow_white_32px));
            }
        }
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
                if(anims.getCorrectBtnId()==1) {
                    score++;
                    Next(++count);
                }else{
                    life--;
                    btn1.setEnabled(false);
                }
                break;
            case R.id.button2:
                if(anims.getCorrectBtnId()==2) {
                    score++;
                    Next(++count);
                }else{
                    life--;
                    btn2.setEnabled(false);
                }
                break;
            case R.id.button3:
                if(anims.getCorrectBtnId()==3) {
                    score++;
                    Next(++count);
                }else{
                    life--;
                    btn3.setEnabled(false);
                }
                break;
            case R.id.button4:
                if(anims.getCorrectBtnId()==4) {
                    score++;
                    Next(++count);
                }else{
                    life--;
                    btn4.setEnabled(false);
                }
                break;
        }
        scoreView.setText(getString(R.string.score)+score);
        lifeView.setText(getString(R.string.life)+life);
        if(life<=0){
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(this, R.style.Transparent);
            View lossView = getLayoutInflater().inflate(R.layout.dialog_loss, null);

            ImageView lossImageView = (ImageView) lossView.findViewById(R.id.loss_img);
            lossView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            mBuilder.setView(lossView);
            AlertDialog lossDialog = mBuilder.create();
            lossDialog.show();
        }
    }
}
