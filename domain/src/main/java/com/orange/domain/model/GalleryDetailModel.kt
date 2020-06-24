package com.orange.domain.model

data class GalleryDetailModel(
    val id: Long?,
    val image: String?,
    val title: String?,
    val artistDisplayName: String?,
    val artistDisplayBio: String?,
    val dimensions: String?,
    val location: String?,
    val date: String?
)