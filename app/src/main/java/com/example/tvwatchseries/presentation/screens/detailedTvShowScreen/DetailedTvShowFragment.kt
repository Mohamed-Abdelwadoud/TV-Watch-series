package com.example.tvwatchseries.presentation.screens.detailedTvShowScreen

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.tvwatchseries.R
import com.example.tvwatchseries.databinding.FragmentDetailedTvShowBinding
import com.example.tvwatchseries.databinding.SheetDialogLayoutBinding
import com.example.tvwatchseries.domain.model.DetailedTvShowModel
import com.example.tvwatchseries.domain.model.EpisodesItemModel
import com.example.tvwatchseries.domain.model.TvShowsItemModel
import com.example.tvwatchseries.presentation.adaptors.EpisodesAdapter
import com.example.tvwatchseries.presentation.adaptors.PicturesAdaptor
import com.example.tvwatchseries.presentation.util.FetchImageUrl
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class DetailedTvShowFragment : Fragment() {
    // search test 30710

    private var _binding: FragmentDetailedTvShowBinding? = null
    private val binding get() = _binding!!

    private val detailedTVViewModel: DetailedTVViewModel by viewModels()
    private lateinit var tvShowItem: TvShowsItemModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailedTvShowBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvShowItem = DetailedTvShowFragmentArgs.fromBundle(
            requireArguments()
        ).tvShowItem

        initMainCard()

        getTvShowDetails()


        initObservers()


    }


    private fun getTvShowDetails() {
        detailedTVViewModel.getTVDetails(tvShowItem.id.toString())
//        detailedTVViewModel.getTVDetails("30710")
//            .observe(viewLifecycleOwner) { response ->
//                if (response != null) {
//                    binding.tipProgressBar.visibility = View.GONE
//                    if (response.tvShow?.pictures?.isNotEmpty() == true) {
//                        initViewPAger(response.tvShow.pictures)
//                        binding.picturesViewPager.visibility = View.VISIBLE
//                        binding.linearSliderIndicators.visibility = View.VISIBLE
//                    } else {
//                        // we place the imageThumbnailPath to view pager
//                        initViewPAger(listOf(tvShowItem.imageThumbnailPath))
//                    }
//                    if (response.tvShow?.countdown.toString().isNotEmpty()) {
//                        binding.textRunTime.text = response.tvShow?.runtime.toString()
//                        binding.textRunTime.append(" Min")
//                    } else {
//                        binding.textRunTime.text = "not available"
//                    }
//                    if (response.tvShow?.rating?.isEmpty() == false) {
//                        binding.textrating.text = String.format(
//                            Locale.getDefault(),
//                            "%.2f", response.tvShow.rating.toDouble()
//                        )
//
//                    } else {
//                        binding.textrating.text = "not available"
//                    }
//                    if (response.tvShow?.genres?.isEmpty() == false) {
//                        binding.textGenres.text = response.tvShow.genres[0]
//                    } else {
//                        binding.textGenres.text = "not available"
//                    }
//                    // handle special characters
//                    if (!response.tvShow?.description.isNullOrEmpty()) {
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                            binding.testDescription.text =
//                                Html.fromHtml(
//                                    response.tvShow?.description,
//                                    Html.FROM_HTML_MODE_LEGACY
//                                )
//                        } else {
//                            response.tvShow?.description?.replace("<br>", "\n")
//                                ?.replace("\r", "")?.replace("\t", "    ")
//                                ?.replace("<b>", "**")?.replace("</b>", "**")
//                        }
//
//
//                    } else {
//                        binding.testDescription.text = "not available"
//                    }
//                    binding.buttonWebSite.setOnClickListener {
//                        if (!response.tvShow?.url.isNullOrEmpty()) {
//                            val intent = Intent(Intent.ACTION_VIEW)
//                            intent.data = Uri.parse(
//                                response.tvShow?.url
//                            )
//                            startActivity(intent)
//                        } else {
//                            Toast.makeText(context, "not available", Toast.LENGTH_SHORT).show()
//                        }
//
//                    }
//                    binding.buttonEpisodes.setOnClickListener {
//                        if (!response.tvShow?.episodes.isNullOrEmpty()) {
//                            val bottomSheetDialog = context?.let { it1 -> BottomSheetDialog(it1) }
//                            val sheetDialogBinding =
//                                SheetDialogLayoutBinding.inflate(layoutInflater)
//                            sheetDialogBinding.topNAme.text = "${tvShowItem.name} Episodes"
//                            val adapter =
//                                EpisodesAdapter(response.tvShow?.episodes as List<EpisodesItem>) //
//                            sheetDialogBinding.epRecView.layoutManager =
//                                LinearLayoutManager(context)
//                            sheetDialogBinding.epRecView.adapter = adapter
//                            bottomSheetDialog?.setContentView(sheetDialogBinding.root)
//                            bottomSheetDialog?.show()
//
//                        } else {
//                            Toast.makeText(context, "not available", Toast.LENGTH_SHORT).show()
//
//                        }
//                    }
//                } else {
//                    Toast.makeText(context, "Time Out", Toast.LENGTH_SHORT).show()
//                    binding.textrating.text = "not available"
//                    binding.testDescription.text = "not available"
//                    binding.textRunTime.text = "not available"
//                    binding.textGenres.text = "not available"
//                    binding.tipProgressBar.visibility = View.GONE
//                    initViewPAger(listOf(tvShowItem.imageThumbnailPath))
//                    binding.buttonEpisodes.setOnClickListener {
//                        Toast.makeText(context, "not available", Toast.LENGTH_SHORT).show()
//                    }
//                    binding.buttonWebSite.setOnClickListener {
//                        Toast.makeText(context, "not available", Toast.LENGTH_SHORT).show()
//                    }
//
//
//                }
//            }

    }


    private fun initViewPAger(pictures: List<String?>?) {
        if (pictures != null) {
            val adapter = PicturesAdaptor(pictures)
            binding.picturesViewPager.adapter = adapter
            if (pictures.size > 1) {
                setupSliderIndicator(pictures.size)
                binding.picturesViewPager.registerOnPageChangeCallback(object :
                    OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        setCurrentSliderIndicator(position)
                    }
                })
                autoSliding(pictures.size)


            }
        }

    }

    private fun setupSliderIndicator(count: Int) {
        val indicator = arrayOfNulls<ImageView>(count)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(10, 0, 10, 20)
        for (i in indicator.indices) {
            indicator[i] = ImageView(requireContext())
            indicator[i]!!.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.indicator_off
                )
            )
            indicator[i]!!.layoutParams = layoutParams
            binding.linearSliderIndicators.addView(indicator[i])
            setCurrentSliderIndicator(0)
        }
        binding.linearSliderIndicators.visibility = View.VISIBLE
    }

    private fun setCurrentSliderIndicator(position: Int) {
        val childCount: Int = binding.linearSliderIndicators.childCount
        for (i in 0 until childCount) {
            val imageView =
                binding.linearSliderIndicators.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator_off
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator_on
                    )
                )
            }
        }
    }

    private fun autoSliding(max: Int) {
        val autoScrollInterval = 3000
        val firstDelay = 3000 // Delay in milliseconds

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread {
                    if (binding.picturesViewPager.currentItem == (max - 1)) {
                        binding.picturesViewPager.setCurrentItem(0, false)
                    } else {
                        binding.picturesViewPager.setCurrentItem(
                            binding.picturesViewPager.currentItem + 1,
                            false
                        )

                    }

                }
            }
        }, firstDelay.toLong(), autoScrollInterval.toLong())

    }


    private fun initMainCard() {
        binding.tvMainData.textNetwork.text = tvShowItem.network
        binding.tvMainData.textStarted.text = tvShowItem.startDate
        binding.tvMainData.textStatus.text = tvShowItem.status
        binding.tvMainData.textName.text = tvShowItem.name
        FetchImageUrl.getImageURL(
            binding.tvMainData.imageTvShow,
            tvShowItem.imageThumbnailPath!!
        )

//        if (!tvShowItem.imageThumbnailPath.isNullOrEmpty()) {
//            if (tvShowItem.imageThumbnailPath?.startsWith("https", true) == true) {
//                FetchImageUrl.getImageURL(
//                    binding.tvMainData.imageTvShow,
//                    tvShowItem.imageThumbnailPath!!
//                )
//            } else {
//                val byteArray: ByteArray =
//                    Base64.decode(tvShowItem.imageThumbnailPath, Base64.DEFAULT)
//                binding.tvMainData.imageTvShow.setImageBitmap(
//                    BitmapFactory.decodeByteArray(
//                        byteArray,
//                        0,
//                        byteArray.size
//                    )
//                )
//
//
//            }
//
//        }

    }


    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            detailedTVViewModel.detailedTvShowSate.collect { state ->
                updateUI(state)
            }
        }
    }

    private fun updateUI(state: DetailedTvShowSate) {
        binding.tipProgressBar.isVisible = state.isLoading

        state.error?.let { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        state.detailedTvShowModel?.let { response ->
            renderTvShowDetails(response)
            initViewsOnClickListeners(response)

        }

    }

    private fun initViewsOnClickListeners(response: DetailedTvShowModel) {
        binding.buttonWebSite.setOnClickListener {
            if (!response.tvShow?.url.isNullOrEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(
                    response.tvShow?.url
                )
                startActivity(intent)
            } else {
                Toast.makeText(context, "not available", Toast.LENGTH_SHORT).show()
            }

        }

        binding.buttonEpisodes.setOnClickListener {
            if (!response.tvShow?.episodes.isNullOrEmpty()) {
                createEpisodeBottomSheet(response.tvShow?.episodes!!)

            } else {
                Toast.makeText(context, "not available", Toast.LENGTH_SHORT)
                    .show()

            }
        }
    }


    private fun renderTvShowDetails(response: DetailedTvShowModel) {
        // use clear syntax
        if (response.tvShow?.pictures?.isNotEmpty() == true) {
            initViewPAger(response.tvShow.pictures)
            binding.picturesViewPager.visibility = View.VISIBLE
            binding.linearSliderIndicators.visibility = View.VISIBLE
        } else {
            // we place the imageThumbnailPath to view pager
            initViewPAger(listOf(tvShowItem.imageThumbnailPath))
        }

        if (response.tvShow?.countdown.toString().isNotEmpty()) {
            binding.textRunTime.text = response.tvShow?.runtime.toString()
            binding.textRunTime.append(" Min")
        } else {
            binding.textRunTime.text = "not available"
        }


        if (response.tvShow?.rating?.isEmpty() == false) {
            binding.textrating.text = String.format(
                Locale.getDefault(),
                "%.2f", response.tvShow.rating.toDouble()
            )

        } else {
            binding.textrating.text = "not available"
        }

        if (response.tvShow?.genres?.isEmpty() == false) {
            binding.textGenres.text = response.tvShow.genres[0]
        } else {
            binding.textGenres.text = "not available"
        }

        // handle special characters
        if (!response.tvShow?.description.isNullOrEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.testDescription.text =
                    Html.fromHtml(response.tvShow?.description, Html.FROM_HTML_MODE_LEGACY)
            } else {
                response.tvShow?.description?.replace("<br>", "\n")
                    ?.replace("\r", "")?.replace("\t", "    ")
                    ?.replace("<b>", "**")?.replace("</b>", "**")
            }

        }

    }

    private fun createEpisodeBottomSheet(episodes: List<EpisodesItemModel>) {
        val bottomSheetDialog =
            context?.let { it1 -> BottomSheetDialog(it1) }
        val sheetDialogBinding =
            SheetDialogLayoutBinding.inflate(layoutInflater)
        sheetDialogBinding.topNAme.text = "${tvShowItem.name} Episodes"
        val adapter =
            EpisodesAdapter(episodes) //
        sheetDialogBinding.epRecView.layoutManager =
            LinearLayoutManager(context)
        sheetDialogBinding.epRecView.adapter = adapter
        bottomSheetDialog?.setContentView(sheetDialogBinding.root)
        bottomSheetDialog?.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}






