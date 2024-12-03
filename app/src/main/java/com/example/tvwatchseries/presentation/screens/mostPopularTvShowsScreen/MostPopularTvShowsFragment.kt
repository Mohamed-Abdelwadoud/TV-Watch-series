package com.example.tvwatchseries.presentation.screens.mostPopularTvShowsScreen

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.tvwatchseries.R
import com.example.tvwatchseries.data.model.TVShowEntity
import com.example.tvwatchseries.data.model.TvShowsItem
import com.example.tvwatchseries.databinding.FragmentMostPopularTvShowsBinding
import com.example.tvwatchseries.databinding.ItemContainerTvShowBinding
import com.example.tvwatchseries.domain.model.TvShowsItemModel
import com.example.tvwatchseries.presentation.adaptors.TvShowsAdaptor
import com.example.tvwatchseries.presentation.screens.favoriteTvShowScreen.FavTvShowsViewModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.io.ByteArrayOutputStream


class MostPopularTvShowsFragment : Fragment(), TvShowsAdaptor.TvListener {
    private lateinit var binding: FragmentMostPopularTvShowsBinding
    private lateinit var tvShowsAdaptor: TvShowsAdaptor
    private lateinit var mMostPopularTvShowsViewModel: MostPopularTvShowsViewModel
    private lateinit var mFavTvShowsViewModel: FavTvShowsViewModel
    private var currentPage = 1
    private var totalAvailablePages = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentMostPopularTvShowsBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_most_popular_tv_shows, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMostPopularTvShowsBinding.bind(view)
        initUI()

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

    private fun initUI() {
//
        mMostPopularTvShowsViewModel = MostPopularTvShowsViewModel()

        tvShowsAdaptor = TvShowsAdaptor(this)
        binding.tvShowsRecyclerView.adapter = tvShowsAdaptor
        binding.tvShowsRecyclerView.setHasFixedSize(true)

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
        getMostPopularTVShows()

        setSwipe()

    }


    private fun getMostPopularTVShows() {
        if (binding.loadingBar.visibility != View.VISIBLE) {
            binding.loadingBar.visibility = View.VISIBLE
            mMostPopularTvShowsViewModel.getMostPopTvShows(currentPage)
                .observe(viewLifecycleOwner) { response ->
                    if (response != null) {
                        totalAvailablePages = response.total!!
                        if (response.tvShows?.isNotEmpty() == true) {
                            tvShowsAdaptor.addList(response.tvShows as ArrayList<TvShowsItemModel>?)
                        }

                    } else {
                        Toast.makeText(context, "Connection Lost", Toast.LENGTH_SHORT).show()

                    }
                    binding.loadingBar.visibility = View.GONE

                }
        }

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
                        val holder = viewHolder as TvShowsAdaptor.ItemTVHolder
                        saveToWatchList(holder.binding)
                        tvShowsAdaptor.notifyDataSetChanged()
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
                        .addSwipeLeftActionIcon(R.drawable.ic_baseline_watch_later_24)
                        .addSwipeLeftLabel("add To Watch List")
                        .addSwipeLeftBackgroundColor(Color.White.toArgb())
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

        itemTouchHelper.attachToRecyclerView(binding.tvShowsRecyclerView)
    }


    override fun handleTVPress(tvShow: TvShowsItemModel) {
        findNavController(requireView()).navigate(
            MostPopularTvShowsFragmentDirections.actionMostPopularTvShowsFragmentToDetailedTvShowFragment(
                tvShow
            )
        )
    }


    private fun saveToWatchList(binding: ItemContainerTvShowBinding) {

        mFavTvShowsViewModel = ViewModelProvider(this)[FavTvShowsViewModel::class.java]
        val drawable = binding.imageTvShow.drawable
        val bitmap = (drawable as? BitmapDrawable)?.bitmap
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        byteArrayOutputStream.close()

        mFavTvShowsViewModel.addNewTvShow(
            TVShowEntity(
                binding.imageTvShow.contentDescription.toString(), // id
                binding.textName.text.toString(),
                binding.textStarted.text.toString(),
                binding.textNetwork.text.toString(),
                binding.textStatus.text.toString(),
                byteArray
            )
        )

    }

    // so when user leave this fragment page reset to top
    override fun onPause() {
        super.onPause()
        currentPage = 1
    }


}