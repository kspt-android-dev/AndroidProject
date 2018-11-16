package ru.gdcn.alex.whattodo.creation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import ru.gdcn.alex.whattodo.R;

public class NoteFragment extends Fragment {

    private EditText contentView;

    private String text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.creation_note_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        contentView = getActivity().findViewById(R.id.creation_note_fragment_text);
        setupData();
    }

    private void setupData() {
        if (text != null)
            contentView.setText(text);
    }

    public String getText() {
        text = contentView.getText().toString();
        return text;
    }

    public void setText(String text) {
        this.text = text;
        if (contentView != null)
            contentView.setText(text);
    }

}
