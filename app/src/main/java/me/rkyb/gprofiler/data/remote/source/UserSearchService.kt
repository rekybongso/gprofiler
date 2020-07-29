package me.rkyb.gprofiler.data.remote.source

import me.rkyb.gprofiler.data.remote.response.UserSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserSearchService {

    //Get users by provided keyword
    @GET("search/users")
    suspend fun fetchUsers(@Query("q") keyword: String): UserSearchResponse

}