package say_desu.ru.anime1280.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import say_desu.ru.anime1280.Activities.PlayActivity;
import say_desu.ru.anime1280.R;

public class StartDialog extends Dialog implements View.OnClickListener {

    public Activity activity;

    public StartDialog(Activity c){
        super(c);
        this.activity = c;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_start);

        Button btnNewGame = (Button) findViewById(R.id.buttonNew);
        Button btnContinue = (Button) findViewById(R.id.buttonContinue);

        btnNewGame.setOnClickListener(this);
        btnContinue.setOnClickListener(this);

        SharedPreferences sPref;
        sPref = activity.getSharedPreferences("SavedData", activity.MODE_PRIVATE);
        boolean isContinuable = sPref.getBoolean("Continuable",false);

        if(!isContinuable){
            btnContinue.setEnabled(false);
            btnContinue.setTextColor(Color.GRAY);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(activity, PlayActivity.class);
        intent.putExtra("mode",view.getId());
        activity.startActivity(intent);
        dismiss();
    }
}
