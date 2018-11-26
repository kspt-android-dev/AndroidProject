package ru.gdcn.alex.whattodo.creation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import ru.gdcn.alex.whattodo.R;
import ru.gdcn.alex.whattodo.objects.Item;
import ru.gdcn.alex.whattodo.objects.Note;
import ru.gdcn.alex.whattodo.utilities.TextFormer;

public class NoteFragment extends Fragment {

    private static final String TAG = "ToDO_Logger";
    private static final String className = "NoteFragment";

    private CreationActivity activity;

    private EditText contentView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = (CreationActivity) getActivity();
        Log.d(TAG, TextFormer.getStartText(className) + "Создал NoteFragment!");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.creation_note_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        contentView = getActivity().findViewById(R.id.creation_note_fragment_text);
        Log.d(TAG, TextFormer.getStartText(className) + "Интерфейс NoteFragment создан!");
    }

    @Override
    public void onResume() {
        super.onResume();
        contentView.setText(activity.getNoteManager().getNote().getContent());
        Log.d(TAG, TextFormer.getStartText(className) + "Resume NoteFragment!");
    }

    @Override
    public void onPause() {
        super.onPause();
        activity.getNoteManager().getNote().setContent(contentView.getText().toString());
        Log.d(TAG, TextFormer.getStartText(className) + "Pause NoteFragment!");
    }
}
