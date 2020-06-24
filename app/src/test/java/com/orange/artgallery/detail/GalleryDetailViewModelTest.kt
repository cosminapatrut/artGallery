package com.orange.artgallery.detail

import com.nhaarman.mockitokotlin2.*
import com.orange.artgallery.gallery.detail.GalleryDetailViewModel
import com.orange.domain.model.GalleryDetailModel
import com.orange.domain.model.GalleryEntityModel
import com.orange.domain.usecases.AddToFavoritesUseCase
import com.orange.domain.usecases.GetGalleryDetailUseCase
import com.orange.domain.usecases.IsInFavoritesUseCase
import com.orange.domain.usecases.RemoveFavoriteUseCase
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import junit.framework.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GalleryDetailViewModelTest {
    private val testScheduler = TestScheduler()
    private val getGalleryDetailUseCase: GetGalleryDetailUseCase = mock()
    private val addToFavoritesUseCase: AddToFavoritesUseCase = mock()
    private val removeFavoriteUseCase: RemoveFavoriteUseCase = mock()
    private val isInFavoritesUseCase: IsInFavoritesUseCase = mock()

    private val galleryDetailViewModel = GalleryDetailViewModel(
        testScheduler,
        testScheduler,
        getGalleryDetailUseCase,
        addToFavoritesUseCase,
        removeFavoriteUseCase,
        isInFavoritesUseCase
    )

    private val objectId = 1L
    @Test
    fun `getData should show and hide loading`() {
        whenever(getGalleryDetailUseCase.getGalleryDetail(objectId)) doReturn Single.just(
            GalleryDetailModel(
                objectId,
                "primary image",
                "title",
                "name",
                null,
                "15x15",
                "Bucharest",
                "2020"
            )
        )

        galleryDetailViewModel.getData(objectId)

        assertNotNull(galleryDetailViewModel.isLoading.value)
        galleryDetailViewModel.isLoading.value?.let {
            assertTrue(it)
        }

        testScheduler.triggerActions()

        verify(getGalleryDetailUseCase).getGalleryDetail(objectId)
        assertNotNull(galleryDetailViewModel.isLoading.value)
        galleryDetailViewModel.isLoading.value?.let {
            assertFalse(it)
        }
    }

    @Test
    fun `showError true when getGallery throws exception`() {
        whenever(getGalleryDetailUseCase.getGalleryDetail(objectId)) doReturn Single.error(
            Throwable("404")
        )

        galleryDetailViewModel.getData(objectId)

        testScheduler.triggerActions()

        assertNotNull(galleryDetailViewModel.showError.value)
        galleryDetailViewModel.showError.value?.let {
            assertTrue(it)
        }
    }

    @Test
    fun `add to favorites should set isFavorites to true`() {
        val galleryEntityModel = GalleryEntityModel(
            objectId,
            "primary image",
            "title",
            "name",
            null,
            "15x15",
            "Bucharest",
            "2020"
        )

        whenever(addToFavoritesUseCase.addToFavorites(galleryEntityModel)) doReturn Completable.complete()

        galleryDetailViewModel.addToFavorites(galleryEntityModel)

        testScheduler.triggerActions()

        assertNotNull(galleryDetailViewModel.isInFavorites.value)
        galleryDetailViewModel.isInFavorites.value?.let {
            assertTrue(it)
        }
        assertNull(galleryDetailViewModel.showError.value)
    }

    @Test
    fun `remove from favorites should set isFavorites to false`() {
        whenever(removeFavoriteUseCase.removeFromFavorites(objectId)) doReturn Completable.complete()

        galleryDetailViewModel.removeFavorite(objectId)

        testScheduler.triggerActions()

        assertNotNull(galleryDetailViewModel.isInFavorites.value)
        galleryDetailViewModel.isInFavorites.value?.let {
            assertFalse(it)
        }
        assertNull(galleryDetailViewModel.showError.value)
    }

    @Test
    fun `error on database should set showError true`() {
        val galleryEntityModel = GalleryEntityModel(
            objectId,
            "primary image",
            "title",
            "name",
            null,
            "15x15",
            "Bucharest",
            "2020"
        )
        whenever(addToFavoritesUseCase.addToFavorites(any())) doReturn Completable.error(
            Throwable("Some database error")
        )

        galleryDetailViewModel.addToFavorites(galleryEntityModel)

        testScheduler.triggerActions()

        assertNotNull(galleryDetailViewModel.showError.value)
        galleryDetailViewModel.showError.value?.let {
            assertTrue(it)
        }
    }
}