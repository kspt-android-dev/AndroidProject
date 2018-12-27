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
    private static final String className = "RecyclerItemClickListener";

    private final OnItemClickListener itemClickListener;
    private final GestureDetector gestureDetector;

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
        this.itemClickListener = listener;
        this.gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                itemClickListener.onScroll(e1.getY(), e2.getY());
                return super.onScroll(e1, e2, distanceX, distanceY);
            }

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
//        Log.d(TAG, TextFormer.getStartText(className) + "Словил Intercept!");
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
        void onScroll(float startY, float endY);
    }
}
