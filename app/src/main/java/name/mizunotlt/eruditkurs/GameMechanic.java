package name.mizunotlt.eruditkurs;

import android.graphics.Point;

import java.util.ArrayList;

import static name.mizunotlt.eruditkurs.State.DEF_CELL;
import static name.mizunotlt.eruditkurs.State.START_POSITION;
import static name.mizunotlt.eruditkurs.State.X2LETTER;
import static name.mizunotlt.eruditkurs.State.X2WORD;
import static name.mizunotlt.eruditkurs.State.X3LETTER;
import static name.mizunotlt.eruditkurs.State.X3WORD;

public class GameMechanic {

    private ArrayList<CellForLetter> listCellLetter = new ArrayList<>();
    private ArrayList<Cell> workListCells = new ArrayList<>();
    private ArrayList<Integer> listSelectLetter = new ArrayList<>();
    private ArrayList<Integer> indexLetterInBoard = new ArrayList<>();

    private int countCell = 0;
    private Player firstPlayer;
    private Player secondPlayer;
    private GameRules rule = new GameRules();
    private boolean nextTurn = true;
    private boolean turn = false;
    private boolean firstTurn = false;
    private final int COUNTCELL_X = 15;
    private final int COUNTCELL_Y = 15;
    private final int COUNT_LETTER = 7;
    private int sizeCell;
    private int incPoint;
    private int incPointX;
    private int incPointY;
    private Point startPoint;
    private Point startBorderLetterPoint;
    public String tc ="";


    public GameMechanic(Player player1, Player player2){
        setPlayers(player1,player2);
    }

    public void setPlayers(Player player1, Player player2){
        this.firstPlayer = player1;
        this.secondPlayer = player2;
    }

    public void addLetters(){
        rule.addLetters(firstPlayer);
        rule.addLetters(secondPlayer);
    }

    public void start(){
        if (!firstTurn){
            setListCellLetter();
            firstTurn = true;
        }
    }

    public void setSizeCell(int sizeCell){
        this.incPoint = sizeCell;
        this.sizeCell = sizeCell;
    }

    public void setListCellLetter(){
        for (int i = 0; i < COUNT_LETTER; i++){
            listCellLetter.add(i,new CellForLetter(new Point(startBorderLetterPoint.x, startBorderLetterPoint.y),i,sizeCell));
            //1.5 это коэффициент расстояния между буквами
            startBorderLetterPoint.x += 1.5 * sizeCell;
        }
    }

    public void setStartPoint(Point start, Point letterStart){
        this.startPoint = start;
        this.startBorderLetterPoint = letterStart;
    }

    public void setIncPointX(int x){
        this.incPointX = x;
    }
    public void setIncPointY(int y){
        this.incPointY = y;
    }

    public void newWord(ArrayList<Character> tempListLetter, ArrayList<Integer> tempListNumCells){
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
    }

    public void resetTurn(){

        turn = !turn;
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
    }

