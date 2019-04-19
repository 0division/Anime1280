package say_desu.ru.anime1280.Activities;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Arrays;

import say_desu.ru.anime1280.Application.GameManager;
import say_desu.ru.anime1280.Domain.AnimeInfo;
import say_desu.ru.anime1280.R;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn1,btn2,btn3,btn4,btnLang;
    ImageButton btnBack;
    TextView scoreView, lifeView;
    ImageView imgView;
    int score;
    int life;
    GameManager gameManager;
    int count = 0;
    AnimeInfo anims;
    //variables for loading progress
    boolean isContinuable;
    int[] randIds;
    int CorrectAnsIndex;
    boolean isContinued=false;
    int screenWidthPx, screenHeightPx;

    protected enum language{Jap, Ru}
    private language lang = language.Jap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_play);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidthPx = size.x;
        screenHeightPx = size.y;

        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        btnLang = (Button) findViewById(R.id.langSwitch);
        btnBack = (ImageButton) findViewById(R.id.buttonBack);
        scoreView = (TextView) findViewById(R.id.scoreLabel);
        lifeView = (TextView) findViewById(R.id.lifeLabel);
        imgView = (ImageView) findViewById(R.id.imageView);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btnLang.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        SharedPreferences sPref;
        sPref = getSharedPreferences("SavedData", MODE_PRIVATE);
        isContinuable = sPref.getBoolean("Continuable",false);

        if(getIntent().getIntExtra("mode",-1)==R.id.buttonContinue && isContinuable){
            score = sPref.getInt("CurrentScore",0);
            life = sPref.getInt("CurrentLife",3);
            count = sPref.getInt("Count",0);
            gameManager = new GameManager(this,sPref.getString("TitleList",null));

            String randIds_merged = sPref.getString("randIds",null);
            String[] randIds_s = randIds_merged.split(",");
            randIds = new int[4];
            for(int i = 0; i<4; i++){
                randIds[i] = Integer.parseInt(randIds_s[i]);
            }
            CorrectAnsIndex = sPref.getInt("CorrectAnsId",-1);
            isContinued = true;
        }else if(getIntent().getIntExtra("mode",-1)==R.id.buttonNew){
            score = 0;
            life = 3;
            gameManager = new GameManager(this);
        }

        scoreView.setText(getString(R.string.score)+score);
        lifeView.setText(getString(R.string.life)+life);

        Next(count);
        isContinuable = true;
    }

    void fixButtonsSize(){
        final int maxlength = 40;
        final float smallFont = 15;
        final float normalFont = 20;
        if(btn1.getText().length() > maxlength){
            btn1.setTextSize(smallFont);
        }else{
            btn1.setTextSize(normalFont);
        }
        if(btn2.getText().length() > maxlength){
            btn2.setTextSize(smallFont);
        }else{
            btn2.setTextSize(normalFont);
        }
        if(btn3.getText().length() > maxlength){
            btn3.setTextSize(smallFont);
        }else{
            btn3.setTextSize(normalFont);
        }
        if(btn4.getText().length() > maxlength){
            btn4.setTextSize(smallFont);
        }else{
            btn4.setTextSize(normalFont);
        }
    }

    void Next(int count){
        if(count<gameManager.getTitlesCount()) {
            if(isContinued){
                anims = gameManager.getRandomAnimes(randIds,CorrectAnsIndex);
                isContinued = false;
            }else {
                anims = gameManager.getRandomAnimes(count);
            }
            btn1.setEnabled(true);
            btn2.setEnabled(true);
            btn3.setEnabled(true);
            btn4.setEnabled(true);

            if(lang==language.Jap) {
                btn1.setText(anims.getVariants()[0]);
                btn2.setText(anims.getVariants()[1]);
                btn3.setText(anims.getVariants()[2]);
                btn4.setText(anims.getVariants()[3]);
            }else{
                btn1.setText(anims.getVariants_ru()[0]);
                btn2.setText(anims.getVariants_ru()[1]);
                btn3.setText(anims.getVariants_ru()[2]);
                btn4.setText(anims.getVariants_ru()[3]);
            }
            fixButtonsSize();

            Glide.with(this).load(Uri.parse(anims.getImagePath())).into(imgView);

            if(anims.getImageTextColor()==AnimeInfo.TextColor.TEXTCOLOR_BLACK){
                scoreView.setTextColor(Color.BLACK);
                lifeView.setTextColor(Color.BLACK);
                btnLang.setTextColor(Color.BLACK);
                btnBack.setBackground(getResources().getDrawable(R.drawable.ic_arrow_black_32px));
            }else if(anims.getImageTextColor()==AnimeInfo.TextColor.TEXTCOLOR_WHITE){
                scoreView.setTextColor(Color.WHITE);
                lifeView.setTextColor(Color.WHITE);
                btnLang.setTextColor(Color.WHITE);
                btnBack.setBackground(getResources().getDrawable(R.drawable.ic_arrow_white_32px));
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonBack:
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
            case R.id.langSwitch:
                if(lang==language.Jap) {
                    lang = language.Ru;
                    btn1.setText(anims.getVariants_ru()[0]);
                    btn2.setText(anims.getVariants_ru()[1]);
                    btn3.setText(anims.getVariants_ru()[2]);
                    btn4.setText(anims.getVariants_ru()[3]);
                    btnLang.setText("Ru⇄Jap");
                }else{
                    lang = language.Jap;
                    btn1.setText(anims.getVariants()[0]);
                    btn2.setText(anims.getVariants()[1]);
                    btn3.setText(anims.getVariants()[2]);
                    btn4.setText(anims.getVariants()[3]);
                    btnLang.setText("Jap⇄Ru");
                }
                fixButtonsSize();
                break;
        }
        scoreView.setText(getString(R.string.score)+score);
        lifeView.setText(getString(R.string.life)+life);

        if(life<=0){
            isContinuable = false;
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(this,R.style.Transparent);
            View lossView = getLayoutInflater().inflate(R.layout.dialog_loss, null);
            lossView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            mBuilder.setView(lossView);
            AlertDialog lossDialog = mBuilder.create();
            WindowManager.LayoutParams layoutParams = lossDialog.getWindow().getAttributes();
            lossDialog.getWindow().setAttributes(layoutParams);
            lossDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            lossDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lossDialog.setCancelable(false);
            lossDialog.show();

        }

        if(score == gameManager.getTitlesCount()){
            isContinuable = false;
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(this, R.style.Transparent);
            View winView = getLayoutInflater().inflate(R.layout.dialog_victory, null);
            winView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            mBuilder.setView(winView);
            AlertDialog winDialog = mBuilder.create();
            WindowManager.LayoutParams layoutParams = winDialog.getWindow().getAttributes();
            winDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            winDialog.getWindow().setAttributes(layoutParams);
            winDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            winDialog.setCancelable(false);
            winDialog.show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sPref;
        sPref = getSharedPreferences("SavedData", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt("CurrentScore",score);
        ed.putInt("CurrentLife",life);
        ed.putInt("Count",count);
        ed.putString("TitleList", TextUtils.join(",", gameManager.getTitleList()));
        ed.putBoolean("Continuable",isContinuable);
        if(score>sPref.getInt("HighScore",0)){
            ed.putInt("HighScore",score);
        }
        ed.putString("randIds",Arrays.toString(gameManager.getRandIds()).replaceAll("\\[|\\]|\\s", ""));
        ed.putInt("CorrectAnsId",anims.getCorrectBtnId()-1);
        ed.commit();
    }
}
