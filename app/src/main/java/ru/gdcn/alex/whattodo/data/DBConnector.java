package ru.gdcn.alex.whattodo.data;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import ru.gdcn.alex.whattodo.utilities.TextFormer;

public class DBConnector {
    private static final String TAG = "ToDO_Logger";
    private static final String className = "DBConnector";


    private static Connection connection = null;

    public static boolean initDB() {
        Log.d(TAG, TextFormer.getStartText(className) + "Инициализирую БД...");
        return connect() && checkTables();
    }

    private static boolean connect() {
        String url = "jdbc:sqlite:WhatToDO.db";
        try {
            Log.d(TAG, TextFormer.getStartText(className) + "Попытка установить соединение с БД...");
            connection = DriverManager.getConnection(url);
            Log.d(TAG, TextFormer.getStartText(className) + "Соединение с БД установленно!");
            return true;
        } catch (SQLException e) {
            Log.e(TAG, TextFormer.getStartText(className) + "Соединение с БД не установленно!");
            Log.e(TAG, TextFormer.getStartText(className) + e.toString());
            return false;
        }
    }

    private static boolean checkTables() {
        Log.d(TAG, TextFormer.getStartText(className) + "Проверка наличия таблицы...");

        String sql = "CREATE TABLE IF NOT EXISTS Notes (\n"
                + " id INTEGER PRIMARY KEY,\n"
                + " parent_id TINYTEXT NOT NULL,\n"
                + " header TINYTEXT,\n"
                + " text TEXT,\n"
                + " check_box BOOL NOT NULL,\n"
                + " notify BOOL NOT NULL,\n"
                + " date DATETIME,\n"
                + " fix BOOL,\n"
                + ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            Log.d(TAG, TextFormer.getStartText(className) + "Таблица существует и работает!");
        } catch (SQLException e) {
            Log.e(TAG, TextFormer.getStartText(className) + "Проблема доступа к таблице!");
            Log.e(TAG, TextFormer.getStartText(className) + e.toString());
            return false;
        }
        return true;
    }

    public static void insertData(int parentId, String header, String text,
                                  boolean checkBox, boolean notify, String date, boolean fix){
        Log.d(TAG, TextFormer.getStartText(className) + "Добавляем запись в таблицу...");
        String sql = "INSERT INTO Notes (parent_id, header, text, check_box, notify, date, fix) VALUES(?,?,?,?,?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, parentId);
            preparedStatement.setString(2, header);
            preparedStatement.setString(3, text);
            preparedStatement.setBoolean(4, checkBox);
            preparedStatement.setBoolean(5, notify);
            preparedStatement.setString(6, date);
            preparedStatement.setBoolean(7, fix);
            preparedStatement.executeUpdate();
            Log.d(TAG, TextFormer.getStartText(className) + "Запись добавлена!");
        } catch (SQLException e) {
            Log.e(TAG, TextFormer.getStartText(className) + "Не удалось добавить запись!");
            e.printStackTrace();
        }
    }

    public static void getData(int parentId){

    }


}
