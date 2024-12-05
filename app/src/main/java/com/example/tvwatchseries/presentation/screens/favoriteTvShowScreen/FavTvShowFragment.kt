package com.example.tvwatchseries.presentation.screens.favoriteTvShowScreen

import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tvwatchseries.R
import com.example.tvwatchseries.data.model.TVShowEntity
import com.example.tvwatchseries.databinding.FragmentFavTvShowBinding
import com.example.tvwatchseries.databinding.ItemContainerTvShowBinding
import com.example.tvwatchseries.domain.model.TvShowsItemModel
import com.example.tvwatchseries.presentation.adaptors.FavTvShowsAdaptor
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavTvShowFragment : Fragment(), FavTvShowsAdaptor.FavTvShowListener {
    private lateinit var binding: FragmentFavTvShowBinding
    private lateinit var mTvShowsAdaptor: FavTvShowsAdaptor
    private lateinit var mFavTvShowsViewModel: FavTvShowsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fav_tv_show, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavTvShowBinding.bind(view)


        initUi()
    }


    private fun initUi() {
        mTvShowsAdaptor = FavTvShowsAdaptor(this)
        binding.favRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.favRecyclerView.adapter = mTvShowsAdaptor
        mFavTvShowsViewModel = ViewModelProvider(this)[FavTvShowsViewModel::class.java]
        mFavTvShowsViewModel.getFavTvShows.observe(viewLifecycleOwner, Observer { shows ->
            mTvShowsAdaptor.setFavList(shows)
        })

        setSwipe()

    }


    private fun setSwipe() {
        val simpleCallBack: ItemTouchHelper.SimpleCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    if (direction == ItemTouchHelper.LEFT) {
                        val holder = viewHolder as FavTvShowsAdaptor.FavTvHolder
                        removeFromWatchList(holder.binding)
                        mTvShowsAdaptor.notifyDataSetChanged()

                    }
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    RecyclerViewSwipeDecorator.Builder(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                        .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                        .addSwipeLeftLabel("Delete")
                        .addSwipeLeftBackgroundColor(Color.WHITE)
                        .create()
                        .decorate()

                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }

            }

        val itemTouchHelper = ItemTouchHelper(simpleCallBack)

        itemTouchHelper.attachToRecyclerView(binding.favRecyclerView)
    }


    private fun removeFromWatchList(holder: ItemContainerTvShowBinding) {
        mFavTvShowsViewModel.viewModelScope.launch(Dispatchers.IO) {
            mFavTvShowsViewModel.removeTvShow(holder.imageTvShow.contentDescription.toString())
        }


    }

    override fun onClick(tvShowEntity: TVShowEntity) {
        Navigation.findNavController(requireView()).navigate(
            FavTvShowFragmentDirections.actionFavTvShowFragmentToDetailedTvShowFragment(
                TvShowsItemModel(
                    null,
                    null,
                    Base64.encodeToString(tvShowEntity.showImageBytearray, Base64.DEFAULT),
                    tvShowEntity.showName,
                    tvShowEntity.showID.toInt(),
                    null,
                    tvShowEntity.showStartDate,
                    tvShowEntity.showNetwork,
                    tvShowEntity.showStatus
                )
            )
        )

    }


}