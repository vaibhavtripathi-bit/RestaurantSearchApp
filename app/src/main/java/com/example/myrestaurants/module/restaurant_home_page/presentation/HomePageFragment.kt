package com.example.myrestaurants.module.restaurant_home_page.presentation

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myrestaurants.R
import com.example.myrestaurants.base.BaseFragment
import com.example.myrestaurants.databinding.RestaurantHomePageFragmentBinding
import com.example.myrestaurants.module.restaurant_home_page.presentation.ViewState.ErrorState
import com.example.myrestaurants.module.restaurant_home_page.presentation.ViewState.LoadingState
import com.example.myrestaurants.module.restaurant_home_page.presentation.ViewState.SuccessState
import com.example.myrestaurants.module.restaurant_home_page.presentation.adapter.HomePageAdapter
import com.example.myrestaurants.module.restaurant_home_page.presentation.adapter.ItemClickListener
import com.example.myrestaurants.module.restaurant_home_page.presentation.adapter.model.HomePageData
import com.example.myrestaurants.module.restaurant_home_page.presentation.adapter.model.HomePageData.RestaurantItem
import com.example.myrestaurants.utils.getQueryTextChangeStateFlow
import com.example.myrestaurants.utils.runWhenLastItemBecomeVisible
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@FlowPreview
class HomePageFragment : BaseFragment(R.layout.restaurant_home_page_fragment) {
    private val viewModel by viewModel<HomePageViewModel>()
    private var _binding: RestaurantHomePageFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var homePageAdapter: HomePageAdapter

    private val clickListener = object : ItemClickListener {
        override fun clickListener(itemView: View, item: HomePageData, position: Int) {
            when (item) {
                is RestaurantItem -> {
                    view?.let { view ->
                        val snackBar = Snackbar.make(view, "Thanks for choosing ${item.name}", Snackbar.LENGTH_SHORT)
                        context?.let {
                            snackBar.view.rootView.setBackgroundColor(ContextCompat.getColor(it, R.color.black))
                            snackBar.show()
                        }
                    }
                }
            }
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        _binding = RestaurantHomePageFragmentBinding.bind(view)
        setUpRecyclerView()
        registerWithLifecycleScope()
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            homePageAdapter = HomePageAdapter(onClickListener = clickListener)
            layoutManager = LinearLayoutManager(context)
            adapter = homePageAdapter
        }

        binding.recyclerView.runWhenLastItemBecomeVisible {
            /*
            TODO: This feature is pending, to load more item API call.
            1. check if more item is there to load, if true, then
            2. make an API call for the next page.
            */
        }
    }

    private fun registerWithLifecycleScope() {
        viewLifecycleOwner.lifecycleScope.launch {
            supervisorScope {
                fetchRestaurantsForQuery("")
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch { registerForUIUpdates() }
                    launch { handleSearchResult() }
                }
            }
        }
    }

    private suspend fun registerForUIUpdates() {
        viewModel.viewState.collect { state ->
            when (state) {
                is LoadingState -> handleLoadingState(state)
                is ErrorState -> handleErrorState(state)
                is SuccessState -> handleSuccessState(state)
            }
            updateUIVisibility(state)
        }
    }

    private suspend fun handleSearchResult() {
        onSearchQueryChange()
            .collect { fetchRestaurantsForQuery(it) }
    }

    private fun handleLoadingState(state: LoadingState) {
    }

    private fun handleErrorState(state: ErrorState) = when (state) {
        is ErrorState.UnknownError -> {
            binding.errorInfo.text = state.error
        }
    }

    private fun handleSuccessState(state: SuccessState) = when (state) {
        is SuccessState.ShowList -> {
            (binding.recyclerView.adapter as HomePageAdapter).updateList(state.paymentInfoList)
        }
        is SuccessState.ShowEmptyView -> {
            (binding.recyclerView.adapter as HomePageAdapter).updateList(listOf())
        }
    }

    private fun updateUIVisibility(state: ViewState) {
        binding.apply {
            when (state) {
                is LoadingState -> {
                    showProgressState()
                }
                is ErrorState -> {
                    showErrorState()
                }
                is SuccessState -> {
                    hideProgressState()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchRestaurantsForQuery(query: String) {
        viewModel.getRestaurantListForQueryText(query)
    }

    private fun onSearchQueryChange(): Flow<String> = binding.searchView.getQueryTextChangeStateFlow()
        .debounce(500)
        //.filter { it.isNotEmpty() or if search should start after 3 character}
        .distinctUntilChanged()
        .mapLatest { it }
        .flowOn(Dispatchers.Default)
        .catch { /* Handle any error before passing data forward */ }

    private fun RestaurantHomePageFragmentBinding.showErrorState() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE
        errorInfo.visibility = View.VISIBLE
    }

    private fun RestaurantHomePageFragmentBinding.showProgressState() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        errorInfo.visibility = View.GONE
    }

    private fun RestaurantHomePageFragmentBinding.hideProgressState() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        errorInfo.visibility = View.GONE
    }
}
