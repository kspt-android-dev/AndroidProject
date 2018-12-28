package ru.alexandra.forum;


import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ru.alexandra.forum.data.DBConnector;
import ru.alexandra.forum.objects.User;

public class LoginActivity extends AppCompatActivity{

    EditText login, pass;
    Button signIn;

    private DBConnector dbConnector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbConnector = new DBConnector(this);

        login = findViewById(R.id.login);
        pass = findViewById(R.id.password);
        signIn = findViewById(R.id.login_sign_in);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (attemptLogin(login.getText().toString(), pass.getText().toString())){
                    User user = dbConnector.loadUser(login.getText().toString());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra(Constants.KEY_USER, user);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean attemptLogin(String login, String pass) {
        User user = dbConnector.loadUser(login);
        if (user == null){
            return registration(login, pass);
        } else {
            return checkUserData(login, pass, user);
        }
    }

    private boolean checkUserData(String login, String pass, User user) {
        if (!login.equals(user.getName())) {
            Toast.makeText(this, getResources().getString(R.string.wrong_login), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!pass.equals(user.getPass())) {
            Toast.makeText(this, getResources().getString(R.string.wrong_password), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean registration(String login, String pass) {
        if (login.equals("")) {
            Toast.makeText(this, getResources().getString(R.string.login_empty), Toast.LENGTH_SHORT).show();
            return false;
        }
        User user = new User(
                login,
                pass,
                0,
                0
        );
        dbConnector.insertUser(user);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString(Constants.KEY_LOGIN, login.getText().toString());
        outState.putString(Constants.KEY_PASS, pass.getText().toString());
        super.onSaveInstanceState(outState, outPersistentState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        login.setText(savedInstanceState.getString(Constants.KEY_LOGIN));
        pass.setText(savedInstanceState.getString(Constants.KEY_PASS));
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        dbConnector.close();
        super.onDestroy();
    }
}

