package lizka.reminder.adapter;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import lizka.reminder.R;
import lizka.reminder.Utils;
import lizka.reminder.model.ModelTask;


class TaskViewHolder extends RecyclerView.ViewHolder {
    private TextView title = itemView.findViewById(R.id.tvTaskTitle);
    private TextView date = itemView.findViewById(R.id.tvTaskDate);
    private TextView time = itemView.findViewById(R.id.tvTaskTime);
    private ImageView priority = itemView.findViewById(R.id.cvTaskPriority);

    TaskViewHolder(View v) {
        super(v);
    }

    void bind(final ModelTask task, final MyCallBack callBackListener) {
        final Resources resources = itemView.getResources();
        title.setText(task.getTitle());
        if (task.getDate() != 0) {
            date.setText(Utils.getFullDate(task.getDate()));
        } else {
            date.setText(null);
        }

        if (task.getTime() != 0) {
            time.setText(Utils.getTime(task.getTime()));
        } else {
            time.setText(null);
        }

        itemView.setVisibility(View.VISIBLE);
        itemView.setBackgroundColor(resources.getColor(R.color.gray_50));
        itemView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                callBackListener.longClick(itemView);
                return false;
            }
        });
        title.setTextColor(resources.getColor(R.color.primary_text_default_material_light));
        date.setTextColor(resources.getColor(R.color.secondary_text_default_material_light));
        priority.setColorFilter(resources.getColor(task.getPriorityColor()));

        priority.setImageResource(R.drawable.blank_icon);

        priority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemView.setBackgroundColor(resources.getColor(R.color.gray_200));
                title.setTextColor(resources.getColor(R.color.primary_text_disabled_material_light));
                date.setTextColor(resources.getColor(R.color.secondary_text_disabled_material_light));
                priority.setColorFilter(resources.getColor(task.getPriorityColor()));
                priority.setImageResource(R.drawable.done_icon);
                callBackListener.clickOnItem();
            }
        });
    }

    interface MyCallBack {
        void clickOnItem();

        void longClick(View itemView);
    }
}
