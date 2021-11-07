package com.example.myrestaurants

import android.app.Application
import com.example.myrestaurants.data.network.response.home_page.MenuResponse
import com.example.myrestaurants.data.network.response.home_page.RestaurantResponse
import com.example.myrestaurants.di.appComponent
import com.example.myrestaurants.utils.FileUtil
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class RestaurantsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        loadData()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@RestaurantsApplication)
            modules(provideDependency())
        }
    }

    //List of Koin dependencies
    private fun provideDependency() = appComponent

    companion object {
        var mockRestaurantResponse: RestaurantResponse? = null
        var mockMenuResponse: MenuResponse? = null
    }

    private fun loadData() {
        mockRestaurantResponse = FileUtil.loadJSONResponseFromAsset(this, "JSON/restaurants_list.txt", RestaurantResponse::class.java)
        mockMenuResponse = FileUtil.loadJSONResponseFromAsset(this, "JSON/menu_list.txt", MenuResponse::class.java)
    }
}
