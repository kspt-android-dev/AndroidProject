package ru.gdcn.alex.whattodo.creation.alarm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import ru.gdcn.alex.whattodo.R;
import ru.gdcn.alex.whattodo.creation.CreationActivity;

public class ChooseDateDialog extends DialogFragment {

    public interface OnSetDateIntoDateDialog {
        public void onSetDate(int year, int month, int day);
    }

    private OnSetDateIntoDateDialog onSetDateIntoDateDialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(getActivity().getLayoutInflater().inflate(R.layout.creation_alarm_dialog_date, null))
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatePicker datePicker = getDialog().findViewById(R.id.creation_alarm_dialog_date);
                        onSetDateIntoDateDialog.onSetDate(
                                datePicker.getYear(),
                                datePicker.getMonth(),
                                datePicker.getDayOfMonth()
                        );
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onSetDateIntoDateDialog = (CreationActivity) context;
    }


}
