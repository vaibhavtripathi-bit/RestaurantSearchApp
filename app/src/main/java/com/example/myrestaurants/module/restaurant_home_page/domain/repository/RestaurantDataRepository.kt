package com.example.myrestaurants.module.restaurant_home_page.domain.repository

import com.example.myrestaurants.data.network.response.home_page.RestaurantResponse

interface RestaurantDataRepository {
    suspend fun getRestaurantsFromQuery(query: String): RestaurantResponse
}
