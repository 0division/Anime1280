package say_desu.ru.anime1280.Domain;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import say_desu.ru.anime1280.Infrastructure.DBHelper;

public class AnimeInfo {
    private int correctBtnId;
    private String[] variants;
    private Bitmap image;

    public AnimeInfo(int[] variants_id, int correctIndex, DBHelper database){
        this.correctBtnId = correctIndex + 1;
        Cursor crs;
        variants = new String[4];
        for(int i = 0; i< 4; i++){
            String[] args = new String[1];
            args[0] = Integer.toString(variants_id[i]+1);
            crs = database.getReadableDatabase().rawQuery("SELECT name FROM titles WHERE id = ?",args);
            if(crs.moveToFirst()) {
                variants[i] = crs.getString(crs.getColumnIndex("name"));
            }
            crs.close();
        }
        String[] args = new String[1];
        args[0] = Integer.toString(variants_id[correctIndex]+1);
        crs = database.getReadableDatabase().rawQuery("SELECT screenshot FROM images WHERE title_id = ?", args);
        if(crs.moveToFirst()) {
            byte[] imgByte = crs.getBlob(crs.getColumnIndex("screenshot"));
            crs.close();
            image = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        }
        crs.close();
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
}
