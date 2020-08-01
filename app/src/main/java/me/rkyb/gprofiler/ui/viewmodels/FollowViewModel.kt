package me.rkyb.gprofiler.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import me.rkyb.gprofiler.data.repository.FollowRepository
import me.rkyb.gprofiler.utils.enum.FollowType
import me.rkyb.gprofiler.utils.enum.FollowType.FOLLOWERS

class FollowViewModel @ViewModelInject constructor(
    private val repo: FollowRepository): ViewModel() {

    private val ioScope = viewModelScope.coroutineContext + Dispatchers.IO

    private val username = MutableLiveData<String>()
    private lateinit var type: FollowType

    val getFollowData = username.switchMap { user ->
        liveData(context = ioScope){
            if (type == FOLLOWERS){
                emit(repo.getFollowData(user, true))
            } else {
                emit(repo.getFollowData(user, false))
            }
        }

    }

    fun setFollowData (user: String, followType: FollowType) {
        if (username.value == user) return

        username.value = user
        type = followType
    }
}