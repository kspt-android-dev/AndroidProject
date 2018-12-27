package ru.gdcn.alex.whattodo.creation.alarm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import android.widget.TimePicker;
import android.widget.Toast;

import ru.gdcn.alex.whattodo.R;
import ru.gdcn.alex.whattodo.creation.CreationActivity;

public class ChooseTimeDialog extends DialogFragment {

    public interface OnSetTimeIntoDateDialog {
        void onSetTime(int hour, int minutes);
    }

    private OnSetTimeIntoDateDialog onSetTimeIntoDateDialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(getActivity().getLayoutInflater().inflate(R.layout.creation_alarm_dialog_time, null))
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TimePicker timePicker = getDialog().findViewById(R.id.creation_alarm_dialog_time);
                        onSetTimeIntoDateDialog.onSetTime(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onSetTimeIntoDateDialog = (CreationActivity) context;
    }
}
