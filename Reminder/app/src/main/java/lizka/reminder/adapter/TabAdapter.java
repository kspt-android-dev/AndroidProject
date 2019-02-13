package lizka.reminder.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import lizka.reminder.fragment.CurrentTaskFragment;
import lizka.reminder.fragment.DoneTaskFragment;

public class TabAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;
    public static final int CURRENT_TASK_FRAGMENT_POSITION = 0;
    public static final int DONE_TASK_FRAGMENT_POSITION = 1;

    private CurrentTaskFragment currentTaskFragment;
    private DoneTaskFragment doneTaskFragment;

    /**
     * @param fm
     * @deprecated
     */
    public TabAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        currentTaskFragment = new CurrentTaskFragment();
        doneTaskFragment = new DoneTaskFragment();
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }


    @Override
    public Fragment getItem(int i) {

        switch (i){
            case 0:
                return currentTaskFragment;

            case 1:
                return doneTaskFragment;

            default:
                return null;
        }


    }
}
