package com.example.tvwatchseries.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tvwatchseries.R
import com.example.tvwatchseries.data.model.TvShowsItem
import com.example.tvwatchseries.databinding.FragmentMostPopularTvShowsBinding
import com.example.tvwatchseries.ui.adaptors.MostPopularTvShowsAdaptor
import com.example.tvwatchseries.viewModels.MostPopularTvShowsViewModel

class MostPopularTvShowsFragment : Fragment(), MostPopularTvShowsAdaptor.TvListener {
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

        binding.searchImage.setOnClickListener(View.OnClickListener {
            findNavController(requireView()).navigate(
                MostPopularTvShowsFragmentDirections.actionMostPopularTvShowsFragmentToSearchFragment())
        })


    }

    private fun initUI() {

        mMostPopularTvShowsViewModel =
            ViewModelProvider(this)[MostPopularTvShowsViewModel::class.java]
        mostPopularTvShowsAdaptor = MostPopularTvShowsAdaptor(this)
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
        if (binding.loadingBar.visibility != View.VISIBLE) {
            binding.loadingBar.visibility = View.VISIBLE
            mMostPopularTvShowsViewModel.getMostPopTvShows(currentPage)?.observe(this) { response ->
                if (response != null) {
                    totalAvailablePages = response.total!!
                    if (response.tvShows?.isNotEmpty() == true) {
                        mostPopularTvShowsAdaptor.addList(response.tvShows as ArrayList<TvShowsItem>?)
                    }
                }
                binding.loadingBar.visibility = View.GONE

            }
        }


    }

    override fun handleTVPress(ClickedShow: TvShowsItem) {
        findNavController(requireView()).navigate(
            MostPopularTvShowsFragmentDirections.actionMostPopularTvShowsFragmentToDetailedTvShowFragment(
                ClickedShow
            )
        )
    }


}