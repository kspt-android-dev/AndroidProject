package com.julia.tag.records;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.julia.tag.R;

import java.util.List;

import static com.julia.tag.game.GameActivity.DB_NAME;

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.RecordViewHolder> {

    private AppDataBase dataBase;
    private List<Record> recordsList;

    public RecordsAdapter(Context context) {
        dataBase =  Room.databaseBuilder(context, AppDataBase.class, DB_NAME)
                .allowMainThreadQueries()
                .build();
        recordsList = dataBase.recordDao().getAll();
    }

    public void deleteAll(){
        dataBase.recordDao().deleteAll();
        recordsList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.record_layout, viewGroup, false);
        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder recordViewHolder, int i) {
        recordViewHolder.bind(recordsList.get(i));
    }

    @Override
    public int getItemCount() {
        return recordsList.size();
    }

    public void onDestroyActivity() {
        dataBase.close();
    }

    class RecordViewHolder extends RecyclerView.ViewHolder {

        private TextView name, moves;

        RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.record_name);
            moves = itemView.findViewById(R.id.record_moves);
        }

        void bind(Record record){
            name.setText(record.name);
            moves.setText(String.valueOf(record.moves));
        }
    }
}
