package ru.gdcn.alex.whattodo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Objects;

import ru.gdcn.alex.whattodo.customviews.NotesView;

public class NotesFragment extends Fragment {

    public static final String className = "NotesFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.notes_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        LinearLayout mainl = getActivity().findViewById(R.id.notes_fragment_id);
//        NotesView notesView = new NotesView(getContext());
//        notesView.setData("Header", "Main text");
//        mainl.addView(notesView);

        super.onActivityCreated(savedInstanceState);
    }
}
