package org.easyeng.easyeng;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseArray;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    private SparseArray<Fragment> fragmentMap;
    private SharedPreferences mPrefs;
    private String CURRENT_FRAGMENT = "current_fragment";
    private int currentFragment = R.layout.fragment_home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentMap = new SparseArray<>(3);
        fragmentMap.put(R.layout.fragment_home, new HomeFragment());
        fragmentMap.put(R.layout.fragment_puzzles, new PuzzlesFragment());
        fragmentMap.put(R.layout.fragment_words, new WordsFragment());

        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_menu);

        //Get current Screen
        mPrefs = getSharedPreferences("details", 0);
        currentFragment = mPrefs.getInt(CURRENT_FRAGMENT, R.layout.fragment_home);
        //Set selected item in the bottom navigation menu
        int selectedItemId = R.id.action_home;
        if (currentFragment == R.layout.fragment_puzzles)
            selectedItemId = R.id.action_puzzles;
        else if (currentFragment == R.layout.fragment_words)
            selectedItemId = R.id.action_words;

        bottomNavigationView.setSelectedItemId(selectedItemId);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragments_container, fragmentMap.get(currentFragment))
                .commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            final int menu_item_id = item.getItemId();
            Fragment fragment = null;
            switch (menu_item_id) {
                case R.id.action_home:
                    fragment = fragmentMap.get(R.layout.fragment_home);
                    currentFragment = R.layout.fragment_home;
                    break;
                case R.id.action_puzzles:
                    fragment = fragmentMap.get(R.layout.fragment_puzzles);
                    currentFragment = R.layout.fragment_puzzles;
                    break;
                case R.id.action_words:
                    fragment = fragmentMap.get(R.layout.fragment_words);
                    currentFragment = R.layout.fragment_words;
                    break;
            }

            return loadFragment(fragment);
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment == null) return false;
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.appearing, R.animator.hiding, R.animator.appearing, R.animator.hiding)
                .replace(R.id.fragments_container, fragment, fragment.getTag())
                .commit();
        return true;
    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putInt(CURRENT_FRAGMENT, currentFragment);
        editor.apply();
    }
}