package com.example.tvwatchseries.ui.adaptors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tvwatchseries.databinding.ItemPicLayoutBinding
import com.example.tvwatchseries.util.FetchImageUrl
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class PicturesAdaptor(picArray: ArrayList<String>) :
    RecyclerView.Adapter<PicturesAdaptor.PicHolder>() {
    private var picList: ArrayList<String> = picArray


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicHolder {
        val binding =
            ItemPicLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PicHolder(binding)
    }

    override fun onBindViewHolder(holder: PicHolder, position: Int) {
        FetchImageUrl.getImageURL(holder.binding.imageShow, picList[position])
    }

    override fun getItemCount(): Int {
        return picList.size
    }


    inner class PicHolder(val binding: ItemPicLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}