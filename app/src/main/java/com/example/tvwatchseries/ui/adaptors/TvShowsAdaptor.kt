package com.example.tvwatchseries.ui.adaptors

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tvwatchseries.data.model.TvShowsItem
import com.example.tvwatchseries.databinding.ItemContainerTvShowBinding
import com.example.tvwatchseries.util.FetchImageUrl

class TvShowsAdaptor(tvListener: TvListener) :
    RecyclerView.Adapter<TvShowsAdaptor.ItemTVHolder>() {
    private var mListener: TvListener

    init {
        mListener = tvListener
    }

    private var tvShows: ArrayList<TvShowsItem> = ArrayList()

    fun addList(list: ArrayList<TvShowsItem>?) {
        if (tvShows.isEmpty()) {
            tvShows = list!!
        } else {
            tvShows.addAll(list!!)
        }
        notifyDataSetChanged()
    }

    fun setList(list: ArrayList<TvShowsItem>) {
        tvShows = list!!
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemTVHolder {
        val binding =
            ItemContainerTvShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemTVHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemTVHolder, position: Int) {

        holder.binding.textName.text = tvShows[position].name
        holder.binding.textStatus.text = tvShows[position].status
        holder.binding.textStarted.text = tvShows[position].startDate
        holder.binding.textNetwork.text = tvShows[position].network
        holder.binding.imageTvShow.contentDescription = "${tvShows[position].id}"
        FetchImageUrl.getImageURL(
            holder.binding.imageTvShow,
            tvShows[position].imageThumbnailPath!!
        )
    }

    override fun getItemCount(): Int {
        return tvShows.size
    }


    inner class ItemTVHolder(val binding: ItemContainerTvShowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener(View.OnClickListener {
                mListener.handleTVPress(
                    tvShows[layoutPosition]
                )
            })
        }
    }


    interface TvListener {
        fun handleTVPress(ClickedShow: TvShowsItem)
    }

}