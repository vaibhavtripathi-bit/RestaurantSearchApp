package com.example.myrestaurants.utils

import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun SearchView.getQueryTextChangeStateFlow(): StateFlow<String> {
    val query = MutableStateFlow("")
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            query.value = newText
            return true
        }
    })
    return query
}

fun RecyclerView.runWhenLastItemBecomeVisible(function: () -> Unit) {
    require(adapter != null) { "Adapter must be set" }
    (layoutManager as? LinearLayoutManager)?.let { layoutManager ->
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val itemPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                if (adapter!!.itemCount > 0 && adapter!!.itemCount - 1 == itemPosition) {
                    function()
                }
            }
        })
    }
}
