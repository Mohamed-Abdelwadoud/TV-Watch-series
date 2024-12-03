package com.example.tvwatchseries.presentation.adaptors

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tvwatchseries.data.model.TVShowEntity
import com.example.tvwatchseries.databinding.ItemContainerTvShowBinding

class FavTvShowsAdaptor(listener: FavTvShowListener) :
    RecyclerView.Adapter<FavTvShowsAdaptor.FavTvHolder>(){
    private var favTvShows: ArrayList<TVShowEntity> = ArrayList()

    private var mListener: FavTvShowListener = listener
    fun setFavList(tvShowEntityList:  List<TVShowEntity>) {
        favTvShows = tvShowEntityList as ArrayList<TVShowEntity>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavTvHolder {
        val binding =
            ItemContainerTvShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavTvHolder(binding)
    }

    override fun onBindViewHolder(holder: FavTvHolder, position: Int) {
        holder.binding.textName.text = favTvShows[position].showName
        holder.binding.textStatus.text = favTvShows[position].showStatus
        holder.binding.textStarted.text = favTvShows[position].showStartDate
        holder.binding.textNetwork.text = favTvShows[position].showNetwork
        holder.binding.imageTvShow.contentDescription=favTvShows[position].showID
        holder.binding.imageTvShow.setImageBitmap(
            BitmapFactory.decodeByteArray(
                favTvShows[position].showImageBytearray,
                0,
                favTvShows[position].showImageBytearray.size
            )
        )
    }

    override fun getItemCount(): Int {
        return favTvShows.size
    }


    inner class FavTvHolder(val binding: ItemContainerTvShowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener(View.OnClickListener {
                mListener.onClick(favTvShows[layoutPosition])
            })
        }
    }

    interface FavTvShowListener{
        fun onClick(tvShowEntity: TVShowEntity)
    }


}