package me.rkyb.gprofiler.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import me.rkyb.gprofiler.data.remote.response.ItemsResponse
import me.rkyb.gprofiler.data.repository.UserSearchRepository
import me.rkyb.gprofiler.utils.Resource

class UserSearchViewModel @ViewModelInject constructor (
    private val repo: UserSearchRepository)
    : ViewModel() {

    private val userName = MutableLiveData<String>()

    var dataFetched: LiveData<Resource<MutableList<ItemsResponse>>> = Transformations
        .switchMap(userName) {
            repo.searchUsers(it)
        }

    fun searchUsers(keyword: String? = null, getPopularUsers: Boolean = false) {
        if (userName.value == keyword){
            return
        }
        userName.value = keyword
    }
}