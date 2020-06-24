package com.orange.data.service

import com.google.gson.annotations.SerializedName

data class GalleryDetailDTO(
    val objectID: Long?,
    val primaryImage: String?,
    val title: String?,
    val artistDisplayName: String?,
    val artistDisplayBio: String?,
    val dimensions: String?,
    @SerializedName("repository")
    val location: String?,
    @SerializedName("objectBeginDate")
    val date: String?
)