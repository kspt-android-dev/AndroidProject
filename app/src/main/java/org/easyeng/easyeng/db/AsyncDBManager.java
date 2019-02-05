package org.easyeng.easyeng.db;

import android.os.AsyncTask;

import org.easyeng.easyeng.db.entities.Word;
import org.easyeng.easyeng.db.entities.WordDAO;

import java.util.List;

import androidx.lifecycle.LiveData;


public class AsyncDBManager {

    private static AsyncDBManager instance;
    private static MyDatabase db;
    private static WordDAO wordDAO;
    private static LiveData<List<Word>> allWords;

    public static AsyncDBManager getInstance(MyDatabase database) {
        if (instance == null) {
            instance = new AsyncDBManager();
        }
        db = database;
        wordDAO = db.wordDAO();
        allWords = wordDAO.getAllWords();
        return instance;
    }

    public static AsyncDBManager getInstance() {
        if (db == null)
            throw new IllegalStateException("Firstly call method with parameter and set MyDatabase");
        return instance;
    }

    public void insert(Word... words) {
        new InsertWordAsync().execute(words);
    }

    public void insert(List<Word> words) {
        new InsertWordAsync().execute(words.toArray(new Word[0]));
    }

    public void delete(int id) {
        new DeleteWordAsync().execute(id);
    }

    public LiveData<List<Word>> getAllWords() {
        return allWords;
    }


    private static class InsertWordAsync extends AsyncTask<Word, Integer, Void> {
        @Override
        protected Void doInBackground(Word... words) {
            wordDAO.insertWords(words);
            return null;
        }
    }

    private static class DeleteWordAsync extends AsyncTask<Integer, Integer, Void> {
        @Override
        protected Void doInBackground(Integer... integers) {
            wordDAO.delete(integers[0]);
            return null;
        }
    }
}
