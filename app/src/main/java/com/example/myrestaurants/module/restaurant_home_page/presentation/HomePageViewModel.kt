package com.example.myrestaurants.module.restaurant_home_page.presentation

import androidx.lifecycle.viewModelScope
import com.example.myrestaurants.base.BaseViewModel
import com.example.myrestaurants.module.restaurant_home_page.domain.usecases.HomePageUseCases
import com.example.myrestaurants.module.restaurant_home_page.presentation.adapter.model.HomePageData.RestaurantItem
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate

class HomePageViewModel(
    private val homePageUseCases: HomePageUseCases,
) : BaseViewModel() {
    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.LoadingState)
    val viewState: StateFlow<ViewState> = _viewState

    private var currentQuery: MutableStateFlow<String> = MutableStateFlow("")

    init {
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            updateViewState(ViewState.ErrorState.UnknownError(exception.localizedMessage ?: "Something Went Wrong"))
        }
        viewModelScope.launch(exceptionHandler) {
            currentQuery
                .collectLatest { fetchRestaurantForSearchQuery(currentQuery.value) }
        }
    }

    fun getRestaurantListForQueryText(query: String) {
        currentQuery.value = query
    }

    private suspend fun fetchRestaurantForSearchQuery(query: String) {
        //1. set initial state and make API call
        //2. Map the Data to the UI model object
        //3. update the final state value.
        runCatching {
            updateViewState(ViewState.LoadingState)
            homePageUseCases
                .restaurantLists.execute(params = query)
                .restaurants.map { restaurant ->
                    RestaurantItem(
                        name = restaurant.name,
                        neighborhood = restaurant.neighborhood,
                        address = restaurant.addressWithLatLng,
                        cuisineType = restaurant.cuisine_type,
                        id = restaurant.id,
                        photograph = restaurant.photograph,
                        operatingHour = restaurant.getTimingDetail(LocalDate.now()),
                        topReview = restaurant.topReview
                    )
                }
        }.onFailure { exception ->
            updateViewState(ViewState.ErrorState.UnknownError(exception.localizedMessage ?: "Something Went Wrong"))
        }.onSuccess {
            updateViewState(ViewState.SuccessState.ShowList(it))
        }
    }

    private fun updateViewState(state: ViewState) {
        if (_viewState.value != state)
            _viewState.value = state
    }
}

sealed class ViewState {
    object LoadingState : ViewState()
    sealed class ErrorState : ViewState() {
        data class UnknownError(val error: String) : ErrorState()
    }

    sealed class SuccessState : ViewState() {
        data class ShowList(val paymentInfoList: List<RestaurantItem>) : SuccessState()
        object ShowEmptyView : SuccessState()
    }
}
