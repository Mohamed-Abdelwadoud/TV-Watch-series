package com.example.tvwatchseries.ui

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.tvwatchseries.R
import com.example.tvwatchseries.data.model.TVShowEntity
import com.example.tvwatchseries.data.model.TvShowsItem
import com.example.tvwatchseries.databinding.FragmentSearchBinding
import com.example.tvwatchseries.databinding.ItemContainerTvShowBinding
import com.example.tvwatchseries.ui.adaptors.TvShowsAdaptor
import com.example.tvwatchseries.viewModels.FavTvShowsViewModel
import com.example.tvwatchseries.viewModels.SearchViewModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.io.ByteArrayOutputStream

class SearchFragment : Fragment(), TvShowsAdaptor.TvListener {
    private lateinit var binding: FragmentSearchBinding
    private var input: String = ""
    private lateinit var tvShowsAdaptor: TvShowsAdaptor
    private lateinit var mSearchViewModel: SearchViewModel
    private var currentPage = 1
    private lateinit var mFavTvShowsViewModel: FavTvShowsViewModel

    private var totalAvailablePages = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSearchBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        initUI()

        binding.searchImage.setOnClickListener(View.OnClickListener {
            input = binding.searchEditText.text.toString().trim()
            if (input.isNotEmpty()) {
                getResult(input)
                binding.loadingBar.visibility=View.VISIBLE
                binding.tvShowsRecyclerView.visibility=View.GONE
            }
        })

    }

    private fun initUI() {
        mSearchViewModel =
            ViewModelProvider(this)[SearchViewModel::class.java]
        tvShowsAdaptor = TvShowsAdaptor(this)
        binding.tvShowsRecyclerView.adapter = tvShowsAdaptor
        binding.tvShowsRecyclerView.setHasFixedSize(true)

        binding.tvShowsRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.tvShowsRecyclerView.canScrollVertically(1)) {
                    if (input.isNotEmpty()) {
                        if (currentPage <= totalAvailablePages) {
                            currentPage += 1
                            getResult(input)
                        }
                    }
                }
            }
        })
        setSwipe()

    }


    private fun getResult(input: String) {
        mSearchViewModel.getSearchTVShows(input, currentPage)?.observe(this) { response ->
            if (response != null) {
                totalAvailablePages = response.total!!
                if (response.tvShows?.isNotEmpty() == true) {
                    tvShowsAdaptor.setList((response.tvShows as ArrayList<TvShowsItem>?)!!)
                    binding.loadingBar.visibility=View.GONE
                    binding.tvShowsRecyclerView.visibility=View.VISIBLE
                }
            }

        }
    }

    override fun handleTVPress(ClickedShow: TvShowsItem) {
        Navigation.findNavController(requireView()).navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailedTvShowFragment(
                ClickedShow
            )
        )
    }

    private fun setSwipe() {
        var simpleCallBack: ItemTouchHelper.SimpleCallback =
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
                        .addSwipeLeftLabel("SaveTo Watch List")
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


}