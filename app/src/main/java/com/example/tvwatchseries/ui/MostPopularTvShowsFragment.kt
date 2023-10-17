package com.example.tvwatchseries.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.tvwatchseries.R
import com.example.tvwatchseries.data.model.TvShowsItem
import com.example.tvwatchseries.databinding.FragmentMostPopularTvShowsBinding
import com.example.tvwatchseries.ui.adaptors.MostPopularTvShowsAdaptor
import com.example.tvwatchseries.viewModels.MostPopularTvShowsViewModel

class MostPopularTvShowsFragment : Fragment() {
    private lateinit var binding: FragmentMostPopularTvShowsBinding
    private lateinit var mostPopularTvShowsAdaptor: MostPopularTvShowsAdaptor
    private lateinit var mMostPopularTvShowsViewModel: MostPopularTvShowsViewModel
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


    }

    private fun initUI() {

        mMostPopularTvShowsViewModel = ViewModelProvider(this)[MostPopularTvShowsViewModel::class.java]
        mostPopularTvShowsAdaptor = MostPopularTvShowsAdaptor()
        binding.tvShowsRecyclerView.adapter = mostPopularTvShowsAdaptor
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

    }


    private fun getMostPopularTVShows() {

        mMostPopularTvShowsViewModel.getMostPopTvShows(currentPage)?.observe(this) { response ->
            if (response != null) {
                Log.d("GGGGGGGGGGGGGGGg", "getMostPopularTVShows: ")
                totalAvailablePages= response.total!!
                if (response.tvShows?.isNotEmpty() == true){
                    Log.d("GGGGGGGGGGGGGGGg", "getMostPopularTVShows: list not empty")
                    mostPopularTvShowsAdaptor.addList(response.tvShows as ArrayList<TvShowsItem>?)
                }
            }
        }


    }


}