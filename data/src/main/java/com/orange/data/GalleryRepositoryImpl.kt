package com.orange.data

import com.orange.data.service.GalleryDetailDTO
import com.orange.data.service.GalleryService
import com.orange.domain.model.GalleryDetailModel
import com.orange.domain.model.GalleryItem
import com.orange.domain.repository.GalleryRepository
import io.reactivex.Single

class GalleryRepositoryImpl(private val service: GalleryService) : GalleryRepository {
    override fun getGallery(): Single<List<GalleryItem>> {
        return service.getGallery()
            .toObservable()
            .flatMapIterable { it }
            .map { galleryItemDTO ->
                GalleryItem(
                    galleryItemDTO.objectID,
                    galleryItemDTO.primaryImage,
                    galleryItemDTO.title
                )
            }
            .toList()
    }

    override fun getGalleryDetail(id: Long): Single<GalleryDetailModel> {
        return service.getGalleryDetail(id)
            .toObservable()
            .map { galleryDetailDTO: GalleryDetailDTO ->
                GalleryDetailModel(
                    id = galleryDetailDTO.objectID,
                    image = galleryDetailDTO.primaryImage,
                    title = galleryDetailDTO.title,
                    artistDisplayBio = galleryDetailDTO.artistDisplayBio,
                    artistDisplayName = galleryDetailDTO.artistDisplayName,
                    dimensions = galleryDetailDTO.dimensions,
                    location = galleryDetailDTO.location,
                    date = galleryDetailDTO.date
                )
            }.singleOrError()
    }
}