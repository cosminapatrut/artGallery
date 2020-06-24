package com.orange.domain.usecases

import com.orange.domain.model.GalleryEntityModel
import com.orange.domain.repository.RoomGalleryRepository
import io.reactivex.Completable

class AddToFavoritesUseCasImpl(
    private val roomGalleryRepository: RoomGalleryRepository
) : AddToFavoritesUseCase {
    override fun addToFavorites(galleryEntityModel: GalleryEntityModel): Completable {
        return Completable.fromCallable {
            roomGalleryRepository.insertFavorites(galleryEntityModel)
        }
    }
}

interface AddToFavoritesUseCase {
    fun addToFavorites(galleryEntityModel: GalleryEntityModel): Completable
}