package me.rkyb.gprofiler.data.remote.response

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserProfileResponse(
    @field:Json(name = "avatar_url")
    val userAvatarUrl: String?,
    @field:Json(name = "company")
    val userCompany: String?,
    @field:Json(name = "followers")
    val userTotalFollowers: Int?,
    @field:Json(name = "followers_url")
    val userFollowersUrl: String?,
    @field:Json(name = "following")
    val userTotalFollowing: Int?,
    @field:Json(name = "following_url")
    val userFollowingUrl: String?,
    @field:Json(name = "location")
    val userLocation: String?,
    @field:Json(name = "name")
    val userFullName: String?,
    @field:Json(name = "login")
    val username: String?
): Parcelable