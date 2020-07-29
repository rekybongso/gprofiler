package me.rkyb.gprofiler.data.remote.response

import com.squareup.moshi.Json

data class ItemsResponse (
    @field:Json(name = "id")
    val userId: Int?,
    @field:Json(name = "login")
    val username: String?,
    @field:Json(name = "avatar_url")
    val userAvatar: String?,
    @field:Json(name = "type")
    val accountType: String?
)