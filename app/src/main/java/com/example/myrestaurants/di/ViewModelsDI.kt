package com.example.myrestaurants.di

import com.example.myrestaurants.module.restaurant_home_page.presentation.HomePageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelDependency = module {
    viewModel { HomePageViewModel(homePageUseCases = get()) }
}
