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
import lizka.reminder.adapter.DoneTaskAdapter;
import lizka.reminder.model.ModelTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoneTaskFragment extends Fragment {
    
    public DoneTaskFragment() {
        // Required empty public constructor
    }
    protected RecyclerView recyclerView;
    protected DoneTaskAdapter adapter;
    public MainActivity activity;
    private LinearLayoutManager layoutManager;


    public void addTask(ModelTask newTask){
        adapter.addItem(newTask);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_done_task, container, false);
        activity = (MainActivity) getActivity();
        recyclerView = rootView.findViewById(R.id.rvDoneTasks);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        activity.getAllDone(new Function1<List<ModelTask>, Unit>() {
            @Override
            public Unit invoke(List<ModelTask> modelTasks) {
                adapterInit(modelTasks);
                return null;
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    private void adapterInit(List<ModelTask> modelTasks) {
        adapter = new DoneTaskAdapter(this, modelTasks);
        recyclerView.setAdapter(adapter);
    }
}
