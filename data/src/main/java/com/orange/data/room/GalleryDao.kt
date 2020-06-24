package com.orange.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GalleryDao {

    @Query("SELECT * FROM galleryentity")
    fun getAllFavorites(): List<GalleryEntity>

    @Insert
    fun addToFavorite(galleryEntity: GalleryEntity)

    @Query("DELETE FROM galleryentity WHERE objectId=:galleryObjectId")
    fun removeFromFavorites(galleryObjectId: Long)
}