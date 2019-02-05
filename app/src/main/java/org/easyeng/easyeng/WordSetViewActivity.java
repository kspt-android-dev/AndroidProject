package org.easyeng.easyeng;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.easyeng.easyeng.db.MyDatabase;
import org.easyeng.easyeng.db.entities.Word;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class WordSetViewActivity extends AppCompatActivity {
    private Item item;
    private List<Word> words;
    private MyDatabase myDatabase;
    private WordViewModel wordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

        super.onCreate(savedInstanceState);
        item = getIntent().getParcelableExtra(getString(R.string.cart_extra_name));
        setContentView(R.layout.words_set_activity);
        ((TextView) findViewById(R.id.title)).setText(item.getTitle());
        ((WebView) findViewById(R.id.web_view)).loadData(item.getText(), "text/html", "utf-8");

        final List<Integer> indices = item.getWords();
        final Button button = findViewById(R.id.add_words);

        CollectionReference collectionReference = FirebaseFirestore.getInstance()
                .collection("Words");

        for (Integer index : indices) {
            collectionReference.whereEqualTo("id", index);
        }

        collectionReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot result = task.getResult();
                if (result == null) return;
                this.words = result.toObjects(Word.class);
                button.setOnClickListener(v -> {
                    wordViewModel.insert(this.words);
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
