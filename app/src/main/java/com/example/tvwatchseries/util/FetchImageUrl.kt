package com.example.tvwatchseries.util

import android.widget.ImageView
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

                    }
                })
        } catch (ignored: Exception) {
        }
    }






}