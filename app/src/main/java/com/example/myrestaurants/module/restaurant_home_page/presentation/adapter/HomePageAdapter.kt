package com.example.myrestaurants.module.restaurant_home_page.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myrestaurants.R
import com.example.myrestaurants.databinding.ItemShowRestaurantBinding
import com.example.myrestaurants.module.restaurant_home_page.presentation.adapter.model.HomePageData
import com.example.myrestaurants.module.restaurant_home_page.presentation.adapter.model.HomePageData.RestaurantItem

class HomePageAdapter(
    itemList: List<HomePageData> = mutableListOf(),
    private val onClickListener: ItemClickListener? = null
) : RecyclerView.Adapter<RestaurantHomeViewHolder>() {

    private var itemList = mutableListOf<HomePageData>()

    init {
        this.itemList.addAll(itemList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantHomeViewHolder {
        return when (viewType) {
            R.layout.item_show_restaurant -> {
                RestaurantHomeViewHolder.RestaurantViewHolder(inflateView(parent))
            }
            else -> throw Exception("Unknown View Holder Type")
        }
    }

    override fun onBindViewHolder(holder: RestaurantHomeViewHolder, position: Int) {
        holder.itemClickListener = onClickListener
        val item = itemList[position]
        when (holder) {
            is RestaurantHomeViewHolder.RestaurantViewHolder -> holder.bind(item as RestaurantItem)
        }
    }

    override fun getItemCount() = itemList.size

    override fun getItemViewType(position: Int): Int {
        return when (itemList[position]) {
            is RestaurantItem -> R.layout.item_show_restaurant
        }
    }

    private fun inflateView(parent: ViewGroup) = ItemShowRestaurantBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    )

    fun updateList(list: List<RestaurantItem>) {
        itemList.clear()
        itemList.addAll(list)
        notifyItemRangeChanged(0, itemList.size)
    }
}
