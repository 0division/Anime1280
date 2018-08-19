package say_desu.ru.anime1280.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.view.Window;
import android.widget.TextView;
import say_desu.ru.anime1280.R;

public class HisghScoreDialog extends Dialog{

    public Activity activity;

    public HisghScoreDialog(Activity activity) {
        super(activity);
        this.activity = activity;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_highscore);

        SharedPreferences sPref;
        sPref = activity.getSharedPreferences("SavedData", activity.MODE_PRIVATE);
        int highScore = sPref.getInt("HighScore",0);
        TextView highScoreTextView = (TextView) findViewById(R.id.highscore_textview);
        highScoreTextView.setText(Integer.toString(highScore));
    }
}
