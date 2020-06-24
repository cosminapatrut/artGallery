package com.orange.domain.usecases

import com.orange.domain.model.GalleryItem
import com.orange.domain.repository.GalleryRepository
import io.reactivex.Single

class GetGalleryUseCaseImpl(
    private val galleryRepository: GalleryRepository
) : GetGalleryUseCase {
    override fun getGallery(): Single<List<GalleryItem>> {
        return galleryRepository.getGallery()
            .toObservable()
            .flatMapIterable { it }
            .filter { it.objectID != null }
            .toList()
    }
}

interface GetGalleryUseCase {
    fun getGallery(): Single<List<GalleryItem>>
}