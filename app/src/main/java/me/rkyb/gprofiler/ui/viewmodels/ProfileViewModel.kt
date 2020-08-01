package me.rkyb.gprofiler.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.rkyb.gprofiler.data.remote.response.UserProfileResponse
import me.rkyb.gprofiler.data.repository.ProfileRepository
import me.rkyb.gprofiler.utils.Resource

class ProfileViewModel @ViewModelInject constructor (
    private val repo: ProfileRepository
): ViewModel() {

    val usersFetched = MutableLiveData<Resource<UserProfileResponse>>()

    fun getUserData(username: String) {
         viewModelScope.launch(Dispatchers.IO) {
             usersFetched.postValue(repo.geUserDetail(username))
         }
    }

}

