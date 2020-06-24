package com.orange.domain.usecases

import com.orange.domain.repository.RoomGalleryRepository
import io.reactivex.Single

class IsInFavoritesUseCaseImpl(
    private val roomGalleryRepository: RoomGalleryRepository
) : IsInFavoritesUseCase {
    override fun isInFavorites(galleryEntityId: Long): Single<Boolean> {
        return Single.fromCallable {
            roomGalleryRepository.getFavorites().find { it.objectId == galleryEntityId } != null
        }
    }
}

interface IsInFavoritesUseCase {
    fun isInFavorites(galleryEntityId: Long): Single<Boolean>
}