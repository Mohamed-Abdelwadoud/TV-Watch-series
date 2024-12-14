package com.example.tvwatchseries.presentation.adaptors

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tvwatchseries.databinding.ItemPicLayoutBinding
import com.example.tvwatchseries.presentation.util.FetchImageUrl
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class PicturesAdaptor(picArray: List<String?>) :
    RecyclerView.Adapter<PicturesAdaptor.PicHolder>() {
    private var picList: List<String?> = picArray


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicHolder {
        val binding =
            ItemPicLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PicHolder(binding)
    }

    override fun onBindViewHolder(holder: PicHolder, position: Int) {
        if (picList[position]!!.startsWith("https", true)) {
            FetchImageUrl.getImageURL(holder.binding.imageShow, picList[position]!!)

        } else {
            val byteArray: ByteArray = Base64.decode(picList[position]!!, Base64.DEFAULT)
            holder.binding.imageShow.setImageBitmap(
                BitmapFactory.decodeByteArray(
                    byteArray,
                    0,
                    byteArray.size
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return picList.size
    }


    inner class PicHolder(val binding: ItemPicLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}