package ru.gdcn.alex.whattodo.recycler;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import ru.gdcn.alex.whattodo.main.NotesRecyclerAdapter;
import ru.gdcn.alex.whattodo.utilities.TextFormer;

public class SwipeDragHelperCallback extends ItemTouchHelper.Callback {

    private static final String TAG = "ToDO_Logger";
    private static final String className = "SwipeDragHelperCallback";

    private ActionCompletionContract contract;

    public SwipeDragHelperCallback(ActionCompletionContract contract) {
        Log.d(TAG, TextFormer.getStartText(className) + "Установил слушателя для перемещения и свайпа!");
        this.contract = contract;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        Log.d(TAG, TextFormer.getStartText(className) + "Получаю направление действия...");
        if (viewHolder instanceof NotesRecyclerAdapter.HeaderViewHolder) {
            Log.d(TAG, TextFormer.getStartText(className) + "Направление действия определено - заголовок!");
            return ItemTouchHelper.ACTION_STATE_IDLE;
        } else {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            Log.d(TAG, TextFormer.getStartText(className) + "Направление действия определено - карточка!");
            return makeMovementFlags(dragFlags, swipeFlags);
        }

    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        contract.onViewMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        Log.d(TAG, TextFormer.getStartText(className) + "Перемещение отслежено!");
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        contract.onViewSwiped(viewHolder.getAdapterPosition());
        Log.d(TAG, TextFormer.getStartText(className) + "Свайп отслежен!");
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        //TODO переделать чтоб при свайпе элемент не исчезал, если вдруг буду делать не удаление на это действие
        //а так же хотелось сделать чтоб снизу при сдвиге отображалась картинка какое действие сейчас будет

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    public interface ActionCompletionContract {
        void onViewMoved(int oldPosition, int newPosition);

        void onViewSwiped(int position);
    }
}
