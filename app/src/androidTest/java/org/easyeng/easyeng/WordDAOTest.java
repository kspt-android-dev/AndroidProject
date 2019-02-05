package org.easyeng.easyeng;

import org.easyeng.easyeng.db.MyDatabase;
import org.easyeng.easyeng.db.entities.Word;
import org.easyeng.easyeng.db.entities.WordDAO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;


@RunWith(AndroidJUnit4.class)
public class WordDAOTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    private MyDatabase myDatabase;
    private WordDAO wordDAO;

    private final Word word1 = new Word(0, "Mother", "Мама", "1:4:67");
    private final Word word2 = new Word(1, "Father", "Папа", "2:22:54");

    @Before
    public void preparing() {
        myDatabase = Room.inMemoryDatabaseBuilder(mActivityRule.getActivity(), MyDatabase.class).build();
        wordDAO = myDatabase.wordDAO();
        myDatabase.clearAllTables();
    }

    @Test
    public void insertWordAndGetAllWordsTest() {
        wordDAO.insertWords(word1, word2);

        word1.setProgress(10);

        wordDAO.getAllWords().observe(mActivityRule.getActivity(), words -> {
            Assert.assertFalse(words.contains(word1));
            Assert.assertTrue(words.contains(word2));
        });
    }

    @Test
    public void insertWordAndGetByIdTest() {
        wordDAO.insertWords(word1);
        Assert.assertEquals(word1, wordDAO.getWordById(word1.getId()));
    }

    @Test
    public void getWordRepeatedBeforeTest() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, 10, 10);
        word1.setLastTraining(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, 5);

        word2.setLastTraining(calendar.getTime());
        wordDAO.insertWords(word1, word2);

        calendar.add(Calendar.DAY_OF_MONTH, -3);
        List<Word> words = wordDAO.getWordsRepeatedBefore(calendar.getTime());
        Assert.assertTrue(words.contains(word1));
        Assert.assertFalse(words.contains(word2));
    }

    @Test
    public void updateTest() {
        wordDAO.insertWords(word1, word2);

        word1.setProgress(100);

        wordDAO.update(word1);
        Assert.assertEquals(word1, wordDAO.getWordById(word1.getId()));

        Word newWord = new Word(10, word1.getWord(), word1.getTranslation(), word1.getExamples());
        newWord.setProgress(word1.getProgress());

        wordDAO.update(word2);

        Assert.assertEquals(wordDAO.getWordById(word2.getId()), word2);
        Assert.assertEquals(wordDAO.getWordById(word1.getId()), word1);
    }

    @Test
    public void deleteTest() {
        wordDAO.insertWords(word1, word2);

        wordDAO.getAllWords().observe(mActivityRule.getActivity(), words -> {
            Assert.assertTrue(words.contains(word1));
            Assert.assertTrue(words.contains(word2));
        });

        wordDAO.delete(word2.getId());

        wordDAO.getAllWords().observe(mActivityRule.getActivity(), words -> {
            Assert.assertTrue(words.contains(word1));
            Assert.assertFalse(words.contains(word2));
        });
    }

    @Test
    public void deleteAllTest() {
        wordDAO.insertWords(word1, word2);

        wordDAO.getAllWords().observe(mActivityRule.getActivity(), words -> {
            Assert.assertTrue(words.contains(word1));
            Assert.assertTrue(words.contains(word2));
        });

        wordDAO.deleteAll();

        wordDAO.getAllWords().observe(mActivityRule.getActivity(), words -> {
            Assert.assertTrue(words.isEmpty());
        });
    }

    @After
    public void after() {
        myDatabase.close();
    }
}
