package com.example.myrestaurants.utils

import android.widget.ImageView
import coil.load
import coil.size.Scale
import com.example.myrestaurants.R


class AppUtility

fun loadImage(imageView: ImageView, imageUrl: String) {
    imageView.load(imageUrl) {
        placeholder(R.drawable.ic_place_holder)
        error(R.drawable.ic_place_holder)
        scale(Scale.FILL)
    }
}

fun loadImage(imageView: ImageView, imageResource: Int) {
    imageView.load(imageResource) {
        imageView.load(R.drawable.ic_place_holder)
        error(R.drawable.ic_place_holder)
    }
}
