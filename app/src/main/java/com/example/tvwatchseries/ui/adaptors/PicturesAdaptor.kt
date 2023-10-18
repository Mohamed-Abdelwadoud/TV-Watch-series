package com.example.tvwatchseries.ui.adaptors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tvwatchseries.databinding.ItemPicLayoutBinding
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
        try {
            holder.binding.imageShow.alpha = 0f
            Picasso.get().load(picList[position]).noFade().into(holder.binding.imageShow, object :
                Callback {
                override fun onSuccess() {
                    holder.binding.imageShow.animate().setDuration(300).alpha(1f).start()
                }

                override fun onError(e: Exception?) {

                }
            })
        } catch (ignored: Exception) {
        }


    }

    override fun getItemCount(): Int {
        return picList.size
    }


    inner class PicHolder(val binding: ItemPicLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}