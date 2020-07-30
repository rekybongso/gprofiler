package me.rkyb.gprofiler.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import kotlinx.coroutines.Dispatchers
import me.rkyb.gprofiler.data.repository.SearchRepository
import me.rkyb.gprofiler.utils.Resource

class SearchViewModel @ViewModelInject constructor (
    private val repo: SearchRepository)
    : ViewModel() {

    private val username = MutableLiveData<String>()

    var dataFetched = username.switchMap { users ->
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            emit(repo.searchUsers(users))
        }
    }

    fun searchUsers(keyword: String) {
        if (username.value == keyword) return
        username.value = keyword
    }
}