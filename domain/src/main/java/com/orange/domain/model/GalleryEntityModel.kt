package com.orange.domain.model

data class GalleryEntityModel(
    val objectId: Long,
    val image: String?,
    val title: String?,
    val artistName: String?,
    val artistBio: String?,
    val dimensions: String?,
    val location: String?,
    val date: String?
)