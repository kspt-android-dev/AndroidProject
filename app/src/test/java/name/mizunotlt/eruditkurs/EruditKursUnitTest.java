package name.mizunotlt.eruditkurs;

import android.graphics.Point;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class EruditKursUnitTest {
    Player testPlayer1 = new Player("ЖОРА");
    Player testPlayer2 = new Player("ГОША");
    GameMechanic mechanicTest = new GameMechanic(testPlayer1,testPlayer2);
    GameRules rules = new GameRules();

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    //Тестирование класса Player
    @Test
    public void addWord(){
        assertEquals("ADD", testPlayer1.addWord("путь"));
        assertEquals("ADD", testPlayer1.addWord("бутылка"));
        assertEquals("ADD", testPlayer1.addWord("хрен"));
        assertEquals("ADD", testPlayer1.addWord("коза"));
        assertEquals("ADD", testPlayer1.addWord("ад"));
        assertEquals("ERROR", testPlayer1.addWord("путь"));
        assertEquals("ERROR", testPlayer1.addWord("хрен"));

    }
    @Test
    public void setAndGetListLetter(){
        List<Character> testList1 = new ArrayList<>();
        testList1.add('А');
        testList1.add('А');
        testList1.add('П');
        testList1.add('А');
        testList1.add('Л');
        testList1.add('Б');
        testList1.add('А');

        List<Character> testList2 = new ArrayList<>();
        testList2.add('А');
        testList2.add('с');
        testList2.add('о');
        testList2.add('А');
        testList2.add('Л');
        testList2.add('с');
        testList2.add('е');

        List<Character> testList3 = new ArrayList<>();
        testList3.add('А');
        testList3.add('*');
        testList3.add('П');
        testList3.add('И');
        testList3.add('Л');
        testList3.add('Я');
        testList3.add('П');
        testPlayer1.setListLetter(testList1);
        testPlayer2.setListLetter(testList2);

        assertEquals(testList1,testPlayer1.getListLetter());
        assertEquals(testList2,testPlayer2.getListLetter());
        testPlayer1.setListLetter(testList3);
        assertEquals(testList3,testPlayer1.getListLetter());

    }
    @Test
    public void addDeleteLetterTest(){
        List<Character> testList1 = new ArrayList<>();
        testList1.add('А');
        testList1.add('П');
        testList1.add('А');
        testList1.add('Л');
        testList1.add('Б');
        testList1.add('А');
        testList1.add('Ц');

        testPlayer1.setListLetter(testList1);
        testPlayer1.deletLetter(0);
        testList1.remove(0);
        assertEquals(testList1, testPlayer1.getListLetter());
        assertEquals(6, testPlayer1.getCountLetter());
        testPlayer1.addLetter(0, 'Б');
        testList1.add(0, 'Б');
        assertEquals(testList1, testPlayer1.getListLetter());
        assertEquals(7, testPlayer1.getCountLetter());
    }

    @Test
    public void checkWord(){
        String word1 = "КАПУСТА";
        String word2 = "ДУБ";
        String word3 = "КАПУС";
        String word4 = "ЧАЙ";
        String word5 = "ЧАщшд";
        String word6 = "БЕГАТЬ";
        CheckWordImp checker1 =  new CheckWordImp(word1);
        CheckWordImp checker2 =  new CheckWordImp(word2);
        CheckWordImp checker3 =  new CheckWordImp(word3);
        CheckWordImp checker4 =  new CheckWordImp(word4);
        CheckWordImp checker5 =  new CheckWordImp(word5);
        CheckWordImp checker6 =  new CheckWordImp(word6);

        try {
            assertEquals(true,checker1.checkCorrectWord(checker1.connectYandexDicApi(checker1.buildUrl())));
            assertEquals(true,checker2.checkCorrectWord(checker2.connectYandexDicApi(checker2.buildUrl())));
            assertEquals(false,checker3.checkCorrectWord(checker3.connectYandexDicApi(checker3.buildUrl())));
            assertEquals(true,checker4.checkCorrectWord(checker4.connectYandexDicApi(checker4.buildUrl())));
            assertEquals(false,checker5.checkCorrectWord(checker5.connectYandexDicApi(checker5.buildUrl())));
            assertEquals(false,checker6.checkCorrectWord(checker6.connectYandexDicApi(checker6.buildUrl())));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void resetPlayer(){
        List<Character> testList1 = new ArrayList<>();
        testList1.add('А');
        testList1.add('П');
        testList1.add('А');
        testList1.add('Л');
        testList1.add('Б');
        testList1.add('А');
        testList1.add('Ц');
        testPlayer1.setListLetter(testList1);
        rules.resetLetter(testPlayer1);
        assertEquals(7, testPlayer1.getCountLetter());
    }

    @Test
    public void testField(){
        int sizeCell = 70;
        mechanicTest.setSizeCell(sizeCell);
        mechanicTest.setIncPointX(15);
        mechanicTest.setIncPointY(70);
        mechanicTest.setStartPoint(new Point(15,100), new Point (1900,100));
        mechanicTest.startGame();

        assertEquals(6, mechanicTest.getWorkListCells().get(0).typeCell());
        assertEquals(1, mechanicTest.getWorkListCells().get(1).typeCell());
        assertEquals(3, mechanicTest.getWorkListCells().get(3).typeCell());
        assertEquals(5, mechanicTest.getWorkListCells().get(16).typeCell());
        assertEquals(4, mechanicTest.getWorkListCells().get(20).typeCell());
        assertEquals(2, mechanicTest.getWorkListCells().get(112).typeCell());
    }

    @Test
    public void tetsAddLetterToBoard(){
        List<Character> testList1 = new ArrayList<>();
        testList1.add('Б');
        testList1.add('А');
        testList1.add('Й');
        testList1.add('К');
        testList1.add('А');
        testList1.add('А');
        testList1.add('Ц');
        testPlayer1.setListLetter(testList1);
        mechanicTest.setPlayers(testPlayer1,testPlayer2);
        int sizeCell = 70;
        mechanicTest.setSizeCell(sizeCell);
        mechanicTest.setIncPointX(15);
        mechanicTest.setIncPointY(70);
        mechanicTest.setStartPoint(new Point(15,100), new Point (1900,100));
        mechanicTest.startGame();

        ArrayList<Character> tempListLetter = new ArrayList<>();
        ArrayList<Integer> tempNumCellsLetter = new ArrayList<>();

        mechanicTest.getFirstPlayer().setFirstTap(112);
        mechanicTest.getWorkListCells().get(112).setLetter(testList1.get(0));
        mechanicTest.getWorkListCells().get(113).setLetter(testList1.get(1));
        mechanicTest.getWorkListCells().get(114).setLetter(testList1.get(2));
        mechanicTest.getWorkListCells().get(115).setLetter(testList1.get(3));
        mechanicTest.getWorkListCells().get(116).setLetter(testList1.get(4));
        tempListLetter.add(testList1.get(0));
        tempListLetter.add(testList1.get(1));
        tempListLetter.add(testList1.get(2));
        tempListLetter.add(testList1.get(3));
        tempListLetter.add(testList1.get(4));
        tempNumCellsLetter.add(112);
        tempNumCellsLetter.add(113);
        tempNumCellsLetter.add(114);
        tempNumCellsLetter.add(115);
        tempNumCellsLetter.add(116);
        mechanicTest.getFirstPlayer().deletLetter(0);
        mechanicTest.getFirstPlayer().deletLetter(1);
        mechanicTest.getFirstPlayer().deletLetter(2);
        mechanicTest.getFirstPlayer().deletLetter(3);
        mechanicTest.getFirstPlayer().deletLetter(4);

        StringBuilder sb = new StringBuilder();
        sb.append(mechanicTest.getWorkListCells().get(112).getLetter());
        sb.append(mechanicTest.getWorkListCells().get(113).getLetter());
        sb.append(mechanicTest.getWorkListCells().get(114).getLetter());
        sb.append(mechanicTest.getWorkListCells().get(115).getLetter());
        sb.append(mechanicTest.getWorkListCells().get(116).getLetter());


        CheckWordImp checker =  new CheckWordImp(sb.toString());
        try {
            assertTrue(checker.checkCorrectWord(checker.connectYandexDicApi(checker.buildUrl())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals("БАЙКА", sb.toString());
    }
}