package com.example.myrestaurants.module.restaurant_home_page.data.repository

import com.example.myrestaurants.data.network.response.home_page.RestaurantResponse
import com.example.myrestaurants.module.restaurant_home_page.domain.repository.RestaurantDataRepository

class RestaurantDataRepositoryImpl : RestaurantDataRepository {
    override suspend fun getRestaurantsFromQuery(query: String): RestaurantResponse {
        if (isNetworkAvailable()) {
            fetchFromNetwork()
        } else {
            fetchFromCachedData()
        }
        TODO("Not yet implemented")
    }

    private fun fetchFromCachedData() {
        TODO("Not yet implemented")
    }

    private fun fetchFromNetwork() {
        TODO("Not yet implemented")
    }

    private fun isNetworkAvailable(): Boolean {
        TODO("Not yet implemented")
    }
}
