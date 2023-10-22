package com.example.tvwatchseries.ui.adaptors

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tvwatchseries.data.model.TVShowEntity
import com.example.tvwatchseries.databinding.ItemContainerTvShowBinding

class FavTvShowsAdaptor(Listener:FavTvShowListener) :
    RecyclerView.Adapter<FavTvShowsAdaptor.FavTvHolder>(){
    private var favTvShows: ArrayList<TVShowEntity> = ArrayList()

    private lateinit var mListener:FavTvShowListener
    init {
        mListener=Listener
    }
    fun setFavList(f_shows:  List<TVShowEntity>) {
        favTvShows = f_shows as ArrayList<TVShowEntity>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavTvHolder {
        val binding =
            ItemContainerTvShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavTvHolder(binding)
    }

    override fun onBindViewHolder(holder: FavTvHolder, position: Int) {
        holder.binding.textName.text = favTvShows[position].f_name
        holder.binding.textStatus.text = favTvShows[position].f_status
        holder.binding.textStarted.text = favTvShows[position].f_startDate
        holder.binding.textNetwork.text = favTvShows[position].f_network
        holder.binding.imageTvShow.contentDescription=favTvShows[position].f_id
        holder.binding.imageTvShow.setImageBitmap(
            BitmapFactory.decodeByteArray(
                favTvShows[position].f_imageBytearray,
                0,
                favTvShows[position].f_imageBytearray.size
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