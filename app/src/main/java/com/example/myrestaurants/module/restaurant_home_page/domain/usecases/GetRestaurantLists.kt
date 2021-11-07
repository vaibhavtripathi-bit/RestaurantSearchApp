package com.example.myrestaurants.module.restaurant_home_page.domain.usecases

import com.example.myrestaurants.data.network.response.home_page.RestaurantResponse
import com.example.myrestaurants.domain.UseCase
import com.example.myrestaurants.module.restaurant_home_page.domain.repository.RestaurantDataRepository

class GetRestaurantLists(
    private val repository: RestaurantDataRepository,
) : UseCase<RestaurantResponse, String>() {
    override suspend fun execute(params: String): RestaurantResponse {
        return repository.getRestaurantsFromQuery(params)
    }
}
