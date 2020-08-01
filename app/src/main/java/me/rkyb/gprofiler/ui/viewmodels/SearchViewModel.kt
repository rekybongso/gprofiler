package me.rkyb.gprofiler.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import me.rkyb.gprofiler.data.repository.SearchRepository
import me.rkyb.gprofiler.utils.Resource

class SearchViewModel @ViewModelInject constructor (
    private val repo: SearchRepository)
    : ViewModel() {

    private val username = MutableLiveData<String>()
    private val ioScope = viewModelScope.coroutineContext + Dispatchers.IO

    var dataFetched = username.switchMap { users ->
        liveData(context = ioScope) {
            emit(Resource.loading(null))
            emit(repo.searchUsers(users))
        }
    }

    fun searchUsers(keyword: String) {
        if (username.value == keyword) return
        username.value = keyword
    }
}