package com.financeflow.utils

import com.facebook.shimmer.Shimmer

object Constant {
    const val VIEW_TYPE_ITEM = 0
    const val VIEW_TYPE_LOADING = 1
    const val BASEIMAGEURL = "https://www.celebrityschool.in/images/artist_image/"
    const val BASEVIDEOURL = "https://www.celebrityschool.in/images/video_thumb/"
    const val BASELESSONURL = "https://www.celebrityschool.in/images/album_image/"

}

private val shimmer = Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
    .setDuration(1800) // how long the shimmering animation takes to do one full sweep
    .setBaseAlpha(0.7f) //the alpha of the underlying children
    .setHighlightAlpha(0.6f) // the shimmer alpha amount
    .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
    .setAutoStart(true)
    .build()