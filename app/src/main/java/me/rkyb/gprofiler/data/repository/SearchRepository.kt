package me.rkyb.gprofiler.data.repository

import me.rkyb.gprofiler.data.remote.response.UserSearchResponse
import me.rkyb.gprofiler.data.remote.source.SearchService
import me.rkyb.gprofiler.utils.Resource
import me.rkyb.gprofiler.utils.ResponseHandler
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor (
    private val service: SearchService,
    private val responseHandler: ResponseHandler) {

    suspend fun searchUsers(keyword: String): Resource<UserSearchResponse>{
        return try {
            val response = service.fetchUsers(keyword)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

}