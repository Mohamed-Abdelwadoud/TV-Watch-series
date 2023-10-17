package com.example.tvwatchseries.ui.adaptors

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tvwatchseries.data.model.TvShowsItem
import com.example.tvwatchseries.databinding.ItemContainerTvShowBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.jetbrains.annotations.NotNull

class MostPopularTvShowsAdaptor : RecyclerView.Adapter<MostPopularTvShowsAdaptor.ItemTVHolder>() {

    private var tvShows: ArrayList<TvShowsItem> = ArrayList()

    fun addList(list: ArrayList<TvShowsItem>?) {
            if (tvShows.isEmpty()){
                    tvShows = list!!
                Log.d("GGGGGGGGGGGGGGGg", "addList: A ")
            }else{
                    tvShows.addAll(list!!)
                Log.d("GGGGGGGGGGGGGGGg", "addList: B ")

            }

        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemTVHolder {
        val binding =
            ItemContainerTvShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemTVHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemTVHolder, position: Int) {

        holder.binding.textName.text = tvShows?.get(position)?.name
        holder.binding.textStatus.text = tvShows?.get(position)?.status
        holder.binding.textStarted.text = tvShows?.get(position)?.startDate
        holder.binding.textNetwork.text = tvShows?.get(position)?.network
        try {
            holder.binding.imageTvShow.alpha = 0f
            Picasso.get().load(tvShows?.get(position)?.imageThumbnailPath).noFade().into(holder.binding.imageTvShow, object :
                Callback {
                override fun onSuccess() {
                    holder.binding.imageTvShow.animate().setDuration(300).alpha(1f).start()
                    Log.d("GGGGGGGGGGGGGGGg", "onSuccess: ")
                }

                override fun onError(e: Exception?) {
                    if (e != null) {
                        Log.d("GGGGGGGGGGGGGGGg", "onError:${e.message} ")
                    }
                }
            })
        } catch (ignored: Exception) {
            Log.d("GGGGGGGGGGGGGGGg", "onBindViewHolderException : ${ignored.message}")
        }

    }

    override fun getItemCount(): Int {
        return tvShows?.size ?: 0
    }


    inner class ItemTVHolder(val binding: ItemContainerTvShowBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }

}