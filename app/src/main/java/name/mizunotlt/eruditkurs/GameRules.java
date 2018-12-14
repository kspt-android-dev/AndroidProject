package name.mizunotlt.eruditkurs;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class GameRules {
    private List<Letter> listLetter = new ArrayList<>();
    private int score = 0;
    private boolean flagx2Word = false;
    private boolean flagx3Word = false;
    private void initListLetter(){
        listLetter.add(0, new Letter('А', 1, 10));
        listLetter.add(1, new Letter('Б', 3, 3));
        listLetter.add(2, new Letter('В', 2, 5));
        listLetter.add(3, new Letter('Г', 3, 3));
        listLetter.add(4, new Letter('Д', 2, 5));
        listLetter.add(5, new Letter('Е', 1, 9));
        listLetter.add(6, new Letter('Ж', 5, 5));
        listLetter.add(7, new Letter('З', 5, 2));
        listLetter.add(8, new Letter('И', 1, 8));
        listLetter.add(9, new Letter('Й', 2, 4));
        listLetter.add(10, new Letter('К', 2, 6));
        listLetter.add(11, new Letter('Л', 2, 4));
        listLetter.add(12, new Letter('М', 2, 5));
        listLetter.add(13, new Letter('Н', 1, 8));
        listLetter.add(14, new Letter('О', 1, 10));
        listLetter.add(15, new Letter('П', 2, 6));
        listLetter.add(16, new Letter('Р', 2, 6));
        listLetter.add(17, new Letter('С', 2, 6));
        listLetter.add(18, new Letter('Т', 2, 5));
        listLetter.add(19, new Letter('У', 3, 3));
        listLetter.add(20, new Letter('Ф', 10, 1));
        listLetter.add(21, new Letter('Х', 5, 2));
        listLetter.add(22, new Letter('Ц', 10, 1));
        listLetter.add(23, new Letter('Ч', 5, 2));
        listLetter.add(24, new Letter('Ш', 10, 1));
        listLetter.add(25, new Letter('Щ', 10, 1));
        listLetter.add(26, new Letter('Ь', 5, 2));
        listLetter.add(27, new Letter('Ы', 5, 2));
        listLetter.add(28, new Letter('Ъ', 10, 1));
        listLetter.add(29, new Letter('Э', 10, 1));
        listLetter.add(30, new Letter('Ю', 10, 1));
        listLetter.add(31, new Letter('Я', 3, 3));
        listLetter.add(32, new Letter('*', 0, 3));
    }
    @SuppressLint("StaticFieldLeak")
    private class MyAsyncTask extends AsyncTask<String, Void, Boolean> {
        boolean result;
        @Override
        protected Boolean doInBackground(String... word) {
            final CheckWordImp checker = new CheckWordImp(word[0]);
                try {
                    result = checker.checkCorrectWord(checker.connectYandexDicApi(checker.buildUrl()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
        }
        @Override
        protected void onPostExecute(Boolean result){
            super.onPostExecute(result);
        }
    }
    GameRules(){

        initListLetter();
    }
    private Letter findLetter(char letter){
        switch (letter){
            case 'А': {
                return listLetter.get(0);
            }
            case 'Б': {
                return listLetter.get(1);
            }
            case 'В': {
                return listLetter.get(2);
            }
            case 'Г': {
                return listLetter.get(3);
            }
            case 'Д': {
                return listLetter.get(4);
            }
            case 'Е': {
                return listLetter.get(5);
            }
            case 'Ж': {
                return listLetter.get(6);
            }
            case 'З': {
                return listLetter.get(7);
            }
            case 'И': {
                return listLetter.get(8);
            }
            case 'Й': {
                return listLetter.get(9);
            }
            case 'К': {
                return listLetter.get(10);
            }
            case 'Л': {
                return listLetter.get(11);
            }
            case 'М': {
                return listLetter.get(12);
            }
            case 'Н': {
                return listLetter.get(13);
            }
            case 'О': {
                return listLetter.get(14);
            }
            case 'П': {
                return listLetter.get(15);
            }
            case 'Р': {
                return listLetter.get(16);
            }
            case 'С': {
                return listLetter.get(17);
            }
            case 'Т': {
                return listLetter.get(18);
            }
            case 'У': {
                return listLetter.get(19);
            }
            case 'Ф': {
                return listLetter.get(20);
            }
            case 'Х': {
                return listLetter.get(21);
            }
            case 'Ц': {
                return listLetter.get(22);
            }
            case 'Ч': {
                return listLetter.get(23);
            }
            case 'Ш': {
                return listLetter.get(24);
            }
            case 'Щ': {
                return listLetter.get(25);
            }
            case 'Ь': {
                return listLetter.get(26);
            }
            case 'Ы': {
                return listLetter.get(27);
            }
            case 'Ъ': {
                return listLetter.get(28);
            }
            case 'Э': {
                return listLetter.get(29);
            }
            case 'Ю': {
                return listLetter.get(30);
            }
            case 'Я': {
                return listLetter.get(31);
            }
            default:{
                return listLetter.get(32);
            }
        }
    }
    private String makeWord(Player player, ArrayList<Cell> workList){
        StringBuilder result = new StringBuilder();
        int first = player.getFirstTap();
        int startMakeWord = 0;
        int index = first;
        if ((workList.get(first + 15).getIsLetter())|(workList.get(first - 15).getIsLetter())){
            if (!(workList.get(first + 1).getIsLetter()) & (!workList.get(first - 1 ).getIsLetter())){
                while (index > 0){
                    if ((!workList.get(index).getIsLetter()) || (index - 15 < 0)){
                        startMakeWord = index;
                        break;
                    }
                    index -=15;
                }
                while (startMakeWord < 225){
                    startMakeWord += 15;
                    if (workList.get(startMakeWord).getIsLetter()){
                        result.append(workList.get(startMakeWord).getLetter());
                        switch (workList.get(startMakeWord).typeCell()){
                            case 3:{
                               if(!workList.get(startMakeWord).getUseErly()){
                                   score += 2 * findLetter(workList.get(startMakeWord).getLetter()).getCountScore();
                                   workList.get(startMakeWord).changeUseErly();
                               }
                               else{
                                   score += findLetter(workList.get(startMakeWord).getLetter()).getCountScore();
                               }
                               break;
                            }
                            case 4:{
                                if(!workList.get(startMakeWord).getUseErly()){
                                    score += 3 * findLetter(workList.get(startMakeWord).getLetter()).getCountScore();
                                    workList.get(startMakeWord).changeUseErly();
                                }
                                else {
                                    score += findLetter(workList.get(startMakeWord).getLetter()).getCountScore();
                                }
                                break;
                            }
                            case 5:{
                                if(!workList.get(startMakeWord).getUseErly()){
                                    score += findLetter(workList.get(startMakeWord).getLetter()).getCountScore();
                                    workList.get(startMakeWord).changeUseErly();
                                    flagx2Word = true;
                                }
                                else {
                                    score += findLetter(workList.get(startMakeWord).getLetter()).getCountScore();
                                }
                                break;
                            }
                            case 6:{
                                if(!workList.get(startMakeWord).getUseErly()){
                                    score += findLetter(workList.get(startMakeWord).getLetter()).getCountScore();
                                    workList.get(startMakeWord).changeUseErly();
                                    flagx3Word =true;
                                }
                                else {
                                    score += findLetter(workList.get(startMakeWord).getLetter()).getCountScore();
                                }
                                break;
                            }
                            default:{
                                score += findLetter(workList.get(startMakeWord).getLetter()).getCountScore();
                            }
                        }
                    }
                    else {
                        break;
                    }
                }
                if (flagx3Word)
                    score *= 3;
                if (flagx2Word)
                    score *=2;
            }
            else
                return result.toString();
        }
        if ((workList.get(first - 1).getIsLetter()) | (workList.get(first + 1).getIsLetter())){
            if (!(workList.get(first - 15).getIsLetter()) & (!workList.get(first + 15 ).getIsLetter())){
                while (index / 10 > 0){
                    if (!workList.get(index).getIsLetter()){
                        startMakeWord = index + 1;
                        break;
                    }
                    index -=1;
                }
                while (startMakeWord / 10  > 0){

                    if (workList.get(startMakeWord).getIsLetter()){
                        result.append(workList.get(startMakeWord).getLetter());
                        switch (workList.get(startMakeWord).typeCell()){
                            case 3:{
                                if(!workList.get(startMakeWord).getUseErly()){
                                    score += 2 * findLetter(workList.get(startMakeWord).getLetter()).getCountScore();
                                    workList.get(startMakeWord).changeUseErly();
                                }
                                else{
                                    score += findLetter(workList.get(startMakeWord).getLetter()).getCountScore();
                                }
                                break;
                            }
                            case 4:{
                                if(!workList.get(startMakeWord).getUseErly()){
                                    score += 3 * findLetter(workList.get(startMakeWord).getLetter()).getCountScore();
                                    workList.get(startMakeWord).changeUseErly();
                                }
                                else {
                                    score += findLetter(workList.get(startMakeWord).getLetter()).getCountScore();
                                }
                                break;
                            }
                            case 5:{
                                if(!workList.get(startMakeWord).getUseErly()){
                                    score += findLetter(workList.get(startMakeWord).getLetter()).getCountScore();
                                    workList.get(startMakeWord).changeUseErly();
                                    flagx2Word = true;
                                }
                                else {
                                    score += findLetter(workList.get(startMakeWord).getLetter()).getCountScore();
                                }
                                break;
                            }
                            case 6:{
                                if(!workList.get(startMakeWord).getUseErly()){
                                    score += findLetter(workList.get(startMakeWord).getLetter()).getCountScore();
                                    workList.get(startMakeWord).changeUseErly();
                                    flagx3Word =true;
                                }
                                else {
                                    score += findLetter(workList.get(startMakeWord).getLetter()).getCountScore();
                                }
                                break;
                            }
                            default:{
                                score += findLetter(workList.get(startMakeWord).getLetter()).getCountScore();
                            }
                        }
                    }
                    else {
                        break;
                    }
                    startMakeWord += 1;
                }
                if (flagx3Word)
                    score *= 3;
                if (flagx2Word)
                    score *=2;
        }
        else
            return result.toString();
        }
        return result.toString();
    }
    private boolean test = false;
    private MyAsyncTask asTask;
    public String checkWord(Player player, ArrayList<Cell> workList) {
        String word = makeWord(player, workList);
        try {
            asTask = new MyAsyncTask();
            test = asTask.execute(word).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if (test){
            player.addWord(word);
            player.appScore(score);
            score = 0;
            flagx2Word = false;
            flagx3Word = false;
            return word;
        }
        else
            return "";
    }
    public void addLetters(Player play){
        List<Character> lettWordList = new ArrayList<>();
        Random rand = new Random();
        int countLet = 0;
        while (countLet < 7){
            int next =rand.nextInt(33);
            if (listLetter.get(next).getCount() != 0) {
                lettWordList.add(listLetter.get(next).getLetter());
                countLet++;
                listLetter.get(next).setCount(listLetter.get(next).getCount() - 1);
            }
        }
        play.setListLetter(lettWordList);
    }
    //Метод восстановления букв после хода
    public void resetLetter(Player play){
        Random rand = new Random();
        int index = 0;
        while (play.getCountLetter() < 7){
            int next =rand.nextInt(33);
            if (play.getListLetter().get(index) == ' '){
                if (listLetter.get(next).getCount() != 0) {
                    listLetter.get(next).setCount(listLetter.get(next).getCount() - 1);
                    play.addLetter(index, listLetter.get(next).getLetter());
                    index++;
                }
                else{
                    index = 0;
                }
            }
            else{
                index++;
            }
        }
    }
    //Метод замены и сброса букв
    public void recycleLetter(Player play, List<Integer> list){
        List<Character> listLet = play.getListLetter();
        Random rand = new Random();
        int index = 0;
        if (list.size() != 0){
            for (int i = 0; i < list.size(); i++){
                play.deletLetter(list.get(i));
            }
            while (play.getCountLetter() < 7){
                int next =rand.nextInt(33);
                if (listLetter.get(next).getCount() != 0) {
                    listLetter.get(next).setCount(listLetter.get(next).getCount() - 1);
                    play.addLetter(list.get(index),listLetter.get(next).getLetter());
                    index++;
                }
            }
        }
        else{
            for(Character let: listLet){
                switch (let){
                    case 'А': {
                        listLetter.get(0).setCount(listLetter.get(0).getCount() + 1);
                        break;
                    }
                    case 'Б': {
                        listLetter.get(1).setCount(listLetter.get(1).getCount() + 1);
                        break;
                    }
                    case 'В': {
                        listLetter.get(2).setCount(listLetter.get(2).getCount() + 1);
                        break;
                    }
                    case 'Г': {
                        listLetter.get(3).setCount(listLetter.get(3).getCount() + 1);
                        break;
                    }
                    case 'Д': {
                        listLetter.get(4).setCount(listLetter.get(4).getCount() + 1);
                        break;
                    }
                    case 'Е': {
                        listLetter.get(5).setCount(listLetter.get(5).getCount() + 1);
                        break;
                    }
                    case 'Ж': {
                        listLetter.get(6).setCount(listLetter.get(6).getCount() + 1);
                        break;
                    }
                    case 'З': {
                        listLetter.get(7).setCount(listLetter.get(7).getCount() + 1);
                        break;
                    }
                    case 'И': {
                        listLetter.get(8).setCount(listLetter.get(8).getCount() + 1);
                        break;
                    }
                    case 'Й': {
                        listLetter.get(9).setCount(listLetter.get(9).getCount() + 1);
                        break;
                    }
                    case 'К': {
                        listLetter.get(10).setCount(listLetter.get(10).getCount() + 1);
                        break;
                    }
                    case 'Л': {
                        listLetter.get(11).setCount(listLetter.get(11).getCount() + 1);
                        break;
                    }
                    case 'М': {
                        listLetter.get(12).setCount(listLetter.get(12).getCount() + 1);
                        break;
                    }
                    case 'Н': {
                        listLetter.get(13).setCount(listLetter.get(13).getCount() + 1);
                        break;
                    }
                    case 'О': {
                        listLetter.get(14).setCount(listLetter.get(14).getCount() + 1);
                        break;
                    }
                    case 'П': {
                        listLetter.get(15).setCount(listLetter.get(15).getCount() + 1);
                        break;
                    }
                    case 'Р': {
                        listLetter.get(16).setCount(listLetter.get(16).getCount() + 1);
                        break;
                    }
                    case 'С': {
                        listLetter.get(17).setCount(listLetter.get(17).getCount() + 1);
                        break;
                    }
                    case 'Т': {
                        listLetter.get(18).setCount(listLetter.get(18).getCount() + 1);
                        break;
                    }
                    case 'У': {
                        listLetter.get(19).setCount(listLetter.get(19).getCount() + 1);
                        break;
                    }
                    case 'Ф': {
                        listLetter.get(20).setCount(listLetter.get(20).getCount() + 1);
                        break;
                    }
                    case 'Х': {
                        listLetter.get(21).setCount(listLetter.get(21).getCount() + 1);
                        break;
                    }
                    case 'Ц': {
                        listLetter.get(22).setCount(listLetter.get(22).getCount() + 1);
                        break;
                    }
                    case 'Ч': {
                        listLetter.get(23).setCount(listLetter.get(23).getCount() + 1);
                        break;
                    }
                    case 'Ш': {
                        listLetter.get(24).setCount(listLetter.get(24).getCount() + 1);
                        break;
                    }
                    case 'Щ': {
                        listLetter.get(25).setCount(listLetter.get(25).getCount() + 1);
                        break;
                    }
                    case 'Ь': {
                        listLetter.get(26).setCount(listLetter.get(26).getCount() + 1);
                        break;
                    }
                    case 'Ы': {
                        listLetter.get(27).setCount(listLetter.get(27).getCount() + 1);
                        break;
                    }
                    case 'Ъ': {
                        listLetter.get(28).setCount(listLetter.get(28).getCount() + 1);
                        break;
                    }
                    case 'Э': {
                        listLetter.get(29).setCount(listLetter.get(29).getCount() + 1);
                        break;
                    }
                    case 'Ю': {
                        listLetter.get(30).setCount(listLetter.get(30).getCount() + 1);
                        break;
                    }
                    case 'Я': {
                        listLetter.get(31).setCount(listLetter.get(31).getCount() + 1);
                        break;
                    }
                    case '*': {
                        listLetter.get(32).setCount(listLetter.get(32).getCount() + 1);
                        break;
                    }
                }
            }
            play.clear();

            addLetters(play);
        }
    }
}
