package com.example.tvwatchseries.presentation.adaptors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.tvwatchseries.data.model.EpisodesItem
import com.example.tvwatchseries.databinding.EpisodeLayoutBinding

class EpisodesAdapter(epsList: List<EpisodesItem>) :
    RecyclerView.Adapter<EpisodesAdapter.EpisodeHolder>() {
    private var list = epsList

    init {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeHolder {
        val binding =
            EpisodeLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodeHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeHolder, position: Int) {
        holder.binding.airDate.text = "released:${list[position].airDate} "
        holder.binding.epName.text = list[position].name
        holder.binding.enpNumber.text = "S0${list[position].season}-EP0${list[position].episode}"

    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class EpisodeHolder(val binding: EpisodeLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

}