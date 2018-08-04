package say_desu.ru.anime1280.Infrastructure;

import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

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

    public List<Integer> getTitleList() {
        return titleList;
    }
}
