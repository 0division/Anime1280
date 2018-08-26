package say_desu.ru.anime1280.Infrastructure;

import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

import say_desu.ru.anime1280.Domain.AnimeInfo;

/**
 * Class to help get data from the database
 */
public class AnimeRepository {
    DBHelper aniDB;
    List<Integer> titleList;

    public AnimeRepository(Context context){
        aniDB = new DBHelper(context);
        aniDB.openDataBase();
        Cursor crs = aniDB.getReadableDatabase().rawQuery("SELECT id FROM titles ORDER BY RANDOM()",null);
        titleList = new ArrayList<Integer>();
        while (crs.moveToNext()){
            titleList.add(crs.getInt(crs.getColumnIndex("id"))-1);
        }
    }

    /**
     * Gets a string array of anime names from the DB to place them on answer buttons
     * @param randIds int arr of size 4 with title ids
     * @return variants string arr of size 4
     */
    public String[] getVariants(int[] randIds){
        Cursor crs;
        String[] variants = new String[4];
        for(int i = 0; i< 4; i++){
            String[] args = new String[1];
            args[0] = Integer.toString(randIds[i]+1);
            crs = aniDB.getReadableDatabase().rawQuery("SELECT name FROM titles WHERE id = ?",args);
            if(crs.moveToFirst()) {
                variants[i] = crs.getString(crs.getColumnIndex("name"));
            }
            crs.close();
        }
        return variants;
    }

    /**
     * Same as getVariants but returns strings in russian
     * @see AnimeRepository#getVariants(int[])
     */
    public String[] getVariants_ru(int[] randIds){
        Cursor crs;
        String[] variants_ru = new String[4];
        for(int i = 0; i< 4; i++){
            String[] args = new String[1];
            args[0] = Integer.toString(randIds[i]+1);
            crs = aniDB.getReadableDatabase().rawQuery("SELECT name_ru FROM titles WHERE id = ?",args);
            if(crs.moveToFirst()) {
                variants_ru[i] = crs.getString(crs.getColumnIndex("name_ru"));
            }
            crs.close();
        }
        return variants_ru;
    }

    /**
     * Gets image byte array from the DB
     * @param correctAns id of title that was selected as a correct answer
     * @return imgByte
     */
    public byte[] getImageByte(int correctAns){
        Cursor crs;
        String[] args = new String[1];
        args[0] = Integer.toString(correctAns+1);
        crs = aniDB.getReadableDatabase().rawQuery("SELECT screenshot FROM images WHERE title_id = ?", args);
        crs.moveToFirst();
        byte[] imgByte = crs.getBlob(crs.getColumnIndex("screenshot"));
        crs.close();
        return imgByte;
    }

    /**
     * Gets color of text that should be written on the top of the image
     * @param correctAns id of title that was selected as a correct answer
     * @return color
     */
    public String getImageColor(int correctAns){
        Cursor crs;
        String[] args = new String[1];
        args[0] = Integer.toString(correctAns+1);
        crs = aniDB.getReadableDatabase().rawQuery("SELECT color FROM images WHERE title_id = ?", args);
        crs.moveToFirst();
        String color = crs.getString(crs.getColumnIndex("color"));
        crs.close();
        return color;
    }

    /**
     * Returns a full list of title ids
     * @return titleList
     */
    public List<Integer> getTitleList() {
        return titleList;
    }
}
