package ru.gdcn.alex.whattodo.creation;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ru.gdcn.alex.whattodo.R;
import ru.gdcn.alex.whattodo.objects.Item;

public class ItemsRecyclerAdapter {


    class ItemViewHolder extends RecyclerView.ViewHolder {

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
//            headerView = itemView.findViewById(R.id.notes_recyclerview_header);
        }

        void bind(Item note) {

        }


    }
}
