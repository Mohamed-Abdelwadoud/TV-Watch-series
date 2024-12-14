package com.example.tvwatchseries.presentation.screens.mostPopularTvShowsScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tvwatchseries.databinding.FragmentMostPopularTvShowsBinding
import com.example.tvwatchseries.domain.model.TvShowsItemModel
import com.example.tvwatchseries.presentation.adaptors.TvShowsAdaptor
import com.example.tvwatchseries.presentation.screens.favoriteTvShowScreen.FavTvShowsViewModel
import com.example.tvwatchseries.presentation.util.ActionSaveToWatchList
import com.example.tvwatchseries.presentation.util.SwipeHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MostPopularTvShowsFragment : Fragment(), TvShowsAdaptor.TvListener {
    private lateinit var tvShowsAdaptor: TvShowsAdaptor
    private lateinit var swipeHelper: SwipeHelper

    private var _binding: FragmentMostPopularTvShowsBinding? = null
    private val binding get() = _binding!!

    private val mostPopularTvShowsViewModel by viewModels<MostPopularTvShowsViewModel>()
    private val favTvShowsViewModel by viewModels<FavTvShowsViewModel>()

    private var currentPage = 1
    private var totalAvailablePages = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMostPopularTvShowsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initUI()

        setOnClickListeners()

        getMostPopularTVShows()


        initObservers()


    }

    private fun initUI() {
        tvShowsAdaptor = TvShowsAdaptor(this)
        binding.tvShowsRecyclerView.adapter = tvShowsAdaptor
        binding.tvShowsRecyclerView.setHasFixedSize(true)

        swipeHelper = SwipeHelper(binding.tvShowsRecyclerView, ActionSaveToWatchList())
        swipeHelper.setSwipe { position ->
            saveToWatchList(tvShowsAdaptor.getItemByPosition(position)) // Your action on swipe
        }

        binding.tvShowsRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.tvShowsRecyclerView.canScrollVertically(1)) {
                    if (currentPage <= totalAvailablePages) {
                        currentPage += 1
                        getMostPopularTVShows()
                    }
                }
            }
        })


    }


    private fun getMostPopularTVShows() {
        mostPopularTvShowsViewModel.getMostPopTvShows(currentPage)
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            mostPopularTvShowsViewModel.mostPopularTvShows.collect { state ->
                updateUI(state)
            }
        }

    }

    private fun updateUI(state: MostPopularTvShowsState) {
        binding.loadingBar.isVisible = state.isLoading


        state.error?.let {
            Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
        }

        state.tvShows?.let { response ->
            response.total?.let { totalAvailablePages = it }
            tvShowsAdaptor.addList(response.tvShows)
        }

    }

    override fun handleTVPress(tvShowItem: TvShowsItemModel) {
        findNavController(requireView()).navigate(
            MostPopularTvShowsFragmentDirections.actionMostPopularTvShowsFragmentToDetailedTvShowFragment(
                tvShowItem
            )
        )
    }


    private fun saveToWatchList(tvShowItem: TvShowsItemModel) {
        favTvShowsViewModel.addNewTvShow(tvShowItem)

    }

    // so when user leave this fragment page reset to top
    override fun onPause() {
        super.onPause()
        currentPage = 1
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun setOnClickListeners() {
        binding.searchImage.setOnClickListener {
            findNavController(requireView()).navigate(
                MostPopularTvShowsFragmentDirections.actionMostPopularTvShowsFragmentToSearchFragment()
            )
        }

        binding.FavImage.setOnClickListener {
            findNavController(requireView()).navigate(
                MostPopularTvShowsFragmentDirections.actionMostPopularTvShowsFragmentToFavTvShowFragment2()
            )
        }
    }


//    private fun setSwipe() {
//        val simpleCallBack: ItemTouchHelper.SimpleCallback =
//            object :
//                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
//
//                override fun onMove(
//                    recyclerView: RecyclerView,
//                    viewHolder: RecyclerView.ViewHolder,
//                    target: RecyclerView.ViewHolder
//                ): Boolean {
//                    return true
//                }
//
//                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                    if (direction == ItemTouchHelper.LEFT) {
//                        val position = viewHolder.bindingAdapterPosition
//                        saveToWatchList(tvShowsAdaptor.getItemByPosition(position))
////                        tvShowsAdaptor.notifyDataSetChanged()
//                    }
//                }
//
//                override fun onChildDraw(
//                    c: Canvas,
//                    recyclerView: RecyclerView,
//                    viewHolder: RecyclerView.ViewHolder,
//                    dX: Float,
//                    dY: Float,
//                    actionState: Int,
//                    isCurrentlyActive: Boolean
//                ) {
//                    RecyclerViewSwipeDecorator.Builder(
//                        c,
//                        recyclerView,
//                        viewHolder,
//                        dX,
//                        dY,
//                        actionState,
//                        isCurrentlyActive
//                    )
//                        .addSwipeLeftActionIcon(R.drawable.ic_baseline_watch_later_24)
//                        .addSwipeLeftLabel("add To Watch List")
//                        .addSwipeLeftBackgroundColor(Color.WHITE)
//                        .create()
//                        .decorate()
//
//                    super.onChildDraw(
//                        c,
//                        recyclerView,
//                        viewHolder,
//                        dX,
//                        dY,
//                        actionState,
//                        isCurrentlyActive
//                    )
//                }
//
//            }
//
//        val itemTouchHelper = ItemTouchHelper(simpleCallBack)
//
//        itemTouchHelper.attachToRecyclerView(binding.tvShowsRecyclerView)
//    }


}