package com.example.myrestaurants.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.myrestaurants.utils.AppUtility
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class BaseFragment : Fragment, KoinComponent {
    constructor() : super()
    constructor(restaurantHomePageFragment: Int) : super(restaurantHomePageFragment)

    val mAppUtility: AppUtility by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        readFromBundle()
        super.onCreate(savedInstanceState)
    }

    protected open fun readFromBundle() {}
}
