package com.example.tvwatchseries.presentation.screens.searchScreen

import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.tvwatchseries.R
import com.example.tvwatchseries.databinding.FragmentSearchBinding
import com.example.tvwatchseries.domain.model.TvShowsItemModel
import com.example.tvwatchseries.presentation.adaptors.TvShowsAdaptor
import com.example.tvwatchseries.presentation.screens.favoriteTvShowScreen.FavTvShowsViewModel
import com.example.tvwatchseries.presentation.util.ActionSaveToWatchList
import com.example.tvwatchseries.presentation.util.SwipeHelper
import dagger.hilt.android.AndroidEntryPoint
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(), TvShowsAdaptor.TvListener {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var searchKey: String = ""

    private lateinit var swipeHelper: SwipeHelper

    private lateinit var tvShowsAdaptor: TvShowsAdaptor

    private val mSearchViewModel: SearchViewModel by viewModels()
    private val mFavTvShowsViewModel: FavTvShowsViewModel by viewModels()

    private var currentPage = 1
    private var totalAvailablePages = 1


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initUI()

        initSearchMechanism()


        initViewClickListeners()

        initObservers()


    }

    private fun initUI() {
        tvShowsAdaptor = TvShowsAdaptor(this)
        binding.tvShowsRecyclerView.adapter = tvShowsAdaptor
        binding.tvShowsRecyclerView.setHasFixedSize(true)

        swipeHelper = SwipeHelper(binding.tvShowsRecyclerView, ActionSaveToWatchList())
        swipeHelper.setSwipe { position ->
            saveToWatchList(tvShowsAdaptor.getItemByPosition(position))
        }

        binding.tvShowsRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.tvShowsRecyclerView.canScrollVertically(1)) {
                    if (searchKey.isNotEmpty()) {
                        if (currentPage <= totalAvailablePages) {
                            currentPage += 1
                            searchForTvShows(searchKey)
                        }
                    }
                }
            }
        })

    }

    private fun initSearchMechanism() {
        var previousTextLength = 0
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                previousTextLength = s?.length ?: 0

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                if (s?.isNotEmpty() == true) {
//                    binding.clearIcon.visibility = View.VISIBLE
//
//                    // Only search if the new length is greater than the previous length
//                    if (s.length > previousTextLength) {
//                        searchForTvShows(s.toString())
//                    }
//                } else {
//                    binding.clearIcon.visibility = View.INVISIBLE
//                }
                if (s?.isNotEmpty() == true) {
                    binding.clearIcon.visibility = View.VISIBLE
                    searchForTvShows(s.toString())
                } else if (s?.isEmpty() == true) {
                    binding.clearIcon.visibility = View.INVISIBLE

                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (binding.searchEditText.text.toString().trim().isNotEmpty()) {
                    searchForTvShows(binding.searchEditText.text.toString().trim())
                    binding.loadingBar.visibility = View.VISIBLE
//                    binding.tvShowsRecyclerView.visibility = View.GONE
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun initViewClickListeners() {

        binding.searchImage.setOnClickListener {
            searchKey = binding.searchEditText.text.toString().trim()
            if (searchKey.isNotEmpty()) {
                searchForTvShows(searchKey)
                binding.loadingBar.visibility = View.VISIBLE
                binding.tvShowsRecyclerView.visibility = View.GONE
            }
        }

        binding.clearIcon.setOnClickListener {
            binding.searchEditText.text.clear()
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            mSearchViewModel.mostPopularTvShows.collect { state ->
                binding.loadingBar.isVisible = state.isLoading

                updateUI(state)

            }
        }
    }


    private fun updateUI(state: SearchTvShowState) {
        binding.loadingBar.isVisible = state.isLoading

        state.error?.let { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        state.tvShows?.let { response ->
            response.total?.let { totalAvailablePages = it }
            tvShowsAdaptor.setList(ArrayList(response.tvShows))
        }

    }


    private fun searchForTvShows(input: String) {
        mSearchViewModel.getSearchTVShows(input, currentPage)
    }


    private fun saveToWatchList(tvShowItem: TvShowsItemModel) {
        mFavTvShowsViewModel.addNewTvShow(tvShowItem)

    }

    override fun handleTVPress(tvShowItem: TvShowsItemModel) {
        Navigation.findNavController(requireView()).navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailedTvShowFragment(
                tvShowItem
            )
        )
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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
////                        saveToWatchList(holder.binding)
//                        saveToWatchList(tvShowsAdaptor.getItemByPosition(position))
//
//                        tvShowsAdaptor.notifyDataSetChanged()
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
//                        .addSwipeLeftLabel("SaveTo Watch List")
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