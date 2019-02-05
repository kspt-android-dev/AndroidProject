package com.julia.tag.game;

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

import com.julia.tag.R;

public class SaveDialog extends DialogFragment {

    public interface OnSaveListener {
        void onSave(String name);
    }

    private OnSaveListener onSaveListener;

    public static final String DEFAULT_NAME = "NO_NAME";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View view = getActivity().getLayoutInflater().inflate(R.layout.record_dialog_save, null);
        builder.setTitle(getResources().getString(R.string.dialog_win_title))
                .setMessage(getResources().getString(R.string.dialog_win))
                .setView(view)
                .setPositiveButton(getResources().getString(R.string.dialog_save),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name = ((EditText) view.findViewById(R.id.record_dialog_save_edit_text))
                                        .getText().toString();
                                if (name.equals(""))
                                    onSaveListener.onSave(DEFAULT_NAME);
                                else
                                    onSaveListener.onSave(name);
                            }
                        })
                .setCancelable(false);
        setCancelable(false);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        onSaveListener = (OnSaveListener) context;
        super.onAttach(context);
    }
}
