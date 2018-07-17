package say_desu.ru.anime1280.Domain;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Objects;

import say_desu.ru.anime1280.Infrastructure.DBHelper;

public class AnimeInfo {
    private int correctBtnId;
    private String[] variants;
    private Bitmap image;
    private int imageTextColor;
    public final int TEXTCOLOR_BLACK = 0;
    public final int TEXTCOLOR_WHITE = 1;

    public AnimeInfo(String[] variants, int correctIndex, byte[] imgByte, int color){
        this.correctBtnId = correctIndex + 1;
        this.variants = new String[variants.length];
        System.arraycopy(variants,0,this.variants,0,variants.length);
        image = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        imageTextColor = color;
    }

    public int getCorrectBtnId() {
        return correctBtnId;
    }

    public String[] getVariants() {
        return variants;
    }

    public Bitmap getImage() {
        return image;
    }

    public int getImageTextColor() {
        return imageTextColor;
    }

}
