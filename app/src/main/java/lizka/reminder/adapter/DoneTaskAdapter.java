package lizka.reminder.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import java.util.List;

import lizka.reminder.R;
import lizka.reminder.fragment.DoneTaskFragment;
import lizka.reminder.model.Item;
import lizka.reminder.model.ModelTask;

public class DoneTaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    private List<ModelTask> doneItems;
    private DoneTaskFragment taskFragment;

    public DoneTaskAdapter(DoneTaskFragment taskFragment, List<ModelTask> done) {
        this.taskFragment = taskFragment;
        doneItems = done;
    }

    public void addItem(Item item) {
        final ModelTask task = (ModelTask) item;
        if (task.getStatus() == ModelTask.STATUS_DONE)
            doneItems.add(task);
        else
            doneItems.add(task);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_task, viewGroup, false);
        return new TaskViewHolder(v);
    }

    @Override
    public int getItemCount() {

        Log.i("BUGG", "done Items!!! = " + doneItems.size());
        return doneItems.size();
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        final ModelTask item = doneItems.get(position);
        if (item.getStatus() == ModelTask.STATUS_DONE)
            holder.bind(item, new TaskViewHolder.MyCallBack() {
                @Override
                public void clickOnItem() {
                    doneItems.remove(item);
                    item.setStatus(ModelTask.STATUS_CURRENT);
                    notifyDataSetChanged();
                    taskFragment.activity.addCurrentTask(item);
                }

                @Override
                public void longClick(View view) {
                    showPopup(view, item);
                }
            });
    }

    private void showPopup(View view, final ModelTask task) {
        PopupMenu popup = new PopupMenu(taskFragment.getContext(), view);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                doneItems.remove(task);
                taskFragment.activity.removeTask(task);
                notifyDataSetChanged();
                return false;
            }
        });
        popup.inflate(R.menu.popup);
        popup.show();
    }
}
