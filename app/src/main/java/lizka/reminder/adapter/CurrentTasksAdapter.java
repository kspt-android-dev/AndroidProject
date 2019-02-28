package lizka.reminder.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import java.util.List;

import lizka.reminder.R;
import lizka.reminder.fragment.CurrentTaskFragment;
import lizka.reminder.model.Item;
import lizka.reminder.model.ModelTask;

public class CurrentTasksAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    private List<ModelTask> currentItems;
    private CurrentTaskFragment taskFragment;

    public CurrentTasksAdapter(CurrentTaskFragment taskFragment, List<ModelTask> current) {
        this.taskFragment = taskFragment;
        currentItems = current;
    }

    public void addItem(Item item) {
        final ModelTask task = (ModelTask) item;
        if (task.getStatus() != ModelTask.STATUS_DONE)
            currentItems.add(task);
        notifyDataSetChanged();
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_task, parent, false);
        return new TaskViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return currentItems.size();
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        final ModelTask item = currentItems.get(position);
        if (item.getStatus() != ModelTask.STATUS_DONE)
            holder.bind(item, new TaskViewHolder.MyCallBack() {
                @Override
                public void clickOnItem() {
                    currentItems.remove(item);
                    item.setStatus(ModelTask.STATUS_DONE);
                    notifyDataSetChanged();
                    taskFragment.activity.addDoneTask(item);
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
                currentItems.remove(task);
                taskFragment.activity.removeTask(task);
                notifyDataSetChanged();
                return false;
            }
        });
        popup.inflate(R.menu.popup);
        popup.show();
    }
}
