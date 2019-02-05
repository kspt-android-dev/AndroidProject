package org.easyeng.easyeng.db.entities;

import com.google.firebase.database.annotations.NotNull;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "words")
public class Word {

    @PrimaryKey
    private int id;
    private int progress;
    @NotNull
    private Date lastTraining;
    @NotNull
    private String word;
    @NotNull
    private String translation;
    @NotNull //Save examples like "1:2:123:34" separating by ':'
    private String examples;

    public Word() {
        //Because Firestore wants it
    }

    public Word(int id, @NotNull String word,
                @NotNull String translation, @NotNull String examples) {
        this.id = id;
        this.progress = 0;
        this.lastTraining = new Date(0);
        this.word = word;
        this.translation = translation;
        this.examples = examples;
    }

    public int getId() {
        return id;
    }

    public int getProgress() {
        return progress;
    }

    public Date getLastTraining() {
        return lastTraining;
    }

    @NotNull
    public String getWord() {
        return word;
    }

    @NotNull
    public String getTranslation() {
        return translation;
    }

    @NotNull
    public List<Integer> getExamplesList() {
        return Arrays.stream(examples.split(":")).map(Integer::getInteger).collect(Collectors.toList());
    }

    @NotNull
    public String getExamples() {
        return examples;
    }

    public boolean addExample(final int example) {
        for (int ex : getExamplesList()) if (ex == example) return false;
        examples = examples + ":" + example;
        return true;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public void setExamples(String examples) {
        this.examples = examples;
    }

    public void setLastTraining(@NotNull Date lastTraining) {
        this.lastTraining = lastTraining;
    }

    public void setProgress(int progress) {
        if (progress < 0) progress = 0;
        else if (progress > 100) progress = 100;
        this.progress = progress;
    }

    public boolean update(final Word word) {
        if (word.getId() != id) return false;
        this.word = word.getWord();
        this.translation = word.getTranslation();
        this.examples = word.getExamples();
        return true;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Word) {
            final Word other = (Word) obj;

            return id == other.id
                    && progress == other.progress
                    && lastTraining.equals(other.lastTraining)
                    && word.equals(other.word)
                    && translation.equals(other.translation)
                    && examples.equals(other.examples);
        }
        return false;
    }
}