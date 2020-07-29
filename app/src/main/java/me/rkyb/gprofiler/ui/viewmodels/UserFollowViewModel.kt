package me.rkyb.gprofiler.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import kotlinx.coroutines.Dispatchers
import me.rkyb.gprofiler.data.repository.FollowRepository
import me.rkyb.gprofiler.utils.enum.FollowType
import me.rkyb.gprofiler.utils.enum.FollowType.*

class UserFollowViewModel @ViewModelInject constructor(
    private val repo: FollowRepository): ViewModel() {

    private val username = MutableLiveData<String>()

    private lateinit var type: FollowType

    val getFollowData = username.switchMap { user ->
        liveData(Dispatchers.IO){
            when (type) {
                FOLLOWERS -> emit(repo.geUserFollowers(user))
                FOLLOWING -> emit(repo.geUserFollowing(user))
            }
        }
    }

    fun setFollowData (user: String, followType: FollowType) {
        if (username.value == user) return

        username.value = user
        type = followType
    }
}