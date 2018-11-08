package ru.gdcn.alex.whattodo.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.gdcn.alex.whattodo.Card;
import ru.gdcn.alex.whattodo.R;

public class NoteFragment extends Fragment {

    private TextView content;
    private Card note;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.creation_note_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        content = getActivity().findViewById(R.id.creation_note_text);
        note = (Card) savedInstanceState.getSerializable("card");
        content.setText(note.getContent());
    }
}
