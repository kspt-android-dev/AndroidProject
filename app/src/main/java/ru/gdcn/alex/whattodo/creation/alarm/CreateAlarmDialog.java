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
import ru.gdcn.alex.whattodo.creation.CreationActivity;
import ru.gdcn.alex.whattodo.objects.Notify;

public class CreateAlarmDialog extends DialogFragment {

    private static final String TAG = "ToDO_Logger";
    private static final String className = "CreateAlarmDialog";

    public interface OnAlarmDialogListener {
        void onSetAlarm();
        void onDeleteAlarm();
        void onCancel();
    }

    private TextView date, time;

    private OnAlarmDialogListener onAlarmDialogListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        View view = getActivity().getLayoutInflater().inflate(R.layout.creation_alarm_dialog, null);
        date = view.findViewById(R.id.creation_alarm_dialog_choose_date);
        time = view.findViewById(R.id.creation_alarm_dialog_choose_time);
        Notify notify = ((CreationActivity) getContext()).getNoteManager().getNotify();
        if (notify != null) {
            date.setText(
                    String.format(
                            "%s:%s:%s",
                            formatString(notify.getDay()),
                            formatString(notify.getMonth() + 1),
                            notify.getYear()
                    )
            );
            time.setText(
                    String.format(
                            "%s:%s",
                            formatString(notify.getHour()),
                            formatString(notify.getMinute()))
            );
        } else {
            date.setText(getResources().getString(R.string.dialog_choose_date));
            time.setText(getResources().getString(R.string.dialog_choose_time));
        }
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChooseDateDialog().show(getFragmentManager(), "date");
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChooseTimeDialog().show(getFragmentManager(), "time");
            }
        });

        builder.setView(view)
                .setPositiveButton(getResources().getString(R.string.dialog_save_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "SAVE!", Toast.LENGTH_SHORT).show();
                        onAlarmDialogListener.onSetAlarm();
                    }
                })
                .setNeutralButton(getResources().getString(R.string.dialog_cancel_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getDialog().cancel();
                    }
                })
                .setTitle(getResources().getString(R.string.dialog_title));
        if (notify != null)
            builder.setNegativeButton(getResources().getString(R.string.dialog_delete_button), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onAlarmDialogListener.onDeleteAlarm();
                }
            });
        return builder.create();
    }

    public void update(int year, int month, int day) {
        date.setText(String.format("%s:%s:%s", formatString(day), formatString(month + 1), year));
    }

    public void update(int hour, int minute) {
        time.setText(String.format("%s:%s", formatString(hour), formatString(minute)));
    }

    private String formatString(int value) {
        if (value < 10)
            return "0" + String.valueOf(value);
        else
            return String.valueOf(value);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAlarmDialogListener = (CreationActivity) context;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        onAlarmDialogListener.onCancel();
        super.onCancel(dialog);
    }
}
