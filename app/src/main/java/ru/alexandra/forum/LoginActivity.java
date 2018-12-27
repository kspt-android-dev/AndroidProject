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

    public static final String TAG = "LOGGER";

    EditText login, pass;
    Button signIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login_email);
        pass = findViewById(R.id.login_password);
        signIn = findViewById(R.id.login_sign_in);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (attemptLogin(login.getText().toString(), pass.getText().toString())){
                    User user = DBConnector.loadUser(LoginActivity.this, login.getText().toString());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean attemptLogin(String login, String pass) {
        User user = DBConnector.loadUser(this, login);
        if (user == null){
            return registration(login, pass);
        } else {
            return checkUserData(login, pass, user);
        }
    }

    private boolean checkUserData(String login, String pass, User user) {
        if (!login.equals(user.getName())) {
            Toast.makeText(this, "Неверный логин!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!pass.equals(user.getPass())) {
            Toast.makeText(this, "Неверный пароль!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean registration(String login, String pass) {
        if (login.equals("")) {
            Toast.makeText(this, "Поле логина не должно быть пустым!", Toast.LENGTH_SHORT).show();
            return false;
        }
        User user = new User(
                login,
                pass,
                "user",
                0,
                0
        );
        DBConnector.insertUser(this, user);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString("login", login.getText().toString());
        outState.putString("password", pass.getText().toString());
        super.onSaveInstanceState(outState, outPersistentState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        login.setText(savedInstanceState.getString("login"));
        pass.setText(savedInstanceState.getString("password"));
        super.onRestoreInstanceState(savedInstanceState);
    }
}

