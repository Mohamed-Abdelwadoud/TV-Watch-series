package com.example.tvwatchseries.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.tvwatchseries.R
import com.example.tvwatchseries.data.model.EpisodesItem
import com.example.tvwatchseries.data.model.TvShowsItem
import com.example.tvwatchseries.databinding.FragmentDetailedTvShowBinding
import com.example.tvwatchseries.databinding.SheetDialogLayoutBinding
import com.example.tvwatchseries.ui.adaptors.EpisodesAdapter
import com.example.tvwatchseries.ui.adaptors.PicturesAdaptor
import com.example.tvwatchseries.viewModels.DetailedTVViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.util.*


class DetailedTvShowFragment : Fragment() {
    private lateinit var binding: FragmentDetailedTvShowBinding
    private lateinit var detailedTVViewModel: DetailedTVViewModel
    private lateinit var clicked: TvShowsItem
    private val timer = Timer()
    private var maxCount = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailedTvShowBinding.inflate(layoutInflater)
        detailedTVViewModel = DetailedTVViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clicked = DetailedTvShowFragmentArgs.fromBundle(requireArguments()).clicked
        initMainCard()

        getApiCall()


    }


    private fun getApiCall() {
        detailedTVViewModel.getTVDetails(clicked.id.toString())?.observe(this) { response ->
            if (response != null) {
                if (response.tvShow?.pictures?.isNotEmpty() == true) {
                    initViewPAger(response.tvShow.pictures)
                    binding.picturesViewPager.visibility = View.VISIBLE
                    binding.linearSliderIndicators.visibility = View.VISIBLE
                }
                if (!response.tvShow?.countdown.toString().isNullOrEmpty()) {
                    binding.textRunTime.text = response.tvShow?.runtime.toString()
                    binding.textRunTime.append(" Min")
                }
                if (response.tvShow?.rating?.isNullOrEmpty() == false) {
                    binding.textrating.text = kotlin.String.format(
                        Locale.getDefault(),
                        "%.2f", response.tvShow.rating.toDouble()
                    )

                }
                if (response.tvShow?.genres?.isNullOrEmpty() == false) {
                    binding.textGenres.text = response.tvShow.genres[0]
                } else {
                    binding.textGenres.text = "NA"
                }

                binding.testDescription.text = response.tvShow?.description

                if (!response.tvShow?.url.isNullOrEmpty()) {
                    binding.buttonWebSite.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(
                            response.tvShow?.url
                        )
                        startActivity(intent)
                    }
                }

                if (!response.tvShow?.episodes.isNullOrEmpty()) {
                    binding.buttonEpisodes.setOnClickListener(View.OnClickListener {
                        val bottomSheetDialog = context?.let { it1 -> BottomSheetDialog(it1) }
                        val sheetDialogBinding = SheetDialogLayoutBinding.inflate(layoutInflater)
                        sheetDialogBinding.topNAme.text = "${clicked.name} Episodes"
                        val adapter =
                            EpisodesAdapter(response.tvShow?.episodes as List<EpisodesItem>) //
                        sheetDialogBinding.epRecView.layoutManager = LinearLayoutManager(context)
                        sheetDialogBinding.epRecView.adapter = adapter
                        bottomSheetDialog?.setContentView(sheetDialogBinding.root)
                        bottomSheetDialog?.show()

                    })
                }

            }
        }

    }


    private fun initViewPAger(pictures: List<String?>?) {
        val adapter = PicturesAdaptor(pictures as ArrayList<String>)
        binding.picturesViewPager.adapter = adapter
        setupSliderIndicator(pictures.size)

        binding.picturesViewPager.registerOnPageChangeCallback(object :
            OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                autoSilding((pictures.size))
                maxCount = pictures.size
                setcurrentSliderIndicator(position)
            }
        })
    }

    fun setupSliderIndicator(count: Int) {
        val indicator = arrayOfNulls<ImageView>(count)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(10, 0, 10, 20)
        for (i in indicator.indices) {
            indicator[i] = ImageView(context!!)
            indicator[i]!!.setImageDrawable(
                ContextCompat.getDrawable(
                    context!!,
                    R.drawable.indicator_off
                )
            )
            indicator[i]!!.layoutParams = layoutParams
            binding.linearSliderIndicators.addView(indicator[i])
            setcurrentSliderIndicator(0)
        }
        binding.linearSliderIndicators.visibility = View.VISIBLE
    }

    fun setcurrentSliderIndicator(position: Int) {
        val childCount: Int = binding.linearSliderIndicators.childCount
        for (i in 0 until childCount) {
            val imageView =
                binding.linearSliderIndicators.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        context!!,
                        R.drawable.indicator_off
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        context!!,
                        R.drawable.indicator_on
                    )
                )
            }
        }
    }


    private fun autoSilding(max: Int) {
        var currentPage = binding.picturesViewPager.currentItem
        val autoScrollInterval = 3000 // Delay in milliseconds

        timer.schedule(object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread {
                    if (currentPage == max) {
                        currentPage = 0
                    }
                    binding.picturesViewPager.setCurrentItem(currentPage++, false)
                }
            }
        }, 0, autoScrollInterval.toLong())

    }


    private fun initMainCard() {
        binding.tvMainData.textNetwork.text = clicked.network
        binding.tvMainData.textStarted.text = clicked.startDate
        binding.tvMainData.textStatus.text = clicked.status
        binding.tvMainData.textName.text = clicked.name
        try {
            binding.tvMainData.imageTvShow.alpha = 0f
            Picasso.get().load(clicked.imageThumbnailPath).noFade()
                .into(binding.tvMainData.imageTvShow, object :
                    Callback {
                    override fun onSuccess() {
                        binding.tvMainData.imageTvShow.animate().setDuration(300).alpha(1f).start()
                    }

                    override fun onError(e: Exception?) {
                    }
                })
        } catch (ignored: Exception) {
        }

    }


}






