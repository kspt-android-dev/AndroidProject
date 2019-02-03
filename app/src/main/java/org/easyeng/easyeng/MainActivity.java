package org.easyeng.easyeng;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SparseArray<Fragment> fragmentMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentMap = new SparseArray<>(3);
        fragmentMap.put(R.layout.fragment_home, new HomeFragment());
        fragmentMap.put(R.layout.fragment_puzzles, new PuzzlesFragment());
        fragmentMap.put(R.layout.fragment_words, new WordsFragment());

        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_menu);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragments_container,fragmentMap.get(R.layout.fragment_home))
                .commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            final int menu_item_id = item.getItemId();

            Toast.makeText(this, String.valueOf(menu_item_id), Toast.LENGTH_LONG).show();
            Fragment fragment = null;
            switch (menu_item_id) {
                case R.id.action_home:
                    fragment = fragmentMap.get(R.layout.fragment_home);
                    break;
                case R.id.action_puzzles:
                    fragment = fragmentMap.get(R.layout.fragment_puzzles);
                    break;
                case R.id.action_words:
                    fragment = fragmentMap.get(R.layout.fragment_words);
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
                .replace(R.id.fragments_container, fragment)
//                .addToBackStack(null)
                .commit();
        return true;
    }
}
