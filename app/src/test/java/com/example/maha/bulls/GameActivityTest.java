package com.example.maha.bulls;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
public class GameActivityTest {
    GameActivity activity = new GameActivity();

    boolean checkRandN(String randN){
        for (int i = 0; i < activity.COUNT_OF_NUMB; i++){
            String str = randN.substring(0, i) + randN.substring(i + 1, 4);
            if(str.contains(randN.substring(i, i + 1))) return false;
        }
        return true;
    }
    @Test
    public void getRandNumberTest() {
        assertEquals(true, checkRandN(activity.getRandNumber()));
        System.out.println(activity.getRandNumber());
    }
    @Test
    public void countOfCB() {
        assertEquals(2, activity.countB("9321", "9123"));
        assertEquals(2, activity.countC("9321", "9123"));
        assertEquals(3, activity.countB("0123", "9123"));
        assertEquals(0, activity.countC("0123", "9123"));
        assertEquals(4, activity.countB("0123", "0123"));
        assertEquals(0, activity.countC("0123", "0123"));
    }
}