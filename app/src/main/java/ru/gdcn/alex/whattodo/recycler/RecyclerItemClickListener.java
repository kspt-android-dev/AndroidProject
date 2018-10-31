package ru.gdcn.alex.whattodo.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import ru.gdcn.alex.whattodo.utilities.TextFormer;

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    private static final String TAG = "ToDO_Logger";
    public static final String className = "RecyclerItemClickListener";

    private OnItemClickListener itemClickListener;
    private GestureDetector gestureDetector;

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
        this.itemClickListener = listener;
        this.gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                itemClickListener.onItemClick(childView, recyclerView.getChildAdapterPosition(childView));
                return super.onSingleTapConfirmed(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
//                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
//                if (childView != null && itemClickListener != null) {
//                    itemClickListener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView));
//                }
            }


        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent event) {
        Log.d(TAG, TextFormer.getStartText(className) + "Словил Intercept!");
        gestureDetector.onTouchEvent(event);
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent event) {
        Log.d(TAG, TextFormer.getStartText(className) + "Словил TouchEvent!");
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {
        Log.d(TAG, TextFormer.getStartText(className) + "Словил Request!");
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
}
