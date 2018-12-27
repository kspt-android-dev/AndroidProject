package ru.polytech.course.pashnik.lines;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import ru.polytech.course.pashnik.lines.DataBase.Contact;
import ru.polytech.course.pashnik.lines.DataBase.DataBaseHandler;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DataBaseTests {

    private final static int TEST_COUNTER = 3;
    private final static int TEST_DEFAULT_SCORE = 246;
    private final static Contact DEFAULT_CONTACT = new Contact("Pavel", TEST_DEFAULT_SCORE);
    private final static Contact DEFAULT_CONTACT_2 = new Contact("Alex", TEST_DEFAULT_SCORE);

    private DataBaseHandler dataBaseHandler;

    @Before
    public void setUpDataBase() {
        dataBaseHandler = new DataBaseHandler(InstrumentationRegistry.getTargetContext());
    }

    @After
    public void finish() {
        deleteAll();
        dataBaseHandler.close();
    }

    @Test
    public void preConditions() {
        assertNotNull(dataBaseHandler);
    }

    @Test
    public void addContact() {
        // adding default contact 3 times
        for (int i = 0; i < TEST_COUNTER; i++) {
            dataBaseHandler.addContact(DEFAULT_CONTACT);
        }
        List<Contact> list = dataBaseHandler.getAllContacts();
        assertEquals(TEST_COUNTER, list.size());

        // checking the content of added contact
        for (int i = 0; i < TEST_COUNTER; i++) {
            assertEquals(DEFAULT_CONTACT, list.get(i));
        }
    }

    @Test
    public void deleteContact() {
        // testing of deleting first Contact from db
        dataBaseHandler.addContact(DEFAULT_CONTACT_2);
        dataBaseHandler.addContact(DEFAULT_CONTACT);

        dataBaseHandler.deleteContactById(1);
        List<Contact> list = dataBaseHandler.getAllContacts();

        assertEquals(1, list.size());
        assertEquals(DEFAULT_CONTACT, list.get(0));
    }

    @Test
    public void deleteAll() {
        // testing of deleting all contacts from db
        dataBaseHandler.addContact(DEFAULT_CONTACT);
        dataBaseHandler.addContact(DEFAULT_CONTACT);
        dataBaseHandler.deleteAll();
        assertTrue(dataBaseHandler.getAllContacts().isEmpty());
    }

}
