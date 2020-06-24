package com.orange.domain.repository

import com.orange.domain.model.GalleryEntityModel

interface RoomGalleryRepository {
    fun getFavorites(): List<GalleryEntityModel>

    fun insertFavorites(galleryEntityModel: GalleryEntityModel)

    fun removeFavorites(galleryEntityId: Long)
}