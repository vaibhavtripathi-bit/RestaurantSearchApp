package com.example.myrestaurants.module.restaurant_home_page.data.repository

import com.example.myrestaurants.RestaurantsApplication
import com.example.myrestaurants.data.network.response.home_page.MenuResponse
import com.example.myrestaurants.data.network.response.home_page.Restaurant
import com.example.myrestaurants.data.network.response.home_page.RestaurantResponse
import com.example.myrestaurants.module.restaurant_home_page.domain.repository.RestaurantDataRepository
import kotlinx.coroutines.delay

class RestaurantDataRepositoryMockImpl : RestaurantDataRepository {
    override suspend fun getRestaurantsFromQuery(query: String): RestaurantResponse {
        return if (query.isEmpty()) {
            fetchRestaurantFromNetwork()
        } else {
            delay(300)
            val restaurantList = getRestaurantWithQueryAndIds(
                query = query,
                restaurantResponse = fetchRestaurantFromNetwork(),
                restaurantIdList = getRestaurantForQueries(fetchMenuFromNetwork(), query)
            )
            RestaurantResponse(restaurantList)
        }
    }

    private fun getRestaurantWithQueryAndIds(
        query: String,
        restaurantResponse: RestaurantResponse,
        restaurantIdList: Set<Int>,
    ): List<Restaurant> {
        return restaurantResponse
            .restaurants
            .filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.cuisine_type.contains(query, ignoreCase = true) ||
                        restaurantIdList.contains(it.id)
            }
    }

    private fun getRestaurantForQueries(
        menuResponse: MenuResponse,
        query: String,
    ): Set<Int> {
        val restaurantIdList = menuResponse.menus.flatMapTo(mutableSetOf()) { menu ->
            menu.categories
                .filter { menuCategory ->
                    menuCategory
                        .menuItems
                        .any { it.description.contains(query, ignoreCase = true) }
                }
                .map { menu.restaurantId }
        }
        return restaurantIdList
    }

    private fun fetchMenuFromNetwork() = RestaurantsApplication.mockMenuResponse ?: MenuResponse(emptyList())

    private fun fetchRestaurantFromNetwork(): RestaurantResponse {
        RestaurantsApplication.mockRestaurantResponse?.let { return it }
        return getEmptyRestaurantResponse()
        // to test the network error.
        //throw Exception("Unable to Fetch the data from Network")
    }

    private fun getEmptyRestaurantResponse() = RestaurantResponse(emptyList())
}
