package com.example.tvwatchseries.presentation.util

import android.graphics.Canvas
import android.graphics.Color
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.tvwatchseries.R
import com.example.tvwatchseries.domain.model.TvShowsItemModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


class SwipeHelper(
    private val recyclerView: RecyclerView,
    private val setSwipeActionProvider: SetSwipeActionProvider,
) {

    fun setSwipe(action: (Int) -> Unit) {
        val simpleCallBack: ItemTouchHelper.SimpleCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    // try false
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    if (direction == ItemTouchHelper.LEFT) {
                        val position = viewHolder.bindingAdapterPosition
                        action(position)
                    }
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    setSwipeActionProvider.setSwipeAction(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )


//                    RecyclerViewSwipeDecorator.Builder(
//                        c,
//                        recyclerView,
//                        viewHolder,
//                        dX,
//                        dY,
//                        actionState,
//                        isCurrentlyActive
//                    )
//                        .addSwipeLeftActionIcon(R.drawable.ic_baseline_watch_later_24)
//                        .addSwipeLeftLabel("Add To Watch List")
//                        .addSwipeLeftBackgroundColor(Color.WHITE)
//                        .create()
//                        .decorate()
//
                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }
            }

        val itemTouchHelper = ItemTouchHelper(simpleCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}


//interface SwipeActionListener {
//    fun onSwipeAction(tvShowsItemModel: TvShowsItemModel)
//}

interface SetSwipeActionProvider {
    fun setSwipeAction(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    )

}

class ActionSaveToWatchList : SetSwipeActionProvider {
    override fun setSwipeAction(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        return RecyclerViewSwipeDecorator.Builder(
            c,
            recyclerView,
            viewHolder,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
            .addSwipeLeftActionIcon(R.drawable.ic_baseline_watch_later_24)
            .addSwipeLeftLabel("Add To Watch List")
            .addSwipeLeftBackgroundColor(Color.WHITE)
            .create()
            .decorate()


    }
}

class ActionRemoveFromWatchList : SetSwipeActionProvider {
    override fun setSwipeAction(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        return RecyclerViewSwipeDecorator.Builder(
            c,
            recyclerView,
            viewHolder,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
            .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
            .addSwipeLeftLabel("Delete")
            .addSwipeLeftBackgroundColor(Color.WHITE)
            .create()
            .decorate()
    }

}