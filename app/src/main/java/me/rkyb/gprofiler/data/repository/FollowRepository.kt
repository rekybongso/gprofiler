package me.rkyb.gprofiler.data.repository

import me.rkyb.gprofiler.data.remote.response.ItemsResponse
import me.rkyb.gprofiler.data.remote.source.UserService
import me.rkyb.gprofiler.utils.Resource
import me.rkyb.gprofiler.utils.ResponseHandler
import javax.inject.Inject

class FollowRepository @Inject constructor(
    private val service: UserService,
    private val responseHandler: ResponseHandler) {

    suspend fun getFollowData(username: String, isItFollowers: Boolean)
            : Resource<MutableList<ItemsResponse>> {

        return try {
                if (isItFollowers) {
                    responseHandler.handleSuccess(service.getUserFollowers(username))
                } else {
                    responseHandler.handleSuccess(service.getUserFollowing(username))
                }
            } catch (e: Exception) { responseHandler.handleException(e) }
    }

}