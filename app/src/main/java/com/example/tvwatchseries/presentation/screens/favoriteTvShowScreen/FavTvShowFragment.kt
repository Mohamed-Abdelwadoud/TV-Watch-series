package com.example.tvwatchseries.presentation.screens.favoriteTvShowScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tvwatchseries.databinding.FragmentFavTvShowBinding
import com.example.tvwatchseries.domain.model.TvShowsItemModel
import com.example.tvwatchseries.presentation.adaptors.TvShowsAdaptor
import com.example.tvwatchseries.presentation.util.ActionRemoveFromWatchList
import com.example.tvwatchseries.presentation.util.SwipeHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavTvShowFragment : Fragment(), TvShowsAdaptor.TvListener {
    private var _binding: FragmentFavTvShowBinding? = null
    private val binding get() = _binding!!
    private lateinit var mTvShowsAdaptor: TvShowsAdaptor
    private lateinit var swipeHelper: SwipeHelper

    private val mFavTvShowsViewModel: FavTvShowsViewModel by viewModels()

    // 2 errors .. empty list(null ) and  re arrange

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavTvShowBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initUi()

        initObservers()
    }


    private fun initObservers() {
        mFavTvShowsViewModel.getFavTvShows.observe(viewLifecycleOwner) { shows ->
            shows?.let {
                mTvShowsAdaptor.setList(ArrayList(it))

            }
        }
    }


    private fun initUi() {
        mTvShowsAdaptor = TvShowsAdaptor(this)
        binding.favRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.favRecyclerView.adapter = mTvShowsAdaptor

        swipeHelper = SwipeHelper(binding.favRecyclerView, ActionRemoveFromWatchList())
        swipeHelper.setSwipe { position ->

            removeFromWatchList(mTvShowsAdaptor.getItemByPosition(position)) // Your action on swipe
        }


    }


    private fun removeFromWatchList(tvShowEntity: TvShowsItemModel) {
        mFavTvShowsViewModel.viewModelScope.launch(Dispatchers.IO) {
            mFavTvShowsViewModel.removeTvShow(tvShowEntity.id)
        }


    }

    override fun handleTVPress(tvShowItem: TvShowsItemModel) {
        Navigation.findNavController(requireView()).navigate(
            FavTvShowFragmentDirections.actionFavTvShowFragmentToDetailedTvShowFragment(
                tvShowItem
            )
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}