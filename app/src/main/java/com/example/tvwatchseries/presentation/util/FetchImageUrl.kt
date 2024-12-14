package com.example.tvwatchseries.presentation.util

import android.util.Log
import android.widget.ImageView
import com.example.tvwatchseries.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

object FetchImageUrl {
     fun getImageURL(view: ImageView, url: String) {
        try {
            view.alpha = 0f
            Picasso.get().load(url).noFade()
                .into(view, object :
                    Callback {
                    override fun onSuccess() {
                        view.animate().setDuration(300).alpha(1f).start()
                    }

                    override fun onError(e: Exception?) {
                        view.setImageResource(R.drawable.ic_baseline_live_tv_24)

                    }
                })
        } catch (ignored: Exception) {
        }
    }






}