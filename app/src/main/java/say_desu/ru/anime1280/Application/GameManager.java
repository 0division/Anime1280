package say_desu.ru.anime1280.Application;

import android.content.Context;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.List;
import say_desu.ru.anime1280.Domain.AnimeInfo;
import say_desu.ru.anime1280.Infrastructure.AnimeRepository;

public class GameManager
{
    private AnimeRepository animeRepository;
    private List<Integer> titleList;

    public GameManager(Context context){
        animeRepository = new AnimeRepository(context);
        titleList = animeRepository.getTitleList();
    }

    public GameManager(Context context, String titleString){
        animeRepository = new AnimeRepository(context);
        titleList = new ArrayList<Integer>();
        String[] titleArray = titleString.split(",");
        for(int i = 0; i<titleArray.length; i++){
            titleList.add(Integer.parseInt(titleArray[i]));
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

        String[] variants = animeRepository.getVariants(randIds);
        String[] variants_ru = animeRepository.getVariants_ru(randIds);
        byte[] imgByte = animeRepository.getImageByte(randIds[correctAnsIndex]);
        String color = animeRepository.getImageColor(randIds[correctAnsIndex]);
        int imgTextColor = 0;
        if(Objects.equals(color, "white")){
            imgTextColor=1;
        }else{
            imgTextColor=0;
        }

        return new AnimeInfo(variants, variants_ru,correctAnsIndex,imgByte,imgTextColor);
    }

    public int getTitlesCount(){
        return titleList.size();
    }

    public List<Integer> getTitleList() {
        return titleList;
    }
}
