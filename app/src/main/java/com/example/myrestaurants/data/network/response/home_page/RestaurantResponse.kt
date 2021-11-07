package com.example.myrestaurants.data.network.response.home_page

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class RestaurantResponse(
    val restaurants: List<Restaurant>
)

data class Restaurant(
    val address: String,
    val cuisine_type: String,
    val id: Int,
    val latlng: Latlng,
    val name: String,
    val neighborhood: String,
    @SerializedName("operating_hours")
    val operatingHours: OperatingHours,
    val photograph: String,
    val reviews: List<Review>
) {
    fun getTimingDetail(date: LocalDate): String {
        return operatingHours.getTiming(date.dayOfWeek.name)
    }

    val topReview: String
        get() {
            return "Top Review : \n" + (reviews.firstOrNull()?.comments ?: "are not available at the moment") + "\n"
        }

    val addressWithLatLng: String
        get() {
            return address + "\nlat: ${latlng.lat}, lng: ${latlng.lng}\n"
        }

    data class Latlng(
        val lat: Double,
        val lng: Double
    )

    data class OperatingHours(
        @SerializedName("Monday")
        val monday: String,
        @SerializedName("Tuesday")
        val tuesday: String,
        @SerializedName("Wednesday")
        val wednesday: String,
        @SerializedName("Thursday")
        val thursday: String,
        @SerializedName("Friday")
        val friday: String,
        @SerializedName("saturday")
        val saturday: String,
        @SerializedName("sunday")
        val sunday: String
    ) {
        fun getTiming(dayName: String): String {
            return "Timing : " + when (dayName) {
                monday -> { monday }
                tuesday -> { tuesday }
                wednesday -> { wednesday }
                thursday -> { thursday }
                friday -> { friday }
                saturday -> { saturday }
                sunday -> { sunday }
                else -> {
                    Throwable("Incorrect date information")
                }
            }
        }
    }

    data class Review(
        val comments: String,
        val date: String,
        val name: String,
        val rating: Int
    )
}