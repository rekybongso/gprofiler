package me.rkyb.gprofiler.data.remote.source

import me.rkyb.gprofiler.data.remote.response.UserSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    // https://api.github.com/search/users?q=keyword
    @GET("search/users")
    suspend fun fetchUsers(@Query("q") keyword: String): UserSearchResponse

}