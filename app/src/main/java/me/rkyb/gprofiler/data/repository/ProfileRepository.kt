package me.rkyb.gprofiler.data.repository

import me.rkyb.gprofiler.data.remote.response.UserProfileResponse
import me.rkyb.gprofiler.data.remote.source.UserService
import me.rkyb.gprofiler.utils.Resource
import me.rkyb.gprofiler.utils.ResponseHandler
import java.lang.Exception
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val responseHandler: ResponseHandler,
    private val service: UserService) {

    suspend fun geUserDetail(username: String): Resource<UserProfileResponse> {
        return try {
            val response = service.getUserDetail(username)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}