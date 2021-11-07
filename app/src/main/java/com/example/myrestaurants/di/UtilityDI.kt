package com.example.myrestaurants.di

import com.example.myrestaurants.utils.AppUtility
import org.koin.dsl.module

/**
 * Utility DI component prepared.
 */
val AppUtilDependency = module {
    single { AppUtility() }
}