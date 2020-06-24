package com.orange.domain.usecases

import com.orange.domain.model.GalleryEntityModel
import com.orange.domain.repository.RoomGalleryRepository
import io.reactivex.Single

class GetFavoritesUseCaseImpl(
    private val roomGalleryRepository: RoomGalleryRepository
) : GetFavoritesUseCase {
    override fun getFavorites(): Single<List<GalleryEntityModel>> {
        return Single.fromCallable {
            roomGalleryRepository.getFavorites()
        }
    }
}

interface GetFavoritesUseCase {
    fun getFavorites(): Single<List<GalleryEntityModel>>
}