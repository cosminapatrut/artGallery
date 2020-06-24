package com.orange.data

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.orange.data.service.GalleryItemDTO
import com.orange.data.service.GalleryService
import com.orange.domain.model.GalleryItem
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Test

class GalleryRepositoryImplTest {
    private val galleryService = mock<GalleryService>()
    private val galleryRepositoryImpl = GalleryRepositoryImpl(galleryService)

    @Test
    fun getGallerySuccess() {
        val apiData = listOf(
            GalleryItemDTO(null, null, null),
            GalleryItemDTO(1, null, null),
            GalleryItemDTO(1, "primaryImage", "title")
        )

        val expectedData = listOf(
            GalleryItem(null, null, null),
            GalleryItem(1, null, null),
            GalleryItem(1, "primaryImage", "title")
        )

        whenever(galleryService.getGallery()) doReturn Single.just(apiData)

        val test = galleryRepositoryImpl.getGallery().test()

        test.assertValue(expectedData)
        test.assertComplete()
        test.assertNoErrors()
    }

    @Test
    fun getGalleryEmptyData() {
        val apiData = emptyList<GalleryItemDTO>()

        whenever(galleryService.getGallery()) doReturn Single.just(apiData)

        val test = galleryRepositoryImpl.getGallery().test()

        test.assertValue(emptyList())
        test.assertComplete()
        test.assertNoErrors()
    }

    @Test
    fun getGalleryError() {
        val apiError = Throwable("api error")

        whenever(galleryService.getGallery()) doReturn Single.error(apiError)

        val test = galleryRepositoryImpl.getGallery().test()

        // test.assertError(apiError) <- use this if you want
        assertEquals(test.errors()[0], apiError)
    }
}