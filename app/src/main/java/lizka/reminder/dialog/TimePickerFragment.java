package lizka.reminder.dialog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import lizka.reminder.Utils;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private EditText etTime;

    public void setTime(EditText etTime){
        this.etTime = etTime;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.set(0, 0, 0, hourOfDay, minute);
        etTime.setText(Utils.getTime(timeCalendar.getTimeInMillis()));
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        etTime.setText(null);
    }
}
