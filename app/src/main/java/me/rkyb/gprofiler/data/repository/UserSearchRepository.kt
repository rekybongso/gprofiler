package me.rkyb.gprofiler.data.repository

import androidx.lifecycle.liveData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import me.rkyb.gprofiler.data.remote.source.UserSearchService
import me.rkyb.gprofiler.utils.Resource
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSearchRepository @Inject constructor (
    private val service: UserSearchService) {

    fun searchUsers(keyword: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                val result = service.fetchUsers(keyword)
                emit(Resource.success(result.items))
            } catch (e: Exception) {
                emit(Resource.error(e.message.toString(), null))
            }
        }
}