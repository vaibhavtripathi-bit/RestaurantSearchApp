package com.example.myrestaurants.data.network.apiservice

import com.example.myrestaurants.data.constants.Constants.CLIENT_ID
import com.example.myrestaurants.data.network.response.home_page.RestaurantResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RestaurantApiService {
    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET("search/restaurant")
    suspend fun getRestaurants(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): RestaurantResponse
}
