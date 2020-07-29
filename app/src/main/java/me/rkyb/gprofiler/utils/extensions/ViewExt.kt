package me.rkyb.gprofiler.utils.extensions

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.Coil
import coil.request.CachePolicy
import coil.request.LoadRequest
import me.rkyb.gprofiler.R

object ViewExt {
    // Show selected view
    fun View.show(): View {
        if (visibility != View.VISIBLE) {
            visibility = View.VISIBLE
        }
        return this
    }

    // Hide selected view
    fun View.hide(): View {
        if (visibility != View.INVISIBLE) {
            visibility = View.INVISIBLE
        }
        return this
    }

    // Change navigation to selected NavDirection based on NavController
    fun View.doNavigate(direction: NavDirections) {
        Navigation.findNavController(this).navigate(direction)
    }
}



