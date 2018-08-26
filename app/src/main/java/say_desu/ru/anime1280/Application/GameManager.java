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
    private int[] randIds = {-1,-2,-3,-4};

    //genereates new title list
    public GameManager(Context context){
        animeRepository = new AnimeRepository(context);
        titleList = animeRepository.getTitleList();
    }

    //loads title list from arguments
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
        int correctAnsIndex = RandomNum(0,3); //button id where correct answer will be displayed
        randIds[correctAnsIndex] = titleList.get(count); //puts the correct ans id in the variant arr
        for(int i = 0; i<4; i++){ //fills the rest of the variant ids arr
            if(i==correctAnsIndex) continue;
            int rawRandom = RandomNum(0,titleList.size()-1);
            while (rawRandom == randIds[0] || rawRandom == randIds[1] || rawRandom == randIds[2] || rawRandom == randIds[3]){
                rawRandom = RandomNum(0,titleList.size()-1);
            }
            randIds[i] = rawRandom;
        }

        String[] variants = animeRepository.getVariants(randIds); //gets strings of the variants via ids
        String[] variants_ru = animeRepository.getVariants_ru(randIds);
        byte[] imgByte = animeRepository.getImageByte(randIds[correctAnsIndex]);
        String color = animeRepository.getImageColor(randIds[correctAnsIndex]);
        AnimeInfo.TextColor imgTextColor;
        if(Objects.equals(color, "white")){
            imgTextColor=AnimeInfo.TextColor.TEXTCOLOR_WHITE;
        }else{
            imgTextColor=AnimeInfo.TextColor.TEXTCOLOR_BLACK;
        }

        return new AnimeInfo(variants, variants_ru,correctAnsIndex,imgByte,imgTextColor);
    }

    public AnimeInfo getRandomAnimes(int count, int[] randIds, int correctAnsIndex){
        this.randIds = randIds;
        String[] variants = animeRepository.getVariants(randIds); //gets strings of the variants via ids
        String[] variants_ru = animeRepository.getVariants_ru(randIds);
        byte[] imgByte = animeRepository.getImageByte(randIds[correctAnsIndex]);
        String color = animeRepository.getImageColor(randIds[correctAnsIndex]);
        AnimeInfo.TextColor imgTextColor;
        if(Objects.equals(color, "white")){
            imgTextColor=AnimeInfo.TextColor.TEXTCOLOR_WHITE;
        }else{
            imgTextColor=AnimeInfo.TextColor.TEXTCOLOR_BLACK;
        }

        return new AnimeInfo(variants, variants_ru,correctAnsIndex,imgByte,imgTextColor);
    }

    public int getTitlesCount(){
        return titleList.size();
    }

    public List<Integer> getTitleList() {
        return titleList;
    }

    public int[] getRandIds() {
        return randIds;
    }
}
