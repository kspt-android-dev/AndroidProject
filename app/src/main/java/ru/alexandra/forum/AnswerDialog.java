package ru.alexandra.forum;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

public class AnswerDialog extends DialogFragment {

    public interface OnAddAnswerListener{
        void onAddAnswer(String text);
    }

    private OnAddAnswerListener onAddAnswerListener;

    EditText editText;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = getActivity().getLayoutInflater().inflate(R.layout.answer_dialog, null);
        editText = view.findViewById(R.id.answer_dialog_text);
        builder.setView(view);
        builder.setPositiveButton(getResources().getString(R.string.dialog_answer), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onAddAnswerListener.onAddAnswer(editText.getText().toString());
            }
        })
                .setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAddAnswerListener = (OnAddAnswerListener) context;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(Constants.KEY_ANSWER, editText.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        editText.setText(savedInstanceState.getString(Constants.KEY_ANSWER));
        super.onViewStateRestored(savedInstanceState);
    }
}
