package lizka.reminder.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

import lizka.reminder.R;

@Entity
public class ModelTask implements Item{

    public static final int PRIORITY_LOW = 0;
    public static final int PRIORITY_NORMAL = 1;
    public static final int PRIORITY_HIGH = 2;

    public static final String[] PRIORITY_LEVELS = {"Low Priority", "Normal Priority", "High Priority"};

    public static final int STATUS_OVERDUE = 0;
    public static final int STATUS_CURRENT = 1;
    public static final int STATUS_DONE = 2;

    @PrimaryKey(autoGenerate = true)
    private long id = 0;
    private String title = null;
    private long date = 0;
    private long time = 0;
    private int priority = 0;
    private int status = 0;
    private long timeStamp = 0;

    public ModelTask(){
        this.status = -1;
        this.timeStamp = new Date().getTime();
    }

    public ModelTask(String title, long date, long time, int priority, int status, long timeStamp){
        this.title = title;
        this.date = date;
        this.time = time;
        this.priority = priority;
        this.status = status;
        this.timeStamp = timeStamp;
    }

    public int getPriorityColor(){
        switch (getPriority()){

            case PRIORITY_HIGH:
                if (getStatus() == STATUS_CURRENT || getStatus() == STATUS_OVERDUE){
                    return R.color.priority_high;
                } else {
                    return R.color.priority_high_selected;
                }

            case PRIORITY_NORMAL:
                if (getStatus() == STATUS_CURRENT || getStatus() == STATUS_OVERDUE){
                    return R.color.priority_normal;
                } else {
                    return R.color.priority_normal_selected;
                }

            case PRIORITY_LOW:
                if (getStatus() == STATUS_CURRENT || getStatus() == STATUS_OVERDUE){
                    return R.color.priority_low;
                } else {
                    return R.color.priority_low_selected;
                }

            default:
                return 0;
        }
    }

    @Override
    public boolean isTask() {
        return status == STATUS_CURRENT;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getTime(){
        return time;
    }

    public void setTime(long time){
        this.time = time;
    }
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
