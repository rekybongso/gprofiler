package me.rkyb.gprofiler.utils.extensions

import me.rkyb.gprofiler.R
import me.rkyb.gprofiler.databinding.FragmentSearchBinding
import me.rkyb.gprofiler.utils.extensions.ViewExt.hide
import me.rkyb.gprofiler.utils.extensions.ViewExt.show

//Extensions to control Ui state
object SearchExt {
    fun FragmentSearchBinding.onSuccess() {
        this.apply {
            rvUserList.show()
            tvStatusNotice.hide()
            progressBar.hide()
        }
    }

    fun FragmentSearchBinding.onLoading() {
        this.apply {
            rvUserList.hide()
            tvStatusNotice.hide()
            progressBar.show()
        }
    }

    fun FragmentSearchBinding.onError(message: String?){
        this.apply {
            rvUserList.hide()
            progressBar.hide()
            tvStatusNotice.apply {
                show()
                text = message ?: resources.getString(R.string.user_not_found_notice)
            }
        }
    }
}