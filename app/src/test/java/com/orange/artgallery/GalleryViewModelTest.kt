package com.orange.artgallery

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.orange.artgallery.gallery.GalleryViewModel
import com.orange.domain.model.GalleryItem
import com.orange.domain.usecases.GetGalleryUseCase
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GalleryViewModelTest {
    private val testScheduler = TestScheduler()
    private val getGalleryUseCase = mock<GetGalleryUseCase>()
    private val galleryViewModel = GalleryViewModel(testScheduler, testScheduler, getGalleryUseCase)
    private val galleryItems = listOf(
        GalleryItem(1, "image", "gallery item 1")
    )

    @Test
    fun getDataShouldShowAndHideLoading() {
        whenever(getGalleryUseCase.getGallery()) doReturn Single.just(galleryItems)

        galleryViewModel.getData()

        assertNotNull(galleryViewModel.isLoading.value)
        galleryViewModel.isLoading.value?.let {
            assertTrue(it)
        }

        testScheduler.triggerActions()
        verify(getGalleryUseCase).getGallery()

        galleryViewModel.isLoading.value?.let {
            assertFalse(it)
        }
    }

    @Test
    fun `showError true when getGallery throws exception`() {
        whenever(getGalleryUseCase.getGallery()) doReturn Single.error(Throwable("OH NO 404!"))

        galleryViewModel.getData()

        testScheduler.triggerActions()

        galleryViewModel.showError.value?.let {
            assertTrue(it)
        }
    }
}