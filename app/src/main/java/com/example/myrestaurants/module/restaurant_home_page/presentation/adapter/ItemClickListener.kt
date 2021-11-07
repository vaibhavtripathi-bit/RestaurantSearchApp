package com.example.myrestaurants.module.restaurant_home_page.presentation.adapter

import android.view.View
import com.example.myrestaurants.module.restaurant_home_page.presentation.adapter.model.HomePageData

interface ItemClickListener {
    fun clickListener(itemView: View, item: HomePageData, position: Int)
}
