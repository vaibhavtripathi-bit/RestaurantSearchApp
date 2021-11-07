package com.example.myrestaurants.di

import com.example.myrestaurants.module.restaurant_home_page.data.repository.RestaurantDataRepositoryMockImpl
import com.example.myrestaurants.module.restaurant_home_page.domain.repository.RestaurantDataRepository
import org.koin.dsl.module

val repositoryDependency = module {
    single<RestaurantDataRepository> {
        RestaurantDataRepositoryMockImpl()
    }
}
