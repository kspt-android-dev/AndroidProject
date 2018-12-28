package ru.alexandra.forum;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ru.alexandra.forum.data.DBConnector;
import ru.alexandra.forum.objects.User;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ThemeRecyclerAdapter themeRecyclerAdapter;

    private User user;

    private DBConnector dbConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbConnector = new DBConnector(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        NavigationView navigationView = findViewById(R.id.main_nav_menu);
        navigationView.setNavigationItemSelectedListener(this);

        initRecyclerView();

        user = (User) getIntent().getSerializableExtra(Constants.KEY_USER);
        if (user == null)
            finish();
        else {
            View headerLayout = navigationView.getHeaderView(0);
            ((TextView) headerLayout.findViewById(R.id.main_nav_menu_header_login)).setText(user.getName());
        }
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.main_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        themeRecyclerAdapter = new ThemeRecyclerAdapter(this, dbConnector);
        recyclerView.setAdapter(themeRecyclerAdapter);
    }

    @Override
    protected void onStart() {
        Log.d("LOGGER", "onStart");
        super.onStart();
        themeRecyclerAdapter.loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.main_menu_add:
                Intent intent = new Intent(this, CreationActivity.class);
                intent.putExtra(Constants.KEY_USER, user);
                startActivity(intent);
                return true;
            case android.R.id.home:
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.main_nav_menu_logout:
                finish();
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        dbConnector.close();
        super.onDestroy();
    }

    public User getUser() {
        return user;
    }
}
