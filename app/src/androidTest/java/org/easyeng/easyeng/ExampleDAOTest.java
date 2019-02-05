package org.easyeng.easyeng;

import org.easyeng.easyeng.db.MyDatabase;
import org.easyeng.easyeng.db.entities.Example;
import org.easyeng.easyeng.db.entities.ExampleDAO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.room.Room;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class ExampleDAOTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    private MyDatabase myDatabase;
    private ExampleDAO exampleDAO;

    private Example example1 = new Example(0, "Hello, my name is Mike", "Привет, меня зовут Майк");
    private Example example2 = new Example(1, "It's cold today", "Сегодня прохладно");

    @Before
    public void preparing() {
        myDatabase = Room.inMemoryDatabaseBuilder(mActivityRule.getActivity(), MyDatabase.class).build();
        exampleDAO = myDatabase.exampleDAO();
        myDatabase.clearAllTables();
    }

    @Test
    public void insertAndGetByIdTest() {
        exampleDAO.insertExamples(example1, example2);
        Example exampleGot1 = exampleDAO.getExampleById(0);
        Example exampleGot2 = exampleDAO.getExampleById(1);
        Assert.assertEquals(example1, exampleGot1);
        Assert.assertEquals(example2, exampleGot2);
    }

    @Test
    public void deleteTest() {
        exampleDAO.insertExamples(example1, example2);
        exampleDAO.delete(example2.getId());
        Assert.assertEquals(example1, exampleDAO.getExampleById(0));
        Assert.assertNull(exampleDAO.getExampleById(1));
    }

    @Test
    public void deleteAllTest() {
        exampleDAO.insertExamples(example1, example2);
        exampleDAO.deleteAll();
        Assert.assertNull(exampleDAO.getExampleById(0));
        Assert.assertNull(exampleDAO.getExampleById(1));
    }

    @Test
    public void updateTest() {
        exampleDAO.insertExamples(example1);

        Example newExample1 = new Example(example1.getId(), example1.getEnglish(), "Привет, мое имя Майк");

        exampleDAO.update(newExample1);

        Assert.assertEquals(newExample1, exampleDAO.getExampleById(example1.getId()));
        Assert.assertNotEquals(example1, exampleDAO.getExampleById(example1.getId()));
    }

    @After
    public void after() {
        myDatabase.close();
    }
}
