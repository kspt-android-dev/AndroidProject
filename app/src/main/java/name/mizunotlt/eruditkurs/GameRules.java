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
    private final int COUNT_LETTER = 33;
    private final int COUNT_LETTER_PLAYER = 7;
    private final int cellsInRow = 15;
    private final int countCells = 225;

    private ArrayList<Integer> countFreeLetter = new ArrayList<>();

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
    public static class MyAsyncTask extends AsyncTask<String, Void, Boolean> {
        boolean result;
        @Override
        protected Boolean doInBackground(String... word) {
            final WordChecker checker = new WordChecker(word[0]);
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
        for (Letter templet: listLetter){
            if (templet.getLetter() == letter)
                return templet;
        }
        return new Letter(' ', 0, 0);
    }

    private void countScore(Cell cell){
        int incScore = findLetter(cell.getLetter()).getCountScore();
        switch (cell.getState()){
            case X2LETTER:{
                if(!cell.getUseErly()){
                    incScore = 2 * incScore;
                    cell.changeUseErly();
                }
                break;
            }
            case X3LETTER:{
                if(!cell.getUseErly()){
                    incScore += 3 * incScore;
                    cell.changeUseErly();
                }
                break;
            }
            case X2WORD:{
                if(!cell.getUseErly()){
                    cell.changeUseErly();
                    flagx2Word = true;
                }
                break;
            }
            case X3WORD:{
                if(!cell.getUseErly()){
                    cell.changeUseErly();
                    flagx3Word =true;
                }
                break;
            }
        }
        score += incScore;
    }

    private String makeWord(Player player, ArrayList<Cell> workList){
        StringBuilder result = new StringBuilder();
        int first = player.getFirstTap();
        int startMakeWord = 0;
        int index = first;
        if ((first < cellsInRow) | (first + cellsInRow > countCells)) {
            if ((first < cellsInRow)) {
                startMakeWord = first;
                while (startMakeWord < countCells) {
                    if (workList.get(startMakeWord).getIsLetter()) {
                        result.append(workList.get(startMakeWord).getLetter());
                        countScore(workList.get(startMakeWord));
                    } else {
                        break;
                    }
                    startMakeWord += cellsInRow;
                }
                if (flagx3Word)
                    score *= 3;
                if (flagx2Word)
                    score *= 2;
            }
            if  (first + cellsInRow > countCells){
                while (index > 0) {
                    if ((!workList.get(index).getIsLetter()) || (index - cellsInRow < 0)) {
                        startMakeWord = index;
                        break;
                    }
                    index -= cellsInRow;
                }
                startMakeWord += cellsInRow;
                while (startMakeWord < countCells) {
                    if (workList.get(startMakeWord).getIsLetter()) {
                        result.append(workList.get(startMakeWord).getLetter());
                        countScore(workList.get(startMakeWord));
                    } else {
                        break;
                    }
                    startMakeWord += cellsInRow;
                }
                if (flagx3Word)
                    score *= 3;
                if (flagx2Word)
                    score *= 2;
            } else
                return result.toString();
        }
        else {
            if ((workList.get(first + cellsInRow).getIsLetter()) | (workList.get(first - cellsInRow).getIsLetter())) {
                if (!(workList.get(first + 1).getIsLetter()) & (!workList.get(first - 1).getIsLetter())) {
                    while (index > 0) {
                        if ((!workList.get(index).getIsLetter()) || (index - cellsInRow < 0)) {
                            startMakeWord = index;
                            break;
                        }
                        index -= cellsInRow;
                    }
                    startMakeWord += cellsInRow;
                    while (startMakeWord < countCells) {
                        if (workList.get(startMakeWord).getIsLetter()) {
                            result.append(workList.get(startMakeWord).getLetter());
                            countScore(workList.get(startMakeWord));
                        } else {
                            break;
                        }
                        startMakeWord += cellsInRow;
                    }
                    if (flagx3Word)
                        score *= 3;
                    if (flagx2Word)
                        score *= 2;
                } else
                    return result.toString();
            }
        }
        if ((workList.get(first - 1).getIsLetter()) | (workList.get(first + 1).getIsLetter())){
            if (!(workList.get(first - cellsInRow).getIsLetter()) & (!workList.get(first + cellsInRow ).getIsLetter())){
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
                        countScore(workList.get(startMakeWord));
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

    public String checkWord(Player player, ArrayList<Cell> workList) {
        String word = makeWord(player, workList);
        try {
            MyAsyncTask asTask = new MyAsyncTask();
            test = asTask.execute(word).get();
            asTask.cancel(true);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if (test){
            player.addWord(word);
            if(player.getCountLetter() == 0)
                player.appScore(score + 15);
            else
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
        while (countLet < COUNT_LETTER_PLAYER){
            int next =rand.nextInt(COUNT_LETTER);
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
        while (play.getCountLetter() < COUNT_LETTER_PLAYER){
            int next =rand.nextInt(COUNT_LETTER);
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
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                play.deletLetter(list.get(i));
            }
            while (play.getCountLetter() < COUNT_LETTER_PLAYER) {
                int next = rand.nextInt(COUNT_LETTER);
                if (listLetter.get(next).getCount() != 0) {
                    listLetter.get(next).setCount(listLetter.get(next).getCount() - 1);
                    play.addLetter(list.get(index), listLetter.get(next).getLetter());
                    index++;
                }
            }
        } else {
            for (Character let : listLet) {
                int oldCount = findLetter(let).getCount();
                findLetter(let).setCount(oldCount + 1);
            }
            play.clear();
            addLetters(play);
        }
    }

    public ArrayList<Integer> getCountFreeLetter() {
        for(Letter lett: listLetter){
            countFreeLetter.add(lett.getCount());
        }
        return countFreeLetter;
    }

    public  void setCountFreeLetter(ArrayList<Integer> countList){
        for (int i = 0; i < listLetter.size(); i++){
            listLetter.get(i).setCount(countList.get(i));
        }
    }

}
