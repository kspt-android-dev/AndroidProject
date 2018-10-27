package ru.gdcn.alex.whattodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String className = "MainActivity";

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_layout);

        fragmentManager = getSupportFragmentManager();


        NotesFragment notes = new NotesFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.main_space, notes)
                .commit();

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.nav_notes:
                                NotesFragment notes = new NotesFragment();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.main_space, notes)
                                        .commit();
                                item.setChecked(true);
                                break;
                            case R.id.nav_tasks:
                                TasksFragment tasks = new TasksFragment();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.main_space, tasks)
                                        .commit();
                                item.setChecked(true);
                                break;
                            case R.id.nav_calendar:
                                CalendarFragment calendarFragment = new CalendarFragment();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.main_space, calendarFragment)
                                        .commit();
                                item.setChecked(true);
                                break;
                        }
                        return false;
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_right_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.top_right_menu_settings:
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.top_right_menu_about:
                Toast.makeText(getApplicationContext(), "Нажали О программе.", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void clickCreate(View v){
        Toast.makeText(this, "Click create!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, CreationActivity.class);
        startActivity(intent);
//        FrameLayout frameLayout = findViewById(R.id.main_space);
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
//                100,
//                100);
//        layoutParams.setMargins(0, 0, 0, (int) (140 / getApplicationContext().getResources().getDisplayMetrics().density));
//        layoutParams.gravity = Gravity.BOTTOM|Gravity.CENTER;
    }
}
