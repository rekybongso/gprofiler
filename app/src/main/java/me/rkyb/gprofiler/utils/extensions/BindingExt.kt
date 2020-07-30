package me.rkyb.gprofiler.utils.extensions

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.api.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import me.rkyb.gprofiler.R

// Load and bind image
@BindingAdapter("imageUrl")
fun ImageView.loadImage (url: String?) {
    val circleProgressDrawable = CircularProgressDrawable(context).apply {
        setColorSchemeColors(R.color.grey_700)
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }

    this.load(url){
        diskCachePolicy(CachePolicy.ENABLED)
        placeholder(circleProgressDrawable)
        error(R.drawable.ic_error)
        crossfade(true)
    }
}

// Load and bind as circular image
@BindingAdapter("circleImage")
fun ImageView.loadAsCircularImage(url: String?){
    this.load(url){
        diskCachePolicy(CachePolicy.ENABLED)
        error(R.drawable.ic_error)
        crossfade(true)
        transformations(CircleCropTransformation())
    }
}


