package name.mizunotlt.eruditkurs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Player {
    private Set<String> baseWord = new HashSet<>();
    private List<Character> listLetter;
    private int  score;
    private int  countLetter;
    private String name;
    private int firstTap = 0;

    public  List<Character> getListLetter(){
        return listLetter;
    }
    public Player(String name){
        this.name = name;
        this.score = 0;
        this.countLetter = 0;
    }
    public void setListLetter(List<Character> list){
        this.listLetter = list;
        this.countLetter = 7;
    }
    public String addWord(String word){
        if (baseWord.contains(word)){
            return "ERROR";
        }
        else{
            baseWord.add(word);
            return "ADD";
        }
    }

    public void setFirstTap(int num){
        if (firstTap == 0)
            this.firstTap = num;
    }
    public void clearFirstTap(){
        this.firstTap = 0;
    }
    public int getFirstTap(){
        return firstTap;
    }
    public void appScore(int count){
        score += count;
    }
    public  int getScore(){
        return score;
    }
    public  int getCountLetter(){
        return countLetter;
    }
    public void deletLetter(int index){
        listLetter.set(index,' ');
        countLetter--;
    }
    public void  addLetter(int index, char let){
        listLetter.set(index,let);
        countLetter++;
    }
    public void clear(){
        listLetter.clear();
        countLetter =0;
    }
    public String getName(){
        return name;
    }
}
