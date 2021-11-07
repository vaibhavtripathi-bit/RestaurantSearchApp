package com.example.myrestaurants.module.restaurant_home_page.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.myrestaurants.databinding.ItemShowRestaurantBinding
import com.example.myrestaurants.module.restaurant_home_page.presentation.adapter.model.HomePageData
import com.example.myrestaurants.utils.loadImage

sealed class RestaurantHomeViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    var itemClickListener: ItemClickListener? = null

    class RestaurantViewHolder(
        private val binding: ItemShowRestaurantBinding,
    ) : RestaurantHomeViewHolder(binding) {
        fun bind(item: HomePageData.RestaurantItem) {
            binding.apply {
                restaurantName.text = item.name
                restaurantNeighborhood.text = item.neighborhood
                restaurantAddress.text = item.address
                restaurantType.text = item.cuisineType
                restaurantOperatingHour.text = item.operatingHour
                restaurantTopReview.text = item.topReview
                loadImage(restaurantIcon, item.photograph)
                root.setOnClickListener {
                    itemClickListener?.clickListener(it, item, adapterPosition)
                }
            }
        }
    }
}
