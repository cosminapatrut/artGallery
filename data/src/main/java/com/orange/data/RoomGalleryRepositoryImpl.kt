package com.orange.data

import com.orange.data.room.GalleryDatabase
import com.orange.data.room.GalleryEntity
import com.orange.domain.model.GalleryEntityModel
import com.orange.domain.repository.RoomGalleryRepository

class RoomGalleryRepositoryImpl(
    private val galleryDatabase: GalleryDatabase
) : RoomGalleryRepository {

    override fun getFavorites(): List<GalleryEntityModel> {
        return galleryDatabase.galleryDao().getAllFavorites().map {
            GalleryEntityModel(
                it.objectId,
                it.primaryImage,
                it.title,
                it.artistDisplayName,
                it.artistDisplayBio,
                it.dimensions,
                it.location,
                it.date
            )
        }
    }

    override fun insertFavorites(galleryEntityModel: GalleryEntityModel) {
        galleryDatabase.galleryDao().addToFavorite(
            GalleryEntity(
                galleryEntityModel.objectId,
                galleryEntityModel.image,
                galleryEntityModel.title,
                galleryEntityModel.artistName,
                galleryEntityModel.artistBio,
                galleryEntityModel.dimensions,
                galleryEntityModel.location,
                galleryEntityModel.date
            )
        )
    }

    override fun removeFavorites(galleryEntityId: Long) {
        galleryDatabase.galleryDao().removeFromFavorites(galleryEntityId)
    }

}