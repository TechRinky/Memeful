package com.example.infrrdassignment

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * This class contains the logic of pagination in recylerview
 */
abstract class PaginationScrollListener(val layoutManager: StaggeredGridLayoutManager) :
    RecyclerView.OnScrollListener() {

    /*
     Method gets callback when user scroll the search list
     */
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val arrayOfFirstVisibleItemPos = layoutManager.findLastCompletelyVisibleItemPositions(null)
        val firstVisibleItemPosition = arrayOfFirstVisibleItemPos[arrayOfFirstVisibleItemPos.size - 1]
        if (!isLoading() && !isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                && firstVisibleItemPosition >= 0
            ) {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()


    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean

    companion object {
        private val TAG = PaginationScrollListener::class.java.simpleName
    }

}