package com.example.myrestaurants.data.network.response.home_page

import com.google.gson.annotations.SerializedName

data class MenuResponse(
    val menus: List<Menu>
)

data class Menu(
    val categories: List<Category>,
    val restaurantId: Int
) {
    data class Category(
        val id: String,
        @SerializedName("menu-items")
        val menuItems: List<MenuItem>,
        val name: String
    )

    data class MenuItem(
        val description: String,
        val id: String,
        val images: List<Any>,
        val name: String,
        val price: String
    )
}
