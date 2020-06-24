package com.orange.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class GalleryEntity(
    @PrimaryKey
    @NotNull
    val objectId: Long,
    @ColumnInfo(name = "image") val primaryImage: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "artistDisplayName") val artistDisplayName: String?,
    @ColumnInfo(name = "artistDisplayBio") val artistDisplayBio: String?,
    @ColumnInfo(name = "dimensions") val dimensions: String?,
    @ColumnInfo(name = "location") val location: String?,
    @ColumnInfo(name = "date") val date: String?
)