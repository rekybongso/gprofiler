package me.rkyb.gprofiler.utils.extensions

import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.Navigation

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


