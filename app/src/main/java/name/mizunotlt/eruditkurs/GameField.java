package name.mizunotlt.eruditkurs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ViewConstructor")
public class GameField extends View {

    private Paint paint = new Paint();
    private ArrayList<CellForLetter> listCellLetter = new ArrayList<>();
    private ArrayList<Cell> workListCells = new ArrayList<>();
    private ArrayList<Integer> listSelectLetter = new ArrayList<>();
    //Лист для запоминания букв игрока
    private ArrayList<Character> tempListLetter = new ArrayList<>();
    //Лист для запоминания поля
    private ArrayList<Integer> tempListNumCells= new ArrayList<>();
    private int countCell = 0;
    private Player firstPlayer;
    private Player secondPlayer;
    private GameRules rule = new GameRules();
    private boolean nextTurn = true;
    private boolean turn = false;
    private final int COUNTCELL_X = 15;
    private final int COUNTCELL_Y = 15;
    private final int COUNT_LETTER = 7;
    private final int SIZE_CELL = 70;
    private final int SIZE_CELL_LETTER = 75;
    private int incPoint = 70;
    private Point startPoint = new Point(20,100);
    private Point startBorderLetterPoint = new Point(100, 1800);
    public String tc ="";

    Context context;
    Bitmap x2Letterpict;
    Bitmap x2Wordpict;
    Bitmap x3Letterpict;
    Bitmap x3Wordpict;
    Bitmap defpict;
    Bitmap startpict;
    Matrix matrix;
    //Картинки букв будут располагаться в отдельном Layout
    Bitmap let1; //А
    Bitmap let2; //Б
    Bitmap let3; //В
    Bitmap let4; //Г
    Bitmap let5; //Д
    Bitmap let6; //Е
    Bitmap let7; //Ж
    Bitmap let8; //З
    Bitmap let9; //И
    Bitmap let10; //Й
    Bitmap let11; //К
    Bitmap let12; //Л
    Bitmap let13; //М
    Bitmap let14; //Н
    Bitmap let15; //О
    Bitmap let16; //П
    Bitmap let17; //Р
    Bitmap let18; //С
    Bitmap let19; //Т
    Bitmap let20; //У
    Bitmap let21; //Ф
    Bitmap let22; //Х
    Bitmap let23; //Ц
    Bitmap let24; //Ч
    Bitmap let25; //Ш
    Bitmap let26; //Щ
    Bitmap let27; //Ь
    Bitmap let28; //Ы
    Bitmap let29; //Ъ
    Bitmap let30; //Э
    Bitmap let31; //Ю
    Bitmap let32; //Я
    Bitmap let33; // *
    Bitmap letb1; //А
    Bitmap letb2; //Б
    Bitmap letb3; //В
    Bitmap letb4; //Г
    Bitmap letb5; //Д
    Bitmap letb6; //Е
    Bitmap letb7; //Ж
    Bitmap letb8; //З
    Bitmap letb9; //И
    Bitmap letb10; //Й
    Bitmap letb11; //К
    Bitmap letb12; //Л
    Bitmap letb13; //М
    Bitmap letb14; //Н
    Bitmap letb15; //О
    Bitmap letb16; //П
    Bitmap letb17; //Р
    Bitmap letb18; //С
    Bitmap letb19; //Т
    Bitmap letb20; //У
    Bitmap letb21; //Ф
    Bitmap letb22; //Х
    Bitmap letb23; //Ц
    Bitmap letb24; //Ч
    Bitmap letb25; //Ш
    Bitmap letb26; //Щ
    Bitmap letb27; //Ь
    Bitmap letb28; //Ы
    Bitmap letb29; //Ъ
    Bitmap letb30; //Э
    Bitmap letb31; //Ю
    Bitmap letb32; //Я
    Bitmap letb33; // *
    public boolean getTurn(){
        return turn;
    }
    public void resetTurn(){
        turn = !turn;
    }
    public boolean getNextTurn(){
        return nextTurn;
    }
    public void start(){
        setListCellLetter();
        invalidate();
    }
    public void newWord(){
       if(nextTurn){
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
           this.tc =rule.checkWord(secondPlayer, workListCells);
           if(tc.equals("")){
               int index = 0;
               while (tempListLetter.size() != 0){
                   if (secondPlayer.getListLetter().get(index) == ' '){
                       secondPlayer.addLetter(index, tempListLetter.get(0));
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

    public ArrayList<CellForLetter> getListCellLetter() {
        return listCellLetter;
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
        setLetterPictures();
        startGame();

    }
    public ArrayList<Cell> getWorkListCells(){
        return workListCells;
    }
    public void setPlayers(Player player1, Player player2){
        this.firstPlayer = player1;
        rule.addLetters(firstPlayer);
        this.secondPlayer = player2;
        rule.addLetters(secondPlayer);
    }

    public Player getFirstPlayer(){
        return firstPlayer;
    }

    public Player getSecondPlayer(){
        return secondPlayer;
    }

    public void startGame(){
        templateOne();
        templateTwo();
        templateThree();
    }

    //Мб добавить  change для другого игрока во время перехода хода
    private void setListCellLetter(){
        for (int i = 0; i < COUNT_LETTER; i++){
            listCellLetter.add(i,new CellForLetter(new Point(startBorderLetterPoint.x, startBorderLetterPoint.y),i));
            startBorderLetterPoint.x += 95;
        }
    }
    private void setLetterPictures(){
        let1 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let1),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let2 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let2),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let3 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let3),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let4 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let4),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let5 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let5),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let6 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let6),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let7 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let7),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let8 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let8),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let9 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let9),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let10 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let10),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let11 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let11),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let12 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let12),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let13 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let13),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let14 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let14),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let15 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let15),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let16 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let16),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let17 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let17),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let18 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let19 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let19),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let20 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let20),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let21 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let21),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let22 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let22),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER,false);
        let23 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let23),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let24 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let24),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let25 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let25),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let26 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let26),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let27 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let27),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let28 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let28),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let29 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let29),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let30 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let30),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let31 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let31),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let32 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let32),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);
        let33 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let33),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER, false);


        //Инициализация картинок для доски

        letb1 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb1),
                SIZE_CELL,SIZE_CELL, false);
        letb2 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb2),
                SIZE_CELL,SIZE_CELL,  false);
        letb3 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb3),
                SIZE_CELL,SIZE_CELL,  false);
        letb4 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb4),
                SIZE_CELL,SIZE_CELL,  false);
        letb5 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb5),
                SIZE_CELL,SIZE_CELL,  false);
        letb6 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb6),
                SIZE_CELL,SIZE_CELL,  false);
        letb7 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb7),
                SIZE_CELL,SIZE_CELL,  false);
        letb8 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb8),
                SIZE_CELL,SIZE_CELL,  false);
        letb9 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb9),
                SIZE_CELL,SIZE_CELL,  false);
        letb10 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb33),
                SIZE_CELL,SIZE_CELL,  false);
        letb11 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb10),
                SIZE_CELL,SIZE_CELL, false);
        letb12 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb11),
                SIZE_CELL,SIZE_CELL,  false);
        letb13 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb12),
                SIZE_CELL,SIZE_CELL,  false);
        letb14 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb13),
                SIZE_CELL,SIZE_CELL,  false);
        letb15 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb14),
                SIZE_CELL,SIZE_CELL,  false);
        letb16 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb15),
                SIZE_CELL,SIZE_CELL, false);
        letb17 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb16),
                SIZE_CELL,SIZE_CELL, false);
        letb18 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb17),
                SIZE_CELL,SIZE_CELL,  false);
        letb19 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb18),
                SIZE_CELL,SIZE_CELL,  false);
        letb20 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb19),
                SIZE_CELL,SIZE_CELL,  false);
        letb21 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb20),
                SIZE_CELL,SIZE_CELL,  false);
        letb22 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb21),
                SIZE_CELL,SIZE_CELL, false);
        letb23 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb22),
                SIZE_CELL,SIZE_CELL,  false);
        letb24 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb23),
                SIZE_CELL,SIZE_CELL,  false);
        letb25 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb24),
                SIZE_CELL,SIZE_CELL, false);
        letb26 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb25),
                SIZE_CELL,SIZE_CELL,  false);
        letb27 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb26),
                SIZE_CELL,SIZE_CELL, false);
        letb28 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb27),
                SIZE_CELL,SIZE_CELL, false);
        letb29 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb28),
                SIZE_CELL,SIZE_CELL,  false);
        letb30 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb29),
                SIZE_CELL,SIZE_CELL,  false);
        letb31 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb30),
                SIZE_CELL,SIZE_CELL,  false);
        letb32 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb31),
                SIZE_CELL,SIZE_CELL,  false);
        letb33 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb32),
                SIZE_CELL,SIZE_CELL,  false);
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
            startPoint.x = 20;
            startPoint.y +=70;
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
            startPoint.x = 20;
            startPoint.y +=70;
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
            startPoint.x = 20;
            startPoint.y += 70;
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
                workListCells.get(num - 1).setLetter(firstPlayer.getListLetter().get(listSelectLetter.get(0)));
                tempListLetter.add(firstPlayer.getListLetter().get(listSelectLetter.get(0)));
                firstPlayer.setFirstTap(num - 1);
                tempListNumCells.add(num - 1);
                firstPlayer.deletLetter(listSelectLetter.get(0));
                listCellLetter.get(listSelectLetter.get(0)).changeSelect();
            }
            else{
                workListCells.get(num - 1).setLetter(secondPlayer.getListLetter().get(listSelectLetter.get(0)));
                tempListLetter.add(firstPlayer.getListLetter().get(listSelectLetter.get(0)));
                secondPlayer.setFirstTap(num - 1);
                secondPlayer.deletLetter(listSelectLetter.get(0));
                listCellLetter.get(listSelectLetter.get(0)).changeSelect();
            }
        }
        catch (IndexOutOfBoundsException e){

        }
        listSelectLetter.clear();
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        //int tempCountX1 = 0;
        try {
            for (int i = 0; i < COUNT_LETTER; i++){
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawPath(listCellLetter.get(i).getPath(),paint);
                matrix.setTranslate(listCellLetter.get(i).getStartPoint().x - 5,
                        listCellLetter.get(i).getStartPoint().y - 5);
                List<Character> list = nextTurn ? firstPlayer.getListLetter() : secondPlayer.getListLetter();
                switch (list.get(i)){
                    case 'А': {
                        canvas.drawBitmap(let1,matrix,paint);
                        break;
                    }
                    case 'Б': {
                        canvas.drawBitmap(let2,matrix,paint);
                        break;
                    }
                    case 'В': {
                        canvas.drawBitmap(let3,matrix,paint);
                        break;
                    }
                    case 'Г': {
                        canvas.drawBitmap(let4,matrix,paint);
                        break;
                    }
                    case 'Д': {
                        canvas.drawBitmap(let5,matrix,paint);
                        break;
                    }
                    case 'Е': {
                        canvas.drawBitmap(let6,matrix,paint);
                        break;
                    }
                    case 'Ж': {
                        canvas.drawBitmap(let7,matrix,paint);
                        break;
                    }
                    case 'З': {
                        canvas.drawBitmap(let8,matrix,paint);
                        break;
                    }
                    case 'И': {
                        canvas.drawBitmap(let9,matrix,paint);
                        break;
                    }
                    case 'Й': {
                        canvas.drawBitmap(let10,matrix,paint);
                        break;
                    }
                    case 'К': {
                        canvas.drawBitmap(let11,matrix,paint);
                        break;
                    }
                    case 'Л': {
                        canvas.drawBitmap(let12,matrix,paint);
                        break;
                    }
                    case 'М': {
                        canvas.drawBitmap(let13,matrix,paint);
                        break;
                    }
                    case 'Н': {
                        canvas.drawBitmap(let14,matrix,paint);
                        break;
                    }
                    case 'О': {
                        canvas.drawBitmap(let15,matrix,paint);
                        break;
                    }
                    case 'П': {
                        canvas.drawBitmap(let16,matrix,paint);
                        break;
                    }
                    case 'Р': {
                        canvas.drawBitmap(let17,matrix,paint);
                        break;
                    }
                    case 'С': {
                        canvas.drawBitmap(let18,matrix,paint);
                        break;
                    }
                    case 'Т': {
                        canvas.drawBitmap(let19,matrix,paint);
                        break;
                    }
                    case 'У': {
                        canvas.drawBitmap(let20,matrix,paint);
                        break;
                    }
                    case 'Ф': {
                        canvas.drawBitmap(let21,matrix,paint);
                        break;
                    }
                    case 'Х': {
                        canvas.drawBitmap(let22,matrix,paint);
                        break;
                    }
                    case 'Ц': {
                        canvas.drawBitmap(let23,matrix,paint);
                        break;
                    }
                    case 'Ч': {
                        canvas.drawBitmap(let24,matrix,paint);
                        break;
                    }
                    case 'Ш': {
                        canvas.drawBitmap(let25,matrix,paint);
                        break;
                    }
                    case 'Щ': {
                        canvas.drawBitmap(let26,matrix,paint);
                        break;
                    }
                    case 'Ь': {
                        canvas.drawBitmap(let27,matrix,paint);
                        break;
                    }
                    case 'Ы': {
                        canvas.drawBitmap(let28,matrix,paint);
                        break;
                    }
                    case 'Ъ': {
                        canvas.drawBitmap(let29,matrix,paint);
                        break;
                    }
                    case 'Э': {
                        canvas.drawBitmap(let30,matrix,paint);
                        break;
                    }
                    case 'Ю': {
                        canvas.drawBitmap(let31,matrix,paint);
                        break;
                    }
                    case 'Я': {
                        canvas.drawBitmap(let32,matrix,paint);
                        break;
                    }
                    case '*': {
                        canvas.drawBitmap(let33,matrix,paint);
                        break;
                    }
                }
            }
        }catch (IndexOutOfBoundsException | NullPointerException e){
        }
        for (int i = 0; i < workListCells.size(); i++){
            matrix.setTranslate(workListCells.get(i).getStartPoint().x,
                    workListCells.get(i).getStartPoint().y);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(workListCells.get(i).getPath(), paint);
            if (workListCells.get(i).getLetter() != ' '){
                switch (workListCells.get(i).getLetter()){//
                    case 'А': {
                        canvas.drawBitmap(letb1,matrix,paint);
                        break;
                    }
                    case 'Б': {
                        canvas.drawBitmap(letb2,matrix,paint);
                        break;
                    }
                    case 'В': {
                        canvas.drawBitmap(letb3,matrix,paint);
                        break;
                    }
                    case 'Г': {
                        canvas.drawBitmap(letb4,matrix,paint);
                        break;
                    }
                    case 'Д': {
                        canvas.drawBitmap(letb5,matrix,paint);
                        break;
                    }
                    case 'Е': {
                        canvas.drawBitmap(letb6,matrix,paint);
                        break;
                    }
                    case 'Ж': {
                        canvas.drawBitmap(letb7,matrix,paint);
                        break;
                    }
                    case 'З': {
                        canvas.drawBitmap(letb8,matrix,paint);
                        break;
                    }
                    case 'И': {
                        canvas.drawBitmap(letb9,matrix,paint);
                        break;
                    }
                    case 'Й': {
                        canvas.drawBitmap(letb10,matrix,paint);
                        break;
                    }
                    case 'К': {
                        canvas.drawBitmap(letb11,matrix,paint);
                        break;
                    }
                    case 'Л': {
                        canvas.drawBitmap(letb12,matrix,paint);
                        break;
                    }
                    case 'М': {
                        canvas.drawBitmap(letb13,matrix,paint);
                        break;
                    }
                    case 'Н': {
                        canvas.drawBitmap(letb14,matrix,paint);
                        break;
                    }
                    case 'О': {
                        canvas.drawBitmap(letb15,matrix,paint);
                        break;
                    }
                    case 'П': {
                        canvas.drawBitmap(letb16,matrix,paint);
                        break;
                    }
                    case 'Р': {
                        canvas.drawBitmap(letb17,matrix,paint);
                        break;
                    }
                    case 'С': {
                        canvas.drawBitmap(letb18,matrix,paint);
                        break;
                    }
                    case 'Т': {
                        canvas.drawBitmap(letb19,matrix,paint);
                        break;
                    }
                    case 'У': {
                        canvas.drawBitmap(letb20,matrix,paint);
                        break;
                    }
                    case 'Ф': {
                        canvas.drawBitmap(letb21,matrix,paint);
                        break;
                    }
                    case 'Х': {
                        canvas.drawBitmap(letb22,matrix,paint);
                        break;
                    }
                    case 'Ц': {
                        canvas.drawBitmap(letb23,matrix,paint);
                        break;
                    }
                    case 'Ч': {
                        canvas.drawBitmap(letb24,matrix,paint);
                        break;
                    }
                    case 'Ш': {
                        canvas.drawBitmap(letb25,matrix,paint);
                        break;
                    }
                    case 'Щ': {
                        canvas.drawBitmap(letb26,matrix,paint);
                        break;
                    }
                    case 'Ь': {
                        canvas.drawBitmap(letb27,matrix,paint);
                        break;
                    }
                    case 'Ы': {
                        canvas.drawBitmap(letb28,matrix,paint);
                        break;
                    }
                    case 'Ъ': {
                        canvas.drawBitmap(letb29,matrix,paint);
                        break;
                    }
                    case 'Э': {
                        canvas.drawBitmap(letb30,matrix,paint);
                        break;
                    }
                    case 'Ю': {
                        canvas.drawBitmap(letb31,matrix,paint);
                        break;
                    }
                    case 'Я': {
                        canvas.drawBitmap(letb32,matrix,paint);
                        break;
                    }
                    case '*': {
                        canvas.drawBitmap(letb33,matrix,paint);
                        break;
                    }
                }
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
