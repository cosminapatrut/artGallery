package com.orange.domain.usecases

import com.orange.domain.repository.RoomGalleryRepository
import io.reactivex.Completable

class RemoveFavoriteUseCaseImpl(
    private val roomGalleryRepository: RoomGalleryRepository
) : RemoveFavoriteUseCase {
    override fun removeFromFavorites(galleryEntityId: Long): Completable {
        return Completable.fromCallable {
            roomGalleryRepository.removeFavorites(galleryEntityId)
        }
    }
}

interface RemoveFavoriteUseCase {
    fun removeFromFavorites(galleryEntityId: Long): Completable
}