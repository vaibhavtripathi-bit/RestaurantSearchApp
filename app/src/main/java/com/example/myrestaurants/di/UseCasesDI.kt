package com.example.myrestaurants.di

import com.example.myrestaurants.module.restaurant_home_page.domain.usecases.GetRestaurantLists
import com.example.myrestaurants.module.restaurant_home_page.domain.usecases.HomePageUseCases
import org.koin.dsl.module

val useCases = module {
    factory { HomePageUseCases(get()) }
    factory { GetRestaurantLists(get()) }
}
