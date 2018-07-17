package say_desu.ru.anime1280.Application;

import android.content.Context;
import android.database.Cursor;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.List;

import say_desu.ru.anime1280.Domain.AnimeInfo;
import say_desu.ru.anime1280.Infrastructure.DBHelper;

public class GameManager
{
    private DBHelper aniDB;
    private List<Integer> titleList;

    public GameManager(Context context){
        aniDB = new DBHelper(context);
        aniDB.openDataBase();
        Cursor crs = aniDB.getReadableDatabase().rawQuery("SELECT id FROM titles ORDER BY RANDOM()",null);
        titleList = new ArrayList<Integer>();
        while (crs.moveToNext()){
            titleList.add(crs.getInt(crs.getColumnIndex("id"))-1);
        }
    }

    private int RandomNum(int min, int max){
        int randomNum = ThreadLocalRandom.current().nextInt(min,max+1);
        return randomNum;
    }

    public AnimeInfo getRandomAnimes(int count){
        int[]randIds = {-1,-2,-3,-4};
        int correctAnsIndex = RandomNum(0,3);
        randIds[correctAnsIndex] = titleList.get(count);
        for(int i = 0; i<4; i++){
            if(i==correctAnsIndex) continue;
            int rawRandom = RandomNum(0,titleList.size()-1);
            while (rawRandom == randIds[0] || rawRandom == randIds[1] || rawRandom == randIds[2] || rawRandom == randIds[3]){
                rawRandom = RandomNum(0,titleList.size()-1);
            }
            randIds[i] = rawRandom;
        }
        return new AnimeInfo(randIds,correctAnsIndex,aniDB);
    }

    public int getTitlesCount(){
        return titleList.size();
    }
}
