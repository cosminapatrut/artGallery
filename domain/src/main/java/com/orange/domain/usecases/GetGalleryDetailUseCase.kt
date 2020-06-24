package com.orange.domain.usecases

import com.orange.domain.model.GalleryDetailModel
import com.orange.domain.repository.GalleryRepository
import io.reactivex.Single

class GetGalleryDetailUseCaseImpl(
    private val galleryRepository: GalleryRepository
) : GetGalleryDetailUseCase {
    override fun getGalleryDetail(id: Long): Single<GalleryDetailModel> {
        return galleryRepository.getGalleryDetail(id)
    }
}

interface GetGalleryDetailUseCase {
    fun getGalleryDetail(id: Long): Single<GalleryDetailModel>
}