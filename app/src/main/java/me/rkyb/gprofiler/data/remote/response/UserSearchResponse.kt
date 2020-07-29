package me.rkyb.gprofiler.data.remote.response

import com.squareup.moshi.Json

data class UserSearchResponse (
    @field:Json(name = "incomplete_result")
    val isDataInComplete: Boolean?,
    @field:Json(name = "items")
    val items: MutableList<ItemsResponse>?,
    @field:Json(name = "total_count")
    val totalCount: Int?
)