package com.julia.tag;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.julia.tag.records.AppDataBase;
import com.julia.tag.records.Record;
import com.julia.tag.records.RecordDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DBTests {

    private AppDataBase dataBase;
    private RecordDao recordDao;

    @Before
    public void createDb() throws Exception {
        dataBase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                AppDataBase.class)
                .build();
        recordDao = dataBase.recordDao();

    }

    @Test
    public void allDBMethodsTests(){
        Record record1 = new Record();
        record1.name = "Test1";
        record1.moves = 100;
        recordDao.insert(record1);
        assertEquals(record1.name, recordDao.getAll().get(0).name);
        assertEquals(record1.moves, recordDao.getAll().get(0).moves);

        Record record2 = new Record();
        record2.name = "Test2";
        record2.moves = 200;
        recordDao.insert(record2);
        assertEquals(record2.name, recordDao.getAll().get(1).name);
        assertEquals(record2.moves, recordDao.getAll().get(1).moves);

        Record record3 = new Record();
        record3.name = "Test3";
        record3.moves = 50;
        recordDao.insert(record3);
        assertEquals(record3.name, recordDao.getAll().get(0).name);
        assertEquals(record3.moves, recordDao.getAll().get(0).moves);

        Record record4 = new Record();
        record4.name = "Test4";
        record4.moves = 51;
        recordDao.insert(record4);
        assertEquals(record4.name, recordDao.getAll().get(1).name);
        assertEquals(record4.moves, recordDao.getAll().get(1).moves);

        assertEquals(4, recordDao.getAll().size());

        recordDao.deleteAll();
        assertEquals(0, recordDao.getAll().size());
    }


    @After
    public void closeDb() throws Exception {
        dataBase.close();
    }

}
