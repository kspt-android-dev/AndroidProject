package ru.gdcn.alex.whattodo.creation.alarm;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import ru.gdcn.alex.whattodo.R;

public class ChooseDateDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setView(getActivity().getLayoutInflater().inflate(R.layout.creation_alarm_dialog_date, null))
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "SAVE!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "CANSEL!", Toast.LENGTH_SHORT).show();
                    }
                });

        return builder.create();
    }
}
