package com.example.myrestaurants.module.restaurant_home_page.presentation.adapter.model

sealed class HomePageData {
    data class RestaurantItem(
        val name: String = "name",
        val neighborhood: String = "address",
        val address: String = "address",
        val cuisineType: String = "cuisineType",
        val id: Int = 123,
        val photograph: String = "photograph",
        val operatingHour: String = "photograph",
        val topReview: String = "photograph",
    ) : HomePageData()
}
