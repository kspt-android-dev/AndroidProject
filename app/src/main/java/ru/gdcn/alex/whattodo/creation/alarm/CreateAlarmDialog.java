package ru.gdcn.alex.whattodo.creation.alarm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ru.gdcn.alex.whattodo.R;

public class CreateAlarmDialog extends DialogFragment {

    private TextView date = null, time;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        View view = getActivity().getLayoutInflater().inflate(R.layout.creation_alarm_dialog, null);

        date = view
                .findViewById(R.id.creation_alarm_dialog_choose_date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChooseDateDialog().show(getFragmentManager(), "date");
            }
        });
        time = view
                .findViewById(R.id.creation_alarm_dialog_choose_time);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChooseTimeDialog().show(getFragmentManager(), "time");
            }
        });

        builder.setView(view)
                .setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
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
                })
                .setTitle("Установите дату и время");
        return builder.create();
    }
}