    public void changeTurnResetLetter(){
        for(CellForLetter cell: listCellLetter){
            if (cell.isSelect()){
                listSelectLetter.add(cell.getNumCell());
            }
        }
        if (nextTurn){
            rule.recycleLetter(firstPlayer, listSelectLetter);
            nextTurn = false;
        }
        else{
            rule.recycleLetter(secondPlayer, listSelectLetter);
            nextTurn = true;
        }
        if (listSelectLetter.size() != 0)
            for(Integer index: listSelectLetter){
                if (listCellLetter.get(index).isSelect()){
                    listCellLetter.get(index).changeSelect();
                }
            }
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
    public ArrayList<Integer> getIndexLetterInBoadr(){
        return indexLetterInBoard;
    }
    public void setLetterInBoard(char[] array, ArrayList<Integer> indexList){
        for(int num: indexList){
            workListCells.get(num).setLetter(array[num]);
        }
    }
    public void setTurn(boolean t){
        this.turn = t;
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
    public void setFirstTurn(boolean firstTurn){
        this.firstTurn = firstTurn;
    }

    //Инициализация клеток поля
    public void startGame(){
        templateOne();
        templateTwo();
        templateThree();
    }

    private void templateOne(){
        for (int i =0; i <= 4; i++){
            for (int j = 0; j < COUNTCELL_Y; j++){
                countCell++;
                Cell tempCell = new Cell(new Point(startPoint.x, startPoint.y),countCell,sizeCell);
                switch (i){
                    case 0 :{
                        switch (j){
                            case 0:{
                                tempCell.setState(X3WORD);
                                break;
                            }
                            case 3: {
                                tempCell.setState(X2LETTER);
                                break;
                            }
                            case 7: {
                                tempCell.setState(X3WORD);
                                break;
                            }
                            case 11: {
                                tempCell.setState(X2LETTER);
                                break;
                            }
                            case 14:{
                                tempCell.setState(X3WORD);
                                break;
                            }
                            default: {
                                tempCell.setState(DEF_CELL);
                                break;
                            }
                        }
                        break;
                    }
                    case 1 :{
                        switch (j){
                            case 1 : {
                                tempCell.setState(X2WORD);
                                break;
                            }
                            case 5 : {
                                tempCell.setState(X3LETTER);
                                break;
                            }
                            case 9 : {
                                tempCell.setState(X3LETTER);
                                break;
                            }
                            case 13 :{
                                tempCell.setState(X2WORD);
                                break;
                            }
                            default: {
                                tempCell.setState(DEF_CELL);
                                break;
                            }
                        }
                        break;
                    }
                    case 2 :{
                        switch (j){
                            case 2 : {
                                tempCell.setState(X2WORD);
                                break;
                            }
                            case 6 : {
                                tempCell.setState(X2LETTER);
                                break;
                            }
                            case 8 : {
                                tempCell.setState(X2LETTER);
                                break;
                            }
                            case 12: {
                                tempCell.setState(X2WORD);
                                break;
                            }
                            default: {
                                tempCell.setState(DEF_CELL);
                                break;
                            }
                        }
                        break;
                    }
                    case 3:{
                        switch (j){
                            case 0 : {
                                tempCell.setState(X2LETTER);
                                break;
                            }
                            case 3 : {
                                tempCell.setState(X2WORD);
                                break;
                            }
                            case 7 : {
                                tempCell.setState(X2LETTER);
                                break;
                            }
                            case 11 : {
                                tempCell.setState(X2WORD);
                                break;
                            }
                            case 14 : {
                                tempCell.setState(X2LETTER);
                                break;
                            }
                            default: {
                                tempCell.setState(DEF_CELL);
                                break;
                            }
                        }
                        break;
                    }
                    case 4 :{
                        switch (j){
                            case 4 : {
                                tempCell.setState(X2WORD);
                                break;
                            }
                            case 10 : {
                                tempCell.setState(X2WORD);
                                break;
                            }
                            default: {
                                tempCell.setState(DEF_CELL);
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
    private void templateTwo(){
        for (int i = 5; i < 10; i++){
            for (int j = 0 ; j < COUNTCELL_Y; j++){
                countCell++;
                Cell tempCell = new Cell(new Point(startPoint.x, startPoint.y),countCell,sizeCell);
                switch (i){
                    case 5 :{
                        switch (j){
                            case 1 : {
                                tempCell.setState(X3LETTER);
                                break;
                            }
                            case 13 :{
                                tempCell.setState(X3LETTER);
                                break;
                            }
                            default: {
                                tempCell.setState(DEF_CELL);
                                break;
                            }
                        }
                        break;
                    }
                    case 6 :{
                        switch (j){
                            case 2 : {
                                tempCell.setState(X2LETTER);
                                break;
                            }
                            case 6 : {
                                tempCell.setState(X2LETTER);
                                break;
                            }
                            case 8 : {
                                tempCell.setState(X2LETTER);
                                break;
                            }
                            case 12 : {
                                tempCell.setState(X2LETTER);
                                break;
                            }
                            default: {
                                tempCell.setState(DEF_CELL);
                                break;
                            }
                        }
                        break;
                    }
                    case 7 :{
                        switch (j){
                            case 0 : {
                                tempCell.setState(X3WORD);
                                break;
                            }
                            case 3 : {
                                tempCell.setState(X2LETTER);
                                break;
                            }
                            case 7 : {
                                tempCell.setState(START_POSITION);
                                break;
                            }
                            case 11: {
                                tempCell.setState(X2LETTER);
                                break;
                            }
                            case 14 : {
                                tempCell.setState(X2WORD);
                                break;
                            }
                            default: {
                                tempCell.setState(DEF_CELL);
                                break;
                            }
                        }
                        break;
                    }
                    case 8 :{
                        switch (j){
                            case 2 : {
                                tempCell.setState(X2LETTER);
                                break;
                            }
                            case 6 : {
                                tempCell.setState(X2LETTER);
                                break;
                            }
                            case 8 : {
                                tempCell.setState(X2LETTER);
                                break;
                            }
                            case 12 : {
                                tempCell.setState(X2LETTER);
                                break;
                            }
                            default: {
                                tempCell.setState(DEF_CELL);
                                break;
                            }
                        }
                        break;
                    }
                    case 9 :{
                        switch (j){
                            case 1 : {
                                tempCell.setState(X3LETTER);
                                break;
                            }
                            case 13 : {
                                tempCell.setState(X3LETTER);
                                break;
                            }
                            default: {
                                tempCell.setState(DEF_CELL);
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
                Cell tempCell = new Cell(new Point(startPoint.x, startPoint.y),countCell,sizeCell);
                switch (i){
                    case 10 :{
                        switch (j){
                            case 4 : {
                                tempCell.setState(X2WORD);
                                break;
                            }
                            case 10 : {
                                tempCell.setState(X2WORD);
                                break;
                            }
                            default: {
                                tempCell.setState(DEF_CELL);
                                break;
                            }
                        }
                        break;
                    }
                    case 11 :{
                        switch (j){
                            case 0 : {
                                tempCell.setState(X2LETTER);
                                break;
                            }
                            case 3 : {
                                tempCell.setState(X2WORD);
                                break;
                            }
                            case 7 : {
                                tempCell.setState(X2LETTER);
                                break;
                            }
                            case 11 : {
                                tempCell.setState(X2WORD);
                                break;
                            }
                            case 14 : {
                                tempCell.setState(X2LETTER);
                                break;
                            }
                            default: {
                                tempCell.setState(DEF_CELL);
                                break;
                            }
                        }
                        break;
                    }
                    case 12 :{
                        switch (j){
                            case 2 : {
                                tempCell.setState(X2WORD);
                                break;
                            }
                            case 6 : {
                                tempCell.setState(X2LETTER);
                                break;
                            }
                            case 8 : {
                                tempCell.setState(X2LETTER);
                                break;
                            }
                            case 12 : {
                                tempCell.setState(X2WORD);
                                break;
                            }
                            default: {
                                tempCell.setState(DEF_CELL);
                                break;
                            }
                        }
                        break;
                    }
                    case 13 :{
                        switch (j){
                            case 1 : {
                                tempCell.setState(X2WORD);
                                break;
                            }
                            case 5 : {
                                tempCell.setState(X3LETTER);
                                break;
                            }
                            case 9 : {
                                tempCell.setState(X3LETTER);
                                break;
                            }
                            case 13 : {
                                tempCell.setState(X2WORD);
                                break;
                            }
                            default: {
                                tempCell.setState(DEF_CELL);
                                break;
                            }
                        }
                        break;
                    }
                    case 14 :{
                        switch (j){
                            case 0 : {
                                tempCell.setState(X3WORD);
                                break;
                            }
                            case 3 : {
                                tempCell.setState(X2LETTER);
                                break;
                            }
                            case 7 : {
                                tempCell.setState(X3WORD);
                                break;
                            }
                            case 11 : {
                                tempCell.setState(X2LETTER);
                                break;
                            }
                            case 14 : {
                                tempCell.setState(X3WORD);
                                break;
                            }
                            default: {
                                tempCell.setState(DEF_CELL);
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

    public int findLetter(char let){
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

    public boolean getFirstTurn(){

        return firstTurn;
    }
}
