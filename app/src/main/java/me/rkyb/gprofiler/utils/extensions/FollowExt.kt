package me.rkyb.gprofiler.utils.extensions

import me.rkyb.gprofiler.R
import me.rkyb.gprofiler.databinding.FragmentFollowBinding

//Extensions to control Ui state on Follow Fragment
fun FragmentFollowBinding.onSuccess() {
    this.apply {
        rvFollowList.show()
        tvStatusNotice.hide()
    }
}

fun FragmentFollowBinding.onLoading() {
    this.apply {
        rvFollowList.hide()
        tvStatusNotice.show()
    }
}

fun FragmentFollowBinding.onError(message: String?){
    this.apply {
        rvFollowList.hide()
        tvStatusNotice.apply {
            show()
            text = message ?: resources.getString(R.string.no_data_notice)
        }
    }
}