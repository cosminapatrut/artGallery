package com.orange.domain.repository

import com.orange.domain.model.GalleryDetailModel
import com.orange.domain.model.GalleryItem
import io.reactivex.Single

interface GalleryRepository {
    fun getGallery(): Single<List<GalleryItem>>
    fun getGalleryDetail(id: Long): Single<GalleryDetailModel>
}