package me.rkyb.gprofiler.data.repository

import me.rkyb.gprofiler.data.remote.response.ItemsResponse
import me.rkyb.gprofiler.data.remote.response.UserProfileResponse
import me.rkyb.gprofiler.data.remote.source.UserService
import me.rkyb.gprofiler.utils.Resource
import me.rkyb.gprofiler.utils.ResponseHandler
import java.lang.Exception
import javax.inject.Inject

class FollowRepository @Inject constructor(
    private val service: UserService,
    private val responseHandler: ResponseHandler) {

    suspend fun geUserFollowing(username: String): Resource<MutableList<ItemsResponse>> {
        return try {
            val response = service.getUserFollowing(username)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun geUserFollowers(username: String): Resource<MutableList<ItemsResponse>> {
        return try {
            val response = service.getUserFollowers(username)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

}