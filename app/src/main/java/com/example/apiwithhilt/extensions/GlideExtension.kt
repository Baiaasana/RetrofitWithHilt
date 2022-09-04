package com.example.apiwithhilt.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.apiwithhilt.R

fun ImageView.setImage(url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide
            .with(context)
            .load(url)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(this)
    } else
        setImageResource(R.mipmap.ic_launcher)
}