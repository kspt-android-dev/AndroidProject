package lizka.reminder.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import lizka.reminder.MainActivity;
import lizka.reminder.R;
import lizka.reminder.adapter.CurrentTasksAdapter;
import lizka.reminder.model.ModelTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentTaskFragment extends Fragment {

    protected RecyclerView recyclerView;
    protected CurrentTasksAdapter adapter;
    public MainActivity activity;
    private LinearLayoutManager layoutManager;

    public CurrentTaskFragment() {
        // Required empty public constructor
    }

    public void addTask(ModelTask newTask){
        adapter.addItem(newTask);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_current_task, container, false);
        activity = (MainActivity) getActivity();
        recyclerView = rootView.findViewById(R.id.rvCurrentTasks);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        activity.getAllCurrent(new Function1<List<ModelTask>, Unit>() {
            @Override
            public Unit invoke(List<ModelTask> modelTasks) {
                adapterInit(modelTasks);
                return null;
            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }

    public void adapterInit(List<ModelTask> listCurrent){
        adapter = new CurrentTasksAdapter(this, listCurrent);
        recyclerView.setAdapter(adapter);
    }
}
