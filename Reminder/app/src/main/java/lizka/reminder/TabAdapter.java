package lizka.reminder;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    /**
     * @param fm
     * @deprecated
     */
    public TabAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }


    @Override
    public Fragment getItem(int i) {

        switch (i){
            case 0:
                return new CurrentTaskFragment();

            case 1:
                return new DoneTaskFragment();

            default:
                return null;
        }


    }
}
