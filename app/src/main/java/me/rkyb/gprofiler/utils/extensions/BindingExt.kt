package me.rkyb.gprofiler.utils.extensions

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.Coil
import coil.request.CachePolicy
import coil.request.LoadRequest
import me.rkyb.gprofiler.R

// Load and bind image
@BindingAdapter("imageUrl")
fun ImageView.loadImage (url: String?) {

    val circularProgress =
        CircularProgressDrawable(this.context).apply {
            setColorSchemeColors(R.color.grey_600)
            strokeWidth = 5f
            centerRadius = 30f
            start()
        }

    val imageLoader = Coil.imageLoader(this.context)
    val imageRequest = LoadRequest.Builder(this.context)
        .data(url)
        .target(this)
        .diskCachePolicy(CachePolicy.ENABLED)
        .placeholder(circularProgress)
        .error(R.drawable.ic_error)
        .crossfade(true)
        .build()

    imageLoader.execute(imageRequest)
}