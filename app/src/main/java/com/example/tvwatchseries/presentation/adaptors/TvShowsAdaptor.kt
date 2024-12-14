package com.example.tvwatchseries.presentation.adaptors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tvwatchseries.databinding.ItemContainerTvShowBinding
import com.example.tvwatchseries.domain.model.TvShowsItemModel
import com.example.tvwatchseries.presentation.util.FetchImageUrl
import javax.inject.Inject

class TvShowsAdaptor @Inject constructor(private val tvListener: TvListener) :
    RecyclerView.Adapter<TvShowsAdaptor.ItemTVHolder>() {


    private var tvShows: MutableList<TvShowsItemModel> = mutableListOf()

    fun addList(list: List<TvShowsItemModel>) {
        val startPosition = tvShows.size
        tvShows.addAll(list)
        notifyItemRangeInserted(startPosition, list.size) // Notify only the added range
    }

    fun setList(list: MutableList<TvShowsItemModel>) {
        tvShows.clear()
        tvShows.addAll(list)
        notifyItemChanged(tvShows.size)
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
            tvShows[position].imageThumbnailPath ?: ""
        )

    }

    override fun getItemCount(): Int {
        return tvShows.size
    }


    inner class ItemTVHolder(val binding: ItemContainerTvShowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                tvListener.handleTVPress(
                    tvShows[layoutPosition]
                )
            }
        }
    }

    fun getItemByPosition(position: Int): TvShowsItemModel {
        val item = tvShows[position] // Store the item to be returned
        tvShows.removeAt(position)  // Remove the item from the list
        notifyItemRemoved(position) // Notify the adapter about the removal
        return item // Return the stored item
    }


    interface TvListener {
        fun handleTVPress(tvShowItem: TvShowsItemModel)
    }

}