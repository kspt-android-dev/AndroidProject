package lizka.reminder.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.Date;

import lizka.reminder.R;
import lizka.reminder.model.ModelTask;

public class AddingTaskDialogFragment extends DialogFragment implements DatePickerFragment.OnDateSetLIstener, TimePickerFragment.OnTimeSetLIstener {

    private AddingTaskListener addingTaskListener;

    Calendar dateCalendar;
    Calendar timeCalendar;

    @Override
    public void onDateSet(Calendar dateCalendar) {
        this.dateCalendar = dateCalendar;
    }

    public void onTimeSet(Calendar timeCalendar) {
        this.timeCalendar = timeCalendar;
    }

    public interface AddingTaskListener {
        void createdTask(ModelTask newTask);

        void onTaskAddingCancel();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            addingTaskListener = (AddingTaskListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement AddingTaskListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.dialog_title);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View container = inflater.inflate(R.layout.dialog_task, null);

        final TextInputLayout tilTitle = container.findViewById(R.id.tilDialogTaskTitle);
        final EditText etTitle = tilTitle.getEditText();

        TextInputLayout tilDate = container.findViewById(R.id.tilDialogTaskDate);
        final EditText etDate = tilDate.getEditText();

        TextInputLayout tilTime = container.findViewById(R.id.tilDialogTaskTime);
        final EditText etTime = tilTime.getEditText();

        Spinner spPriority = container.findViewById(R.id.spDialogTaskPriority);

        tilTitle.setHint(getString(R.string.task_title));
        tilDate.setHint(getString(R.string.task_date));
        tilTime.setHint(getResources().getString(R.string.task_time));

        builder.setView(container);

        final ModelTask task = new ModelTask();

        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, ModelTask.PRIORITY_LEVELS);

        spPriority.setAdapter(priorityAdapter);

        spPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                task.setPriority(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 1);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etTime.length() == 0) {
                    etTime.setText(" ");
                }

                DatePickerFragment datePickerFragment = new DatePickerFragment(AddingTaskDialogFragment.this);
                datePickerFragment.setDate(etDate);
                datePickerFragment.show(getActivity().getSupportFragmentManager(), "DataPickerFragment");
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etTime.length() == 0) {
                    etTime.setText(" ");
                }

                TimePickerFragment timePickerFragment = new TimePickerFragment( AddingTaskDialogFragment.this);
                timePickerFragment.setTime(etTime);
                timePickerFragment.show(getActivity().getSupportFragmentManager(), "TimePickerFragment");
            }
        });

        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                task.setTitle((etTitle.getText().toString()));
                if (etDate.length() != 0 || etTime.length() != 0) {
                    if (dateCalendar != null) {
                        task.setDate(dateCalendar.getTimeInMillis());
                        if (timeCalendar != null){
                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.DATE, dateCalendar.get(Calendar.DATE));
                            cal.set(Calendar.MONTH, dateCalendar.get(Calendar.MONTH));
                            cal.set(Calendar.YEAR, dateCalendar.get(Calendar.YEAR));
                            cal.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY));
                            cal.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));
                            cal.set(Calendar.SECOND, timeCalendar.get(Calendar.SECOND));
                            task.setTime(cal.getTimeInMillis());
                        }
                    } else
                        task.setDate(calendar.getTimeInMillis());
                    // dateCalendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 1);
                }
                addingTaskListener.createdTask(task);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addingTaskListener.onTaskAddingCancel();
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                final Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                // блокируем создание пустых task'ов
                if (etTitle.length() == 0) {
                    positiveButton.setEnabled(false);
                    tilTitle.setError(getString(R.string.dialog_error_empty_title));
                }

                etTitle.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() == 0) {
                            positiveButton.setEnabled(false);
                            tilTitle.setError(getString(R.string.dialog_error_empty_title));
                        } else {
                            positiveButton.setEnabled(true);
                            tilTitle.setErrorEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });

        return alertDialog;
    }
}
