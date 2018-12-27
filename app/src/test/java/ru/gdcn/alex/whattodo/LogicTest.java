package ru.gdcn.alex.whattodo;

import android.content.Context;
import android.content.Intent;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import ru.gdcn.alex.whattodo.creation.CreationManager;
import ru.gdcn.alex.whattodo.data.DBConnector;
import ru.gdcn.alex.whattodo.objects.Note;

import static org.mockito.Mockito.*;

public class LogicTest {

//    @Mock
//    Context fakeContext;
//
//    @Mock
//    Intent fakeIntent;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetNote() {
        CreationManager creationManager = mock(CreationManager.class);
        for (long i = 1; i < 100; i++) {
            Note note = new Note(
                    1,
                    "Test" + i,
                    "test",
                    "list",
                    null,
                    0,
                    0);
            when(creationManager.getNote()).thenReturn(note);
            Assert.assertEquals(note, creationManager.getNote());
        }
    }

//    @Test
//    public void testDB(){
//       PowerMockito.mockStatic(DBConnector.class);
//        for (long i = 1; i < 100; i++) {
//            Note note = new Note(
//                    1,
//                    "Test" + i,
//                    "test",
//                    "list",
//                    null,
//                    0,
//                    0);
//            PowerMockito.doReturn(i).when(DBConnector.insertNote(fakeContext, note));
//            CreationManager creationManager = mock(CreationManager.class);
//
//            creationManager.init(fakeIntent);
//            PowerMockito.verifyStatic();

//            PowerMockito.when(DBConnector.insertNote(fakeContext, note)).thenReturn(i);
//            Assert.assertEquals(i, DBConnector.insertNote(fakeContext, note));
//        }
//    }
}
