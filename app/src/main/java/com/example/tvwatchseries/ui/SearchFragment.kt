package com.example.tvwatchseries.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.addTextChangedListener
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        initUI()

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isNotEmpty() == true) {
                    binding.clearIcon.visibility = View.VISIBLE
                    getResult(s.toString())
                } else if (s?.isEmpty() == true) {
                    binding.clearIcon.visibility = View.INVISIBLE

                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.searchEditText.setOnEditorActionListener { textView, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (binding.searchEditText.text.toString().trim().isNotEmpty()) {
                    getResult(binding.searchEditText.text.toString().trim())
                    binding.loadingBar.visibility = View.VISIBLE
                    binding.tvShowsRecyclerView.visibility = View.GONE
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.searchImage.setOnClickListener {
            input = binding.searchEditText.text.toString().trim()
            if (input.isNotEmpty()) {
                getResult(input)
                binding.loadingBar.visibility = View.VISIBLE
                binding.tvShowsRecyclerView.visibility = View.GONE
            }
        }

        binding.clearIcon.setOnClickListener {
            binding.searchEditText.text.clear()
        }


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
                if (!response.tvShows.isNullOrEmpty()) {
                    tvShowsAdaptor.setList((response.tvShows as ArrayList<TvShowsItem>?)!!)
                    binding.tvShowsRecyclerView.visibility = View.VISIBLE
                }else{
                    Toast.makeText(context,"Not Found",Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context,"Connection Lost",Toast.LENGTH_SHORT).show()
            }
            binding.loadingBar.visibility = View.GONE

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
    override fun handleTVPress(ClickedShow: TvShowsItem) {
        Log.d("DDDDDDDDDDDDDDD", "handleTVPress: ${ClickedShow.id}")
        Navigation.findNavController(requireView()).navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailedTvShowFragment(
                ClickedShow
            )
        )
    }


}