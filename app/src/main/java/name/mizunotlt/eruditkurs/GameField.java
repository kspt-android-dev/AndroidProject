package name.mizunotlt.eruditkurs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ViewConstructor")
public class GameField extends View {

    private Paint paint = new Paint();
    private ArrayList<CellForLetter> listCellLetter = new ArrayList<>();
    private ArrayList<Cell> workListCells = new ArrayList<>();
    private ArrayList<Integer> listSelectLetter = new ArrayList<>();
    private ArrayList<Integer> indexLetterInBoard = new ArrayList<>();
    //Лист для запоминания букв игрока
    private ArrayList<Character> tempListLetter = new ArrayList<>();
    //Лист для запоминания поля в случае ошибки ввода слова
    private ArrayList<Integer> tempListNumCells= new ArrayList<>();
    private int countCell = 0;
    //false если портретная, true если альбомная
    public boolean flag = false;
    private Player firstPlayer;
    private Player secondPlayer;
    private GameRules rule = new GameRules();
    private boolean nextTurn = true;
    private boolean turn = false;
    private boolean firstTurn = false;
    private final int COUNTCELL_X = 15;
    private final int COUNTCELL_Y = 15;
    private final int COUNT_LETTER = 7;
    private final int SIZE_CELL = 70;
    private final int SIZE_CELL_LETTER = 75;
    private int incPoint = 70;
    private int incPointX;
    private int incPointY;
    private Point startPoint;
    private Point startBorderLetterPoint;
    public String tc ="";
    private List<Bitmap> bitmapListLetter = new ArrayList<>();
    private List<Bitmap> bitmapListLetterBoard = new ArrayList<>();
    Context context;
    Bitmap x2Letterpict;
    Bitmap x2Wordpict;
    Bitmap x3Letterpict;
    Bitmap x3Wordpict;
    Bitmap defpict;
    Bitmap startpict;
    Matrix matrix;
    private void initBitmapListLetter(){
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let1),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let2),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let3),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let4),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let5),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let6),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let7),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let8),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let9),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let10),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let11),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let12),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let13),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let14),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let15),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let16),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let17),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let18),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let19),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let20),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let21),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let22),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let23),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let24),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let25),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let26),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let27),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let28),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let29),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let30),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let31),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let32),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let33),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false));

        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb1),
                SIZE_CELL,SIZE_CELL, false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb2),
                SIZE_CELL,SIZE_CELL,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb3),
                SIZE_CELL,SIZE_CELL,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb4),
                SIZE_CELL,SIZE_CELL,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb5),
                SIZE_CELL,SIZE_CELL,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb6),
                SIZE_CELL,SIZE_CELL,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb7),
                SIZE_CELL,SIZE_CELL,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb8),
                SIZE_CELL,SIZE_CELL,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb9),
                SIZE_CELL,SIZE_CELL,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb10),
                SIZE_CELL,SIZE_CELL,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb11),
                SIZE_CELL,SIZE_CELL, false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb12),
                SIZE_CELL,SIZE_CELL,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb13),
                SIZE_CELL,SIZE_CELL,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb14),
                SIZE_CELL,SIZE_CELL,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb15),
                SIZE_CELL,SIZE_CELL,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb16),
                SIZE_CELL,SIZE_CELL, false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb17),
                SIZE_CELL,SIZE_CELL, false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb18),
                SIZE_CELL,SIZE_CELL,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb19),
                SIZE_CELL,SIZE_CELL,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb20),
                SIZE_CELL,SIZE_CELL,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb21),
                SIZE_CELL,SIZE_CELL,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb22),
                SIZE_CELL,SIZE_CELL, false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb23),
                SIZE_CELL,SIZE_CELL,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb24),
                SIZE_CELL,SIZE_CELL,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb25),
                SIZE_CELL,SIZE_CELL, false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb26),
                SIZE_CELL,SIZE_CELL,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb27),
                SIZE_CELL,SIZE_CELL, false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb28),
                SIZE_CELL,SIZE_CELL, false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb29),
                SIZE_CELL,SIZE_CELL,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb30),
                SIZE_CELL,SIZE_CELL,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb31),
                SIZE_CELL,SIZE_CELL,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb32),
                SIZE_CELL,SIZE_CELL,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb33),
                SIZE_CELL,SIZE_CELL,  false));
    }
    public void resetTurn(){
        turn = !turn;
    }
    public void setStartPoint(){
        if (!flag){
            startPoint = new Point(20,100);
            startBorderLetterPoint = new Point(100, 1900);
            incPointX = 20;
            incPointY = 70;
        }
        else {
            if (flag) {
                startPoint = new Point(0, 5);
                startBorderLetterPoint = new Point(1200, 10);
                incPointX = 0;
                incPointY = 70;
            }
        }
        startGame();
    }
    public void start(){
        if (!firstTurn){
            setListCellLetter();
            firstTurn = true;
        }
        invalidate();
    }
    public void setFlag(boolean flg) {
        flag = flg;
    }
    public void newWord(){
       if((nextTurn)&&(tempListLetter.size() !=0 )){
           this.tc =rule.checkWord(firstPlayer, workListCells);
           if(tc.equals("")){
               int index = 0;
               while (tempListLetter.size() != 0){
                   if (firstPlayer.getListLetter().get(index) == ' '){
                       firstPlayer.addLetter(index, tempListLetter.get(0));
                       tempListLetter.remove(0);
                   }
                   index++;
               }
               for(Integer num : tempListNumCells){
                   workListCells.get(num).setLetter(' ');
               }
           }
           tempListNumCells.clear();
           tempListLetter.clear();
           firstPlayer.clearFirstTap();
       }
       else{
           if (tempListLetter.size() != 0) {
               this.tc = rule.checkWord(secondPlayer, workListCells);
               if (tc.equals("")) {
                   int index = 0;
                   while (tempListLetter.size() != 0) {
                       if (secondPlayer.getListLetter().get(index) == ' ') {
                           secondPlayer.addLetter(index, tempListLetter.get(0));
                           tempListLetter.remove(0);
                       }
                       index++;
                   }
                   for (Integer num : tempListNumCells) {
                       workListCells.get(num).setLetter(' ');
                   }
               }
           }
           tempListNumCells.clear();
           tempListLetter.clear();
           secondPlayer.clearFirstTap();
       }
        invalidate();
    }
    public void changeTurn(){
        if (nextTurn){
            rule.resetLetter(firstPlayer);
            nextTurn = false;
        }
        else{
            rule.resetLetter(secondPlayer);
            nextTurn = true;
        }
        invalidate();
    }
    public void changeTurnResetLetter(){
        if (nextTurn){
            resycleLetter();
            nextTurn = false;
        }
        else{
            resycleLetter();
            nextTurn = true;
        }
        invalidate();
    }
    public GameField(Context context, Player player1, Player player2) {
        super(context);
        this.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        this.setClickable(true);
        this.setOnTouchListener(new GameFieldListener(this));
        matrix = new Matrix();
        setPlayers(player1,player2);
        setPictures();
        initBitmapListLetter();
    }
    public void setPlayers(Player player1, Player player2){
        this.firstPlayer = player1;
        rule.addLetters(firstPlayer);
        this.secondPlayer = player2;
        rule.addLetters(secondPlayer);
    }

    //Для восстановления поля

    public void resetGameField(@NotNull Bundle savedInstanceState){
        //Восстановление игроков
        firstPlayer.setName((String) savedInstanceState.get("firstPlayerName"));
        secondPlayer.setName((String) savedInstanceState.get("secondPlayerName"));
        firstPlayer.setScore((int) savedInstanceState.get("firstPlayerScore"));
        secondPlayer.setScore((int) savedInstanceState.get("secondPlayerScore"));
        firstPlayer.setListLetterAfterReset((char []) savedInstanceState.get("firstPlayerLetter"));
        secondPlayer.setListLetterAfterReset((char []) savedInstanceState.get("secondPlayerLetter"));
        //восстановление переменных поля
        this.firstTurn = (boolean) savedInstanceState.get("firstTurn");
        this.flag = (boolean) savedInstanceState.get("flag");
        this.turn = (boolean) savedInstanceState.get("turn");
        this.nextTurn = (boolean) savedInstanceState.get("nextTurn");
        //Восстановление элементов на поле
        setLetterInBoard((char []) savedInstanceState.get("letterInBoard"),
                (ArrayList<Integer>) savedInstanceState.get("indexLetterInBoard"));
        //Восстановление оставшихся букв
        this.rule.setCountFreeLetter( (ArrayList<Integer>) savedInstanceState.get("freeLetter"));
        setListCellLetter();
        invalidate();
    }
    public Player getFirstPlayer(){
        return firstPlayer;
    }
    public Player getSecondPlayer(){
        return secondPlayer;
    }
    public ArrayList<CellForLetter> getListCellLetter() {
        return listCellLetter;
    }
    public ArrayList<Cell> getWorkListCells(){
        return workListCells;
    }

    public ArrayList<Integer> getIndexLetterInBoadr(){
        return indexLetterInBoard;
    }
    public char[] getLetterInBoard(){
        char[] letterInBoard = new char[workListCells.size()];
        for(int i =0; i < workListCells.size(); i++){
            if (workListCells.get(i).getIsLetter()){
                letterInBoard[i] = workListCells.get(i).getLetter();
                indexLetterInBoard.add(i);
            }
        }
        return letterInBoard;
    }
    public void setLetterInBoard(char[] array, ArrayList<Integer> indexList){
        for(int num: indexList){
            workListCells.get(num).setLetter(array[num]);
        }
    }
    public GameRules getGameRule(){
        return rule;
    }
    public boolean getTurn(){
        return turn;
    }
    public boolean getNextTurn(){
        return nextTurn;
    }
    public void setNextTurn(boolean t){
        this.nextTurn = t;
    }
    public boolean getFirstTurn(){
        return firstTurn;
    }
    public boolean getFlag(){
        return flag;
    }

    //Инициализация начала игры
    public void startGame(){
        templateOne();
        templateTwo();
        templateThree();
    }
    private void setListCellLetter(){
        for (int i = 0; i < COUNT_LETTER; i++){
            listCellLetter.add(i,new CellForLetter(new Point(startBorderLetterPoint.x, startBorderLetterPoint.y),i));
            startBorderLetterPoint.x += 95;
        }
    }
    private void setPictures(){
        x2Letterpict = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.x2letter),
                SIZE_CELL,SIZE_CELL,false);
        x2Wordpict = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.x2word),
                SIZE_CELL,SIZE_CELL,false);
        x3Letterpict = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.x3letter),
                SIZE_CELL,SIZE_CELL,false);
        x3Wordpict = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.x3word),
                SIZE_CELL,SIZE_CELL,false);
        defpict = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.defpict),
                SIZE_CELL,SIZE_CELL,false);
        startpict = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.start),
                SIZE_CELL,SIZE_CELL,false);
    }
    private void templateOne(){
        for (int i =0; i <= 4; i++){
            for (int j = 0; j < COUNTCELL_Y; j++){
                countCell++;
                Cell tempCell = new Cell(new Point(startPoint.x, startPoint.y),countCell);
                switch (i){
                    case 0 :{
                        switch (j){
                            case 0:{
                                tempCell.setTrueX3Word();
                                break;
                            }
                            case 3: {
                                tempCell.setTrueX2Letter();
                                break;
                            }
                            case 7: {
                                tempCell.setTrueX3Word();
                                break;
                            }
                            case 11: {
                                tempCell.setTrueX2Letter();
                                break;
                            }
                            case 14:{
                                tempCell.setTrueX3Word();
                                break;
                            }
                            default: {
                                tempCell.setDefCell();
                                break;
                            }
                        }
                        break;
                    }
                    case 1 :{
                        switch (j){
                            case 1 : {
                                tempCell.setTrueX2Word();
                                break;
                            }
                            case 5 : {
                                tempCell.setTrueX3Letter();
                                break;
                            }
                            case 9 : {
                                tempCell.setTrueX3Letter();
                                break;
                            }
                            case 13 :{
                                tempCell.setTrueX2Word();
                                break;
                            }
                            default: {
                                tempCell.setDefCell();
                                break;
                            }
                        }
                        break;
                    }
                    case 2 :{
                        switch (j){
                            case 2 : {
                                tempCell.setTrueX2Word();
                                break;
                            }
                            case 6 : {
                                tempCell.setTrueX2Letter();
                                break;
                            }
                            case 8 : {
                                tempCell.setTrueX2Letter();
                                break;
                            }
                            case 12: {
                                tempCell.setTrueX2Word();
                                break;
                            }
                            default: {
                                tempCell.setDefCell();
                                break;
                            }
                        }
                        break;
                    }
                    case 3:{
                        switch (j){
                            case 0 : {
                                tempCell.setTrueX2Letter();
                                break;
                            }
                            case 3 : {
                                tempCell.setTrueX2Word();
                                break;
                            }
                            case 7 : {
                                tempCell.setTrueX2Letter();
                                break;
                            }
                            case 11 : {
                                tempCell.setTrueX2Word();
                                break;
                            }
                            case 14 : {
                                tempCell.setTrueX2Letter();
                                break;
                            }
                            default: {
                                tempCell.setDefCell();
                                break;
                            }
                        }
                        break;
                    }
                    case 4 :{
                        switch (j){
                            case 4 : {
                                tempCell.setTrueX2Word();
                                break;
                            }
                            case 10 : {
                                tempCell.setTrueX2Word();
                                break;
                            }
                            default: {
                                tempCell.setDefCell();
                                break;
                            }
                        }
                        break;
                    }
                }
                workListCells.add(tempCell);
                if (j != COUNTCELL_Y - 1){
                    startPoint.x += incPoint;
                }
            }
            startPoint.x = incPointX;
            startPoint.y +=incPoint;
        }
    }
    private void templateTwo(){
        for (int i = 5; i < 10; i++){
            for (int j = 0 ; j < COUNTCELL_Y; j++){
                countCell++;
                Cell tempCell = new Cell(new Point(startPoint.x, startPoint.y),countCell);
                switch (i){
                    case 5 :{
                        switch (j){
                            case 1 : {
                                tempCell.setTrueX3Letter();
                                break;
                            }
                            case 13 :{
                                tempCell.setTrueX3Letter();
                                break;
                            }
                            default: {
                                tempCell.setDefCell();
                                break;
                            }
                        }
                        break;
                    }
                    case 6 :{
                        switch (j){
                            case 2 : {
                                tempCell.setTrueX2Letter();
                                break;
                            }
                            case 6 : {
                                tempCell.setTrueX2Letter();
                                break;
                            }
                            case 8 : {
                                tempCell.setTrueX2Letter();
                                break;
                            }
                            case 12 : {
                                tempCell.setTrueX2Letter();
                                break;
                            }
                            default: {
                                tempCell.setDefCell();
                                break;
                            }
                        }
                        break;
                    }
                    case 7 :{
                        switch (j){
                            case 0 : {
                                tempCell.setTrueX3Word();
                                break;
                            }
                            case 3 : {
                                tempCell.setTrueX2Letter();
                                break;
                            }
                            case 7 : {
                                tempCell.setStartPosition();
                                break;
                            }
                            case 11: {
                                tempCell.setTrueX2Letter();
                                break;
                            }
                            case 14 : {
                                tempCell.setTrueX2Word();
                                break;
                            }
                            default: {
                                tempCell.setDefCell();
                                break;
                            }
                        }
                        break;
                    }
                    case 8 :{
                        switch (j){
                            case 2 : {
                                tempCell.setTrueX2Letter();
                                break;
                            }
                            case 6 : {
                                tempCell.setTrueX2Letter();
                                break;
                            }
                            case 8 : {
                                tempCell.setTrueX2Letter();
                                break;
                            }
                            case 12 : {
                                tempCell.setTrueX2Letter();
                                break;
                            }
                            default: {
                                tempCell.setDefCell();
                                break;
                            }
                        }
                        break;
                    }
                    case 9 :{
                        switch (j){
                            case 1 : {
                                tempCell.setTrueX3Letter();
                                break;
                            }
                            case 13 : {
                                tempCell.setTrueX3Letter();
                                break;
                            }
                            default: {
                                tempCell.setDefCell();
                                break;
                            }
                        }
                        break;
                    }
                }
                workListCells.add(tempCell);
                if (j != COUNTCELL_Y - 1){
                    startPoint.x += incPoint;
                }
            }
            startPoint.x = incPointX;
            startPoint.y +=incPoint;
        }
    }
    private void templateThree(){
        for (int i = 10; i < COUNTCELL_X; i++){
            for (int j = 0; j < COUNTCELL_Y;j++ ){
                countCell++;
                Cell tempCell = new Cell(new Point(startPoint.x, startPoint.y),countCell);
                switch (i){
                    case 10 :{
                        switch (j){
                            case 4 : {
                                tempCell.setTrueX2Word();
                                break;
                            }
                            case 10 : {
                                tempCell.setTrueX2Word();
                                break;
                            }
                            default: {
                                tempCell.setDefCell();
                                break;
                            }
                        }
                        break;
                    }
                    case 11 :{
                        switch (j){
                            case 0 : {
                                tempCell.setTrueX2Letter();
                                break;
                            }
                            case 3 : {
                                tempCell.setTrueX2Word();
                                break;
                            }
                            case 7 : {
                                tempCell.setTrueX2Letter();
                                break;
                            }
                            case 11 : {
                                tempCell.setTrueX2Word();
                                break;
                            }
                            case 14 : {
                                tempCell.setTrueX2Letter();
                                break;
                            }
                            default: {
                                tempCell.setDefCell();
                                break;
                            }
                        }
                        break;
                    }
                    case 12 :{
                        switch (j){
                            case 2 : {
                                tempCell.setTrueX2Word();
                                break;
                            }
                            case 6 : {
                                tempCell.setTrueX2Letter();
                                break;
                            }
                            case 8 : {
                                tempCell.setTrueX2Letter();
                                break;
                            }
                            case 12 : {
                                tempCell.setTrueX2Word();
                                break;
                            }
                            default: {
                                tempCell.setDefCell();
                                break;
                            }
                        }
                        break;
                    }
                    case 13 :{
                        switch (j){
                            case 1 : {
                                tempCell.setTrueX2Word();
                                break;
                            }
                            case 5 : {
                                tempCell.setTrueX3Letter();
                                break;
                            }
                            case 9 : {
                                tempCell.setTrueX3Letter();
                                break;
                            }
                            case 13 : {
                                tempCell.setTrueX2Word();
                                break;
                            }
                            default: {
                                tempCell.setDefCell();
                                break;
                            }
                        }
                        break;
                    }
                    case 14 :{
                        switch (j){
                            case 0 : {
                                tempCell.setTrueX3Word();
                                break;
                            }
                            case 3 : {
                                tempCell.setTrueX2Letter();
                                break;
                            }
                            case 7 : {
                                tempCell.setTrueX3Word();
                                break;
                            }
                            case 11 : {
                                tempCell.setTrueX2Letter();
                                break;
                            }
                            case 14 : {
                                tempCell.setTrueX3Word();
                                break;
                            }
                            default: {
                                tempCell.setDefCell();
                                break;
                            }
                        }
                        break;
                    }
                }
                workListCells.add(tempCell);
                if (j != COUNTCELL_Y - 1){
                    startPoint.x += incPoint;
                }
            }
            startPoint.x = incPointX;
            startPoint.y += incPoint;
        }
    }
    public void resycleLetter(){
        for(CellForLetter cell: listCellLetter){
            if (cell.isSelect()){
                listSelectLetter.add(cell.getNumCell());
            }
        }
        if (nextTurn){
            rule.recycleLetter(firstPlayer, listSelectLetter);
        }
        else{
            rule.recycleLetter(secondPlayer, listSelectLetter);
        }
        if (listSelectLetter.size() != 0)
            for(Integer index: listSelectLetter){
                if (listCellLetter.get(index).isSelect()){
                    listCellLetter.get(index).changeSelect();
                }
            }
        listSelectLetter.clear();
        invalidate();
    }
    @Override
    public boolean performClick() {
        return super.performClick();
    }
    public void updateField(int num){

        for(CellForLetter cell: listCellLetter){
            if (cell.isSelect()){
                listSelectLetter.add(cell.getNumCell());
            }
        }
        try{
            if (nextTurn){
                if (!workListCells.get(num - 1).getIsLetter()) {
                    workListCells.get(num - 1).setLetter(firstPlayer.getListLetter().get(listSelectLetter.get(0)));
                    tempListLetter.add(firstPlayer.getListLetter().get(listSelectLetter.get(0)));
                    firstPlayer.setFirstTap(num - 1);
                    tempListNumCells.add(num - 1);
                    firstPlayer.deletLetter(listSelectLetter.get(0));
                    listCellLetter.get(listSelectLetter.get(0)).changeSelect();
                }
            }
            else{
                if (!workListCells.get(num - 1).getIsLetter()) {
                    workListCells.get(num - 1).setLetter(secondPlayer.getListLetter().get(listSelectLetter.get(0)));
                    tempListLetter.add(secondPlayer.getListLetter().get(listSelectLetter.get(0)));
                    secondPlayer.setFirstTap(num - 1);
                    tempListNumCells.add(num - 1);
                    secondPlayer.deletLetter(listSelectLetter.get(0));
                    listCellLetter.get(listSelectLetter.get(0)).changeSelect();
                }
            }
        }
        catch (IndexOutOfBoundsException e){

        }
        listSelectLetter.clear();
        invalidate();
    }
    private int findLetter(char let){
        for (int i = 0; i < 33; i++) {
            switch (let) {
                case 'А':
                    return 0;
                case 'Б':
                    return 1;
                case 'В':
                    return 2;
                case 'Г':
                    return 3;
                case 'Д':
                    return 4;
                case 'Е':
                    return 5;
                case 'Ж':
                    return 6;
                case 'З':
                    return 7;
                case 'И':
                    return 8;
                case 'Й':
                    return 9;
                case 'К':
                    return 10;
                case 'Л':
                    return 11;
                case 'М':
                    return 12;
                case 'Н':
                    return 13;
                case 'О':
                    return 14;
                case 'П':
                    return 15;
                case 'Р':
                    return 16;
                case 'С':
                    return 17;
                case 'Т':
                    return 18;
                case 'У':
                    return 19;
                case 'Ф':
                    return 20;
                case 'Х':
                    return 21;
                case 'Ц':
                    return 22;
                case 'Ч':
                    return 23;
                case 'Ш':
                    return 24;
                case 'Щ':
                    return 25;
                case 'Ь':
                    return 26;
                case 'Ы':
                    return 27;
                case 'Ъ':
                    return 28;
                case 'Э':
                    return 29;
                case 'Ю':
                    return 30;
                case 'Я':
                    return 31;
                case '*':
                    return 32;
            }
        }
        return 33;
    }
    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        try {
            for (int i = 0; i < COUNT_LETTER; i++){
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawPath(listCellLetter.get(i).getPath(),paint);
                matrix.setTranslate(listCellLetter.get(i).getStartPoint().x - 5,
                        listCellLetter.get(i).getStartPoint().y - 5);
                List<Character> list = nextTurn ? firstPlayer.getListLetter() : secondPlayer.getListLetter();
                if (list.get(i) != ' ')
                    canvas.drawBitmap(bitmapListLetter.get(findLetter(list.get(i))),matrix,paint);
            }
        }catch (IndexOutOfBoundsException | NullPointerException e){
        }
        for (int i = 0; i < workListCells.size(); i++){
            matrix.setTranslate(workListCells.get(i).getStartPoint().x,
                    workListCells.get(i).getStartPoint().y);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(workListCells.get(i).getPath(), paint);
            if (workListCells.get(i).getLetter() != ' '){
                canvas.drawBitmap(bitmapListLetterBoard.get(findLetter(workListCells.get(i).getLetter())),matrix,paint);
            }
            else {
                switch (workListCells.get(i).typeCell()) {
                    case 1: {
                        canvas.drawBitmap(defpict, matrix, paint);
                        break;
                    }
                    case 2: {
                        canvas.drawBitmap(startpict, matrix, paint);
                        break;
                    }
                    case 3: {
                        canvas.drawBitmap(x2Letterpict, matrix, paint);
                        break;
                    }
                    case 4: {
                        canvas.drawBitmap(x3Letterpict, matrix, paint);
                        break;
                    }
                    case 5: {
                        canvas.drawBitmap(x2Wordpict, matrix, paint);
                        break;
                    }
                    case 6: {
                        canvas.drawBitmap(x3Wordpict, matrix, paint);
                        break;
                    }
                }
            }
        }
    }
}
