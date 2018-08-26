package say_desu.ru.anime1280.Domain;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class AnimeInfo {
    private int correctBtnId;

    private String[] variants;
    private String[] variants_ru;
    private Bitmap image;
    private TextColor imageTextColor;

    public enum TextColor{
        TEXTCOLOR_BLACK,
        TEXTCOLOR_WHITE
    }

    public AnimeInfo(String[] variants, String[] variants_ru, int correctIndex, byte[] imgByte, TextColor color){
        this.correctBtnId = correctIndex + 1;
        this.variants = new String[variants.length];
        System.arraycopy(variants,0,this.variants,0,variants.length);
        this.variants_ru = new String[variants_ru.length];
        System.arraycopy(variants_ru,0,this.variants_ru,0,variants_ru.length);
        image = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        imageTextColor = color;
    }

    public int getCorrectBtnId() {
        return correctBtnId;
    }

    public String[] getVariants() {
        return variants;
    }

    public String[] getVariants_ru() { return variants_ru; }

    public Bitmap getImage() {
        return image;
    }

    public TextColor getImageTextColor() {
        return imageTextColor;
    }

}
