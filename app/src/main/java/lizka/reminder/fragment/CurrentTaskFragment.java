package lizka.reminder.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lizka.reminder.R;
import lizka.reminder.adapter.CurrentTasksAdapter;
import lizka.reminder.model.ModelTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentTaskFragment extends Fragment {

    private RecyclerView rvCurrentTasks;
    private RecyclerView.LayoutManager layoutManager;

    private CurrentTasksAdapter adapter;

    public CurrentTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_current_task, container, false);

        rvCurrentTasks = rootView.findViewById(R.id.rvCurrentTasks);

        layoutManager = new LinearLayoutManager(getActivity());
        rvCurrentTasks.setLayoutManager(layoutManager);

        adapter = new CurrentTasksAdapter();
        rvCurrentTasks.setAdapter(adapter);

        // Inflate the layout for this fragment
        return rootView;
    }

    // добавляем элементы по дате
    public void addTask(ModelTask newTask){
        int position = -1;
        adapter.addItem(newTask);
        adapter.notifyDataSetChanged();

//        for (int i = 0; i < adapter.getItemCount(); i++){
//            if (adapter.getItem(i).isTask()){
//                ModelTask task = (ModelTask) adapter.getItem(i);
//                if (newTask.getDate() < task.getDate()){
//                    position = i;
//                    break;
//                }
//            }
//
//        }
    }
}
