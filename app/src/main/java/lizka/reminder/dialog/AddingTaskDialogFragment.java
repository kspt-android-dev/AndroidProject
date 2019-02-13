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
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

import lizka.reminder.R;
import lizka.reminder.model.ModelTask;

public class AddingTaskDialogFragment extends DialogFragment {

    private  AddingTaskListener addingTaskListener;

    public interface AddingTaskListener {
        void onTaskAdded(ModelTask newTask);
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
    public Dialog onCreateDialog(Bundle savedInstanceState){

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

        tilTitle.setHint(getString(R.string.task_title));
        tilDate.setHint(getString(R.string.task_date));
        tilTime.setHint(getString(R.string.task_time));

        builder.setView(container);

        final ModelTask task = new ModelTask();
        final Calendar calendar = Calendar.getInstance();
        // добавим ко времени час, чтобы напоминание срабатывало если не указано время, а только дата
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 1);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etTime.length() == 0){
                etTime.setText(" ");
            }

                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.setDate(etDate);
                datePickerFragment.show(getActivity().getSupportFragmentManager(), "DataPickerFragment");
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etTime.length() == 0){
                    etTime.setText(" ");
                }

                TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.setTime(etTime);
                timePickerFragment.show(getActivity().getSupportFragmentManager(), "TimePickerFragment");
            }
        });

        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                task.setTitle((etTitle.getText().toString()));
                if (etDate.length() != 0 || etTime.length() != 0){
                    task.setDate(calendar.getTimeInMillis());
                }
                addingTaskListener.onTaskAdded(task);
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
                if (etTitle.length() == 0){
                    positiveButton.setEnabled(false);
                    tilTitle.setError(getString(R.string.dialog_error_empty_title));
                }

                etTitle.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() == 0){
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
