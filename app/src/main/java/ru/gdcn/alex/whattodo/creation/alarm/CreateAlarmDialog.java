package ru.gdcn.alex.whattodo.creation.alarm;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ru.gdcn.alex.whattodo.R;
import ru.gdcn.alex.whattodo.creation.AlarmBroadcastReceiver;
import ru.gdcn.alex.whattodo.creation.CreationActivity;
import ru.gdcn.alex.whattodo.objects.Notify;

public class CreateAlarmDialog extends DialogFragment{

    public void update(int year, int month, int day) {
        date.setText(String.format("%s:%s:%s", day, month + 1, year));
    }

    public void update(int hour, int minute) {
        time.setText(String.format("%s:%s", hour, minute));
    }

    public interface OnAlarmDialogListener{
        public void onSetAlarm();
        public void onDeleteAlarm();
    }

    private TextView date, time;

    private OnAlarmDialogListener onAlarmDialogListener;
    private Notify notify;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        View view = getActivity().getLayoutInflater().inflate(R.layout.creation_alarm_dialog, null);
        notify = ((CreationActivity)getContext()).getNoteManager().getNotify();
        date = view
                .findViewById(R.id.creation_alarm_dialog_choose_date);
        date.setText(String.format("%s:%s:%s", notify.getDay(), notify.getMonth() + 1, notify.getYear()));
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChooseDateDialog().show(getFragmentManager(), "date");
            }
        });
        time = view
                .findViewById(R.id.creation_alarm_dialog_choose_time);
        time.setText(String.format("%s:%s", notify.getHour(), notify.getMinute()));
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

                        onAlarmDialogListener.onSetAlarm();
                    }
                })
                .setNegativeButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onAlarmDialogListener.onDeleteAlarm();
                    }
                })
                .setNeutralButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getDialog().cancel();
                    }
                })
                .setTitle("Установите дату и время");
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAlarmDialogListener = (CreationActivity) context;
    }
}
