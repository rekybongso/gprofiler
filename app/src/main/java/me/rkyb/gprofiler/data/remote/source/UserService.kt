package me.rkyb.gprofiler.data.remote.source

import retrofit2.http.GET
import retrofit2.http.Path
import me.rkyb.gprofiler.data.remote.response.UserProfileResponse
import me.rkyb.gprofiler.data.remote.response.ItemsResponse

interface UserService {

    // https://api.github.com/users/username
    @GET("users/{username}")
    suspend fun getUserDetail(@Path("username") username: String)
            : UserProfileResponse

    // https://api.github.com/users/username/followers
    @GET("users/{username}/followers")
    suspend fun getUserFollowers(@Path("username") username: String)
            : MutableList<ItemsResponse>

    // https://api.github.com/users/username/following
    @GET("users/{username}/following")
    suspend fun getUserFollowing(@Path("username") username: String)
            : MutableList<ItemsResponse>
}