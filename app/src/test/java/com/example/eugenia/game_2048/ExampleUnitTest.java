package com.example.eugenia.game_2048;


import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
//    Matrix matrix;
//    @Before
//    public void setUp() {
//        Matrix matrix = new Matrix();
//        matrix.m =new int[][]  {    {0, 0, 0, 0 },
//                {0, 2, 0, 0 },
//                {0, 2, 2, 0 },
//                {0, 0, 0, 0 } };
//    }

    @Test
    public void upMatrix() {
        Matrix matrix = new Matrix();
        matrix.m =new int[][]  {    {0, 0, 0, 0 },
                {0, 2, 0, 0 },
                {0, 2, 2, 0 },
                {0, 0, 0, 0 } };
        int[][] expectedUp;
        expectedUp = new int[][]{    {0, 4, 2, 0 },
                {0, 0, 0, 0 },
                {0, 0, 0, 0 },
                {0, 0, 0, 0 } };

        matrix.up();
        System.out.println();
        assertArrayEquals(expectedUp,matrix.m);

    }
    @Test
    public void rightMatrix() {
        Matrix matrix = new Matrix();
        matrix.m =new int[][]  {    {0, 0, 0, 0 },
                {0, 2, 0, 0 },
                {0, 2, 2, 0 },
                {0, 0, 0, 0 } };

        int[][]expectedRight;
        expectedRight = new int[][]{    {0, 0, 0, 0 },
                {0, 0, 0, 2 },
                {0, 0, 0, 4},
                {0, 0, 0, 0 } };

        matrix.right();
        System.out.println();
        assertArrayEquals(expectedRight,matrix.m);

    }


}