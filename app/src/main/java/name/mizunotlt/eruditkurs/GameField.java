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
    private ArrayList<Integer> listSelectLetter = new ArrayList<>();
    //Лист для запоминания букв игрока
    private ArrayList<Character> tempListLetter = new ArrayList<>();
    //Лист для запоминания поля в случае ошибки ввода слова
    private ArrayList<Integer> tempListNumCells= new ArrayList<>();
    //false если портретная, true если альбомная
    private Point sizeScreen = new Point();
    public boolean orientation = false;
    private final int COUNT_LETTER = 7;
    private final int NUMBER_CELL_IN_ROW = 15;
    private  int SIZE_CELL_LETTER;
    private int sizeCell;
    private List<Bitmap> bitmapListLetter = new ArrayList<>();
    private List<Bitmap> bitmapListLetterBoard = new ArrayList<>();
    private GameMechanic games;
    private final int indent = 100;
    private final int indentY = 5;
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
                SIZE_CELL_LETTER,SIZE_CELL_LETTER,false));
        bitmapListLetter.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.let13),
                SIZE_CELL_LETTER,SIZE_CELL_LETTER,false));
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
                sizeCell,sizeCell, false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb2),
                sizeCell,sizeCell,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb3),
                sizeCell,sizeCell,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb4),
                sizeCell,sizeCell, false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb5),
                sizeCell,sizeCell,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb6),
                sizeCell,sizeCell,false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb7),
                sizeCell,sizeCell,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb8),
                sizeCell,sizeCell, false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb9),
                sizeCell,sizeCell,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb10),
                sizeCell,sizeCell,false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb11),
                sizeCell,sizeCell, false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb12),
                sizeCell,sizeCell,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb13),
                sizeCell,sizeCell,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb14),
                sizeCell,sizeCell,false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb15),
                sizeCell,sizeCell,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb16),
                sizeCell,sizeCell,false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb17),
                sizeCell,sizeCell,false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb18),
                sizeCell,sizeCell,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb19),
                sizeCell,sizeCell,false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb20),
                sizeCell,sizeCell,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb21),
                sizeCell,sizeCell, false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb22),
                sizeCell,sizeCell,false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb23),
                sizeCell,sizeCell, false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb24),
                sizeCell,sizeCell,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb25),
                sizeCell,sizeCell,false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb26),
                sizeCell,sizeCell, false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb27),
                sizeCell,sizeCell, false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb28),
                sizeCell,sizeCell,false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb29),
                sizeCell,sizeCell, false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb30),
                sizeCell,sizeCell,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb31),
                sizeCell,sizeCell, false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb32),
                sizeCell,sizeCell,  false));
        bitmapListLetterBoard.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.letb33),
                sizeCell,sizeCell,  false));
    }

    public void setStartPoint(){
        if (!orientation){
            int startX = (sizeScreen.x - sizeCell*NUMBER_CELL_IN_ROW) / 2;
            games.setStartPoint( new Point(startX,indent), new Point(indent, sizeScreen.y - indent));
            games.setIncPointX(startX);
            games.setIncPointY(sizeCell);
        }
        else {
            games.setStartPoint(new Point(0, indentY),new Point(sizeCell * NUMBER_CELL_IN_ROW + indent,
                    indentY*2));
            games.setIncPointX(0);
            games.setIncPointY(sizeCell);
        }
        setPictures();
        initBitmapListLetter();
        games.setSizeCell(sizeCell);
        games.startGame();
    }

    public void start(){
        if (!games.getFirstTurn()){
            games.addLetters();
            games.setListCellLetter();
            games.setFirstTurn(true);
        }
        invalidate();
    }

    public Point getSizeScreen() {
        return sizeScreen;
    }

    public void setOrientation(boolean orient) {

        orientation = orient;
    }

    public void setSizeScreen(Point screen){
        this.sizeScreen = screen;
    }

    public void newWord(){
        games.newWord(tempListLetter, tempListNumCells);
        tempListLetter.clear();
        tempListNumCells.clear();
        invalidate();
    }

    public void changeTurn(){
        games.changeTurn();
        invalidate();
    }

    public void changeTurnResetLetter(){
        if (tempListNumCells.isEmpty())
            games.changeTurnResetLetter();
        invalidate();
    }

    public GameField(Context context, Player player1, Player player2, int sizeCell) {
        super(context);
        this.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        this.setClickable(true);
        this.setOnTouchListener(new GameFieldListener(this));
        this.sizeCell = sizeCell;
        this.SIZE_CELL_LETTER = sizeCell + 5;
        matrix = new Matrix();
        games = new GameMechanic(player1,player2);
    }
    //Для восстановления поля
    public void resetGameField(@NotNull Bundle savedInstanceState){
        //Восстановление игроков
        games.getFirstPlayer().setName((String) savedInstanceState.get("firstPlayerName"));
        games.getSecondPlayer().setName((String) savedInstanceState.get("secondPlayerName"));
        games.getFirstPlayer().setScore((int) savedInstanceState.get("firstPlayerScore"));
        games.getSecondPlayer().setScore((int) savedInstanceState.get("secondPlayerScore"));

        games.setFirstTurn ((boolean) savedInstanceState.get("firstTurn"));

        if(games.getFirstTurn()) {
            games.getFirstPlayer().setListLetterAfterReset((char[]) savedInstanceState.get("firstPlayerLetter"));
            games.getSecondPlayer().setListLetterAfterReset((char[]) savedInstanceState.get("secondPlayerLetter"));
            games.setListCellLetter();
        }
        //восстановление переменных поля
        games.setTurn((boolean) savedInstanceState.get("turn"));
        games.setNextTurn((boolean) savedInstanceState.get("nextTurn"));
        //Восстановление элементов на поле
        games.setLetterInBoard((char []) savedInstanceState.get("letterInBoard"),
                (ArrayList<Integer>) savedInstanceState.get("indexLetterInBoard"));
        setSizeScreen(new Point ( (int) savedInstanceState.get("sizeX"), (int) savedInstanceState.get("sizeY")));
        //Восстановление оставшихся букв
        games.getGameRule().setCountFreeLetter( (ArrayList<Integer>) savedInstanceState.get("freeLetter"));
        invalidate();
    }

    public int getSizeCell(){
        return sizeCell;
    }
    public GameMechanic getGames(){
        return games;
    }
    public boolean getOrientation(){
        return orientation;
    }

    public boolean isTempListNumCells(){
        return tempListNumCells.isEmpty();
    }
    private void setPictures(){
        x2Letterpict = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.x2letter),
                sizeCell,sizeCell,false);
        x2Wordpict = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.x2word),
                sizeCell,sizeCell,false);
        x3Letterpict = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.x3letter),
                sizeCell,sizeCell,false);
        x3Wordpict = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.x3word),
                sizeCell,sizeCell,false);
        defpict = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.defpict),
                sizeCell,sizeCell,false);
        startpict = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.start),
                sizeCell,sizeCell,false);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
    public void updateField(int num){

        for(CellForLetter cell: games.getListCellLetter()){
            if (cell.isSelect()){
                listSelectLetter.add(cell.getNumCell());
            }
        }
        try{
            if (games.getNextTurn()){
                if (!games.getWorkListCells().get(num - 1).getIsLetter()) {
                    games.getWorkListCells().get(num - 1).setLetter(games.getFirstPlayer().getListLetter().get(listSelectLetter.get(0)));
                    tempListLetter.add(games.getFirstPlayer().getListLetter().get(listSelectLetter.get(0)));
                    games.getFirstPlayer().setFirstTap(num - 1);
                    tempListNumCells.add(num - 1);
                    games.getFirstPlayer().deleteLetter(listSelectLetter.get(0));
                    games.getListCellLetter().get(listSelectLetter.get(0)).changeSelect();
                }
            }
            else{
                if (!games.getWorkListCells().get(num - 1).getIsLetter()) {
                    games.getWorkListCells().get(num - 1).setLetter(games.getSecondPlayer().getListLetter().get(listSelectLetter.get(0)));
                    tempListLetter.add(games.getSecondPlayer().getListLetter().get(listSelectLetter.get(0)));
                    games.getSecondPlayer().setFirstTap(num - 1);
                    tempListNumCells.add(num - 1);
                    games.getSecondPlayer().deleteLetter(listSelectLetter.get(0));
                    games.getListCellLetter().get(listSelectLetter.get(0)).changeSelect();
                }
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
        try {
            for (int i = 0; i < COUNT_LETTER; i++){
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawPath(games.getListCellLetter().get(i).getPath(),paint);
                matrix.setTranslate(games.getListCellLetter().get(i).getStartPoint().x - 5,
                        games.getListCellLetter().get(i).getStartPoint().y - 5);
                List<Character> list = games.getNextTurn() ? games.getFirstPlayer().getListLetter() : games.getSecondPlayer().getListLetter();
                if (list.get(i) != ' ')
                    canvas.drawBitmap(bitmapListLetter.get(games.findLetter(list.get(i))),matrix,paint);
            }
        }catch (IndexOutOfBoundsException | NullPointerException e){
        }
        for (int i = 0; i < games.getWorkListCells().size(); i++){
            matrix.setTranslate(games.getWorkListCells().get(i).getStartPoint().x,
                    games.getWorkListCells().get(i).getStartPoint().y);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(games.getWorkListCells().get(i).getPath(), paint);
            if (games.getWorkListCells().get(i).getLetter() != ' '){
                canvas.drawBitmap(bitmapListLetterBoard.get(games.findLetter(games.getWorkListCells().get(i).getLetter())),matrix,paint);
            }
            else {
                switch (games.getWorkListCells().get(i).getState()) {
                    case DEF_CELL: {
                        canvas.drawBitmap(defpict, matrix, paint);
                        break;
                    }
                    case START_POSITION: {
                        canvas.drawBitmap(startpict, matrix, paint);
                        break;
                    }
                    case X2LETTER: {
                        canvas.drawBitmap(x2Letterpict, matrix, paint);
                        break;
                    }
                    case X3LETTER: {
                        canvas.drawBitmap(x3Letterpict, matrix, paint);
                        break;
                    }
                    case X2WORD: {
                        canvas.drawBitmap(x2Wordpict, matrix, paint);
                        break;
                    }
                    case X3WORD: {
                        canvas.drawBitmap(x3Wordpict, matrix, paint);
                        break;
                    }
                }
            }
        }
    }
}
