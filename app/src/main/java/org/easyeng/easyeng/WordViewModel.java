package org.easyeng.easyeng;

import android.app.Application;

import org.easyeng.easyeng.db.AsyncDBManager;
import org.easyeng.easyeng.db.MyDatabase;
import org.easyeng.easyeng.db.entities.Word;
import org.easyeng.easyeng.db.entities.WordDAO;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class WordViewModel extends AndroidViewModel {

    private final WordDAO wordDAO;
    private final AsyncDBManager asyncDBManager;

    public WordViewModel(@NonNull Application application) {
        super(application);
        final MyDatabase myDatabase = MyDatabase.getInstance(application, null);
        asyncDBManager = AsyncDBManager.getInstance(myDatabase);
        wordDAO = myDatabase.wordDAO();
    }

    public void insert(Word... words) {
        asyncDBManager.insert(words);
    }

    public void insert(List<Word> words) {
        asyncDBManager.insert(words);
    }

    public void delete(int id) {
        asyncDBManager.delete(id);
    }

    public LiveData<List<Word>> getAllWords() {
        return asyncDBManager.getAllWords();
    }
}
