package ru.alexandra.forum;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import ru.alexandra.forum.data.DBConnector;
import ru.alexandra.forum.objects.Answer;
import ru.alexandra.forum.objects.Theme;
import ru.alexandra.forum.objects.User;

public class ThemeActivity extends AppCompatActivity implements AnswerDialog.OnAddAnswerListener {



    private AnswerRecyclerAdapter answerRecyclerAdapter;
    private Theme theme;
    private User user;

    private DBConnector dbConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        dbConnector = new DBConnector(this);

        theme = (Theme) getIntent().getSerializableExtra(Constants.KEY_THEME);
        user = (User) getIntent().getSerializableExtra(Constants.KEY_USER);

        Toolbar toolbar = findViewById(R.id.theme_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(theme.getHeader());

        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.theme_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        answerRecyclerAdapter = new AnswerRecyclerAdapter(dbConnector, theme);
        recyclerView.setAdapter(answerRecyclerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        answerRecyclerAdapter.loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.theme_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.theme_menu_answer:
                new AnswerDialog().show(getSupportFragmentManager(), "answer");
                return true;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onAddAnswer(String text) {
        Answer answer = new Answer(
                theme.getId(),
                user.getId(),
                text,
                user
        );
        answerRecyclerAdapter.addAnswer(answer);
    }

    @Override
    protected void onDestroy() {
        dbConnector.close();
        super.onDestroy();
    }
}
