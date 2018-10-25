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
//        Toast.makeText(this, "Click create notes!", Toast.LENGTH_SHORT).show();
        FrameLayout frameLayout = findViewById(R.id.main_space);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                100,
                100);
        layoutParams.setMargins(0, 0, 0, (int) (140 / getApplicationContext().getResources().getDisplayMetrics().density));
        layoutParams.gravity = Gravity.BOTTOM|Gravity.CENTER;
        Button n = new Button(this),
                t = new Button(this),
                l = new Button(this);
        Button c = findViewById(R.id.create_button);


        Animation n_a = AnimationUtils.loadAnimation(this, R.anim.n_trans),
                t_a = AnimationUtils.loadAnimation(this, R.anim.t_trans);
        Animation c_a = AnimationUtils.loadAnimation(this, R.anim.c_scale);

        frameLayout.addView(n);
        frameLayout.addView(t);
        frameLayout.addView(l);

//        n.setGravity(Gravity.BOTTOM & Gravity.CENTER);
        n.setLayoutParams(layoutParams);
        n.setBackground(getDrawable(R.drawable.ic_button));
        n.setText("n");
        n.setTextSize(10);
        n.setGravity(Gravity.CENTER);
        n.startAnimation(n_a);

//        t.setGravity(Gravity.BOTTOM & Gravity.CENTER);
        t.setLayoutParams(layoutParams);
        t.setBackground(getDrawable(R.drawable.ic_button));
        t.setText("t");
        t.setTextSize(10);
        t.setGravity(Gravity.CENTER);
        t.startAnimation(t_a);

//        l.setGravity(Gravity.BOTTOM & Gravity.CENTER);
        l.setLayoutParams(layoutParams);
        l.setBackground(getDrawable(R.drawable.ic_button));
        l.setText("l");
        l.setTextSize(10);
        t.setGravity(Gravity.CENTER);
        c.startAnimation(c_a);
    }
}
