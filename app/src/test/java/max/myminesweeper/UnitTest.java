package max.myminesweeper;

import android.graphics.Point;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;


import java.util.ArrayList;

@Config(sdk = 18)
@RunWith(RobolectricTestRunner.class)
public class UnitTest {

    // --------------------------------------------------------------

    // cell test
    @Test
    public void containsPointTest() {
        Cell cell = new Cell(new Point(5, 5), 50);
        Assert.assertTrue(cell.containsPoint(new Point(5, 5)));
        Assert.assertTrue(cell.containsPoint(new Point(50, 50)));
        Assert.assertTrue(cell.containsPoint(new Point(25, 25)));
        Assert.assertFalse(cell.containsPoint(new Point(51, 0)));
        Assert.assertFalse(cell.containsPoint(new Point(0, 0)));
    }

    // --------------------------------------------------------------

    // logic tests
    private MainActivity mainActivity;
    private ArrayList<Integer> assertionArray;
    private Logic logic;

    @Before
    public void setMainActivity() {
        mainActivity = Robolectric.setupActivity(MainActivity.class);
        assertionArray = new ArrayList<>();
        logic = new Logic(mainActivity);
    }

    private MyViewModel setViewModel(int event) {

        //  field:
        //  (empty)  (mine)
        //  (mine)   (empty)

        MyViewModel myViewModel = new MyViewModel();

        switch (event) {
            case 1: {
                myViewModel.setCondition(Logic.Condition.NOT_MINED);
                myViewModel.setTotalOpenedCells(0);
                myViewModel.setNumberOfMines(2);
            }

            case 2: {
                myViewModel.setCondition(Logic.Condition.NOT_MINED);
                myViewModel.setNumberOfMines(2);
                myViewModel.setTotalOpenedCells(0);
            }
        }

        myViewModel.setCellSide(100);
        myViewModel.setFieldWidth(2);
        myViewModel.setFieldHeight(2);

        return myViewModel;
    }

    // --------------------------------------------------------------

    @Test
    public void miningTest() {

        MyViewModel viewModel = setViewModel(1);
        viewModel.setCondition(Logic.Condition.NOT_MINED);
        logic.update(viewModel);

        logic.openCell(new Point(150, 50));
        int count = 0;
        for (Cell cell : logic.getCells()) {
            if (cell.getNeighbourMines() == Cell.MINE) {
                count++;
            }
        }
        Assert.assertEquals(2, count);
    }

    @Test
    public void openCellTest() {

        MyViewModel viewModel = setViewModel(2);
        logic.update(viewModel);
        logic.setCondition(Logic.Condition.PROCESSING);
        logic.getCells().get(0).setNeighbourMines(2);
        logic.getCells().get(3).setNeighbourMines(2);
        logic.getCells().get(1).setNeighbourMines(Cell.MINE);
        logic.getCells().get(2).setNeighbourMines(Cell.MINE);

        // opens empty cell = continue
        assertionArray.clear();
        logic.openCell(new Point(50, 50));
        assertionArray.add(0);
        Assert.assertEquals(assertionArray, logic.getOpenCells());

        // opens mine = lost
        assertionArray.clear();
        logic.openCell(new Point(150, 50));
        assertionArray.add(1);
        assertionArray.add(2);
        Assert.assertEquals(assertionArray, logic.getCellsWithMine());
        Assert.assertEquals(Logic.Condition.LOST, logic.getCondition());
    }

    // --------------------------------------------------------------

    // view model updating test
    @Test
    public void updateTest() {

        MyViewModel viewModel = setViewModel(1);
        viewModel.setCondition(Logic.Condition.NOT_MINED);
        logic.update(viewModel);

        assertionArray.clear();
        Assert.assertEquals(Logic.Condition.NOT_MINED, logic.getCondition());
        Assert.assertEquals(100, logic.getCellSide());
        Assert.assertEquals(2, logic.getFieldWidth());
        Assert.assertEquals(2, logic.getFieldHeight());
        Assert.assertEquals(assertionArray, logic.getCellsWithMine());
        Assert.assertEquals(assertionArray, logic.getOpenCells());

        logic.update(newViewModel());

        Assert.assertEquals(10, logic.getFieldHeight());
        Assert.assertEquals(10, logic.getFieldWidth());
        Assert.assertEquals(50, logic.getCellSide());

        assertionArray.clear();
        Assert.assertEquals(assertionArray, logic.getCellsWithMine());

        assertionArray.clear();
        assertionArray.add(1);
        Assert.assertEquals(assertionArray, logic.getOpenCells());
        Assert.assertEquals(1, logic.getTotalOpenCells());
    }

    private MyViewModel newViewModel() {
        MyViewModel viewModel = new MyViewModel();

        viewModel.setCondition(Logic.Condition.NOT_MINED);
        viewModel.setTotalOpenedCells(1);

        ArrayList<Integer> openCells = new ArrayList<>();
        openCells.add(1);
        viewModel.setOpenCells(openCells);

        viewModel.setCellSide(50);
        viewModel.setNumberOfMines(2);
        viewModel.setFieldWidth(10);
        viewModel.setFieldHeight(10);

        return viewModel;
    }

}
