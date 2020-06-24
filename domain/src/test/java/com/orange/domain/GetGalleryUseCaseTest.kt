package com.orange.domain

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.orange.domain.model.GalleryItem
import com.orange.domain.repository.GalleryRepository
import com.orange.domain.usecases.GetGalleryUseCaseImpl
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Test

class GetGalleryUseCaseTest {
    private val galleryRepository = mock<GalleryRepository>()
    private val getGalleryUseCase = GetGalleryUseCaseImpl(galleryRepository)

    @Test
    fun getGallerySuccess_filtering_on_emptyData() {
        whenever(galleryRepository.getGallery()) doReturn Single.just(emptyList())

        val test = getGalleryUseCase.getGallery().test()

        test.assertValue(emptyList())
        assertEquals(test.values()[0].size, 0)
    }

    @Test
    fun getGallerySuccess_filtering() {
        val repoData = listOf(
            GalleryItem(null, null, null),
            GalleryItem(1, null, null),
            GalleryItem(2, null, null)
        )

        val expectedData = listOf(
            GalleryItem(1, null, null),
            GalleryItem(2, null, null)
        )

        whenever(galleryRepository.getGallery()) doReturn Single.just(repoData)

        val test = getGalleryUseCase.getGallery().test()

        test.assertValue(expectedData)
        assertEquals(test.values()[0].size, 2)
    }

    @Test
    fun onError_returnError() {
        val npe = NullPointerException("Custom message")
        whenever(galleryRepository.getGallery()) doReturn Single.error(npe)

        val test = getGalleryUseCase.getGallery().test()

        test.assertError(npe)
        assertEquals(test.errors()[0], npe)
        assertEquals(test.errors()[0].message, "Custom message")
        test.assertNotComplete()
    }
}