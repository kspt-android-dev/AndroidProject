package org.easyeng.easyeng.db;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import org.easyeng.easyeng.db.entities.Example;
import org.easyeng.easyeng.db.entities.Word;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class AsyncDBManager {

    private static AsyncDBManager instance;
    private static MyDatabase db;

    public static AsyncDBManager getInstance(MyDatabase database) {
        if (instance == null) {
            instance = new AsyncDBManager();
        }
        db = database;
        return instance;
    }

    public static AsyncDBManager getInstance() {
        if (db == null)
            throw new IllegalStateException("Firstly call method with parameter and set MyDatabase");
        return instance;
    }

    @SuppressLint("StaticFieldLeak")
    public Future<Void> saveWords(List<Word> words) {
        Future<Void> future = new CompletableFuture<>();
        new AsyncTask<Word, Integer, Void>() {
            @Override
            protected Void doInBackground(Word... words) {
                db.wordDAO().insertWords(words);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                ((CompletableFuture<Void>) future).complete(null);
            }
        }.execute(words.toArray(new Word[0]));
        return future;
    }

    @SuppressLint("StaticFieldLeak")
    public Future<List<Word>> loadAllWords() {
        Future<List<Word>> future = new CompletableFuture<>();
        new AsyncTask<Void, Integer, List<Word>>() {
            @Override
            protected List<Word> doInBackground(Void... voids) {
                final List<Word> words = db.wordDAO().getAllWords();
                return words;
            }

            @Override
            protected void onPostExecute(List<Word> words) {
                super.onPostExecute(words);
                ((CompletableFuture<List<Word>>) future).complete(words);
            }
        }.execute();
        return future;
    }

    public void saveExamples(List<Example> examples) {

    }

    public List<Example> loadExamples() {
        return null;
    }
}
