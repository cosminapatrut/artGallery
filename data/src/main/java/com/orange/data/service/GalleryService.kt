package com.orange.data.service

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GalleryService {

    @GET("cosminapatrut/artGalleryApi/master/db.json")
    fun getGallery(): Single<List<GalleryItemDTO>>

    @GET("cosminapatrut/artGalleryApi/master/{artworkId}.json")
    fun getGalleryDetail(
        @Path("artworkId")
        id: Long
    ): Single<GalleryDetailDTO>

}