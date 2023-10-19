package com.example.tvwatchseries.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.tvwatchseries.R
import com.example.tvwatchseries.data.model.TvShowsItem
import com.example.tvwatchseries.databinding.FragmentMostPopularTvShowsBinding
import com.example.tvwatchseries.databinding.FragmentSearchBinding
import com.example.tvwatchseries.ui.adaptors.MostPopularTvShowsAdaptor
import com.example.tvwatchseries.viewModels.MostPopularTvShowsViewModel
import com.example.tvwatchseries.viewModels.SearchViewModel

class SearchFragment : Fragment(), MostPopularTvShowsAdaptor.TvListener {
    // test Code
    private lateinit var binding: FragmentSearchBinding
    private var input: String = ""
    private lateinit var mostPopularTvShowsAdaptor: MostPopularTvShowsAdaptor
    private lateinit var mSearchViewModel: SearchViewModel
    private var currentPage = 1
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
        mostPopularTvShowsAdaptor = MostPopularTvShowsAdaptor(this)
        binding.tvShowsRecyclerView.adapter = mostPopularTvShowsAdaptor
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

    }


    private fun getResult(input: String) {
        mSearchViewModel.getSearchTVShows(input, currentPage)?.observe(this) { response ->
            if (response != null) {
                totalAvailablePages = response.total!!
                if (response.tvShows?.isNotEmpty() == true) {
                    mostPopularTvShowsAdaptor.setList(response.tvShows as ArrayList<TvShowsItem>?)
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

}