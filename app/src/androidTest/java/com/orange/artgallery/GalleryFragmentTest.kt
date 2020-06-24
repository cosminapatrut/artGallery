package com.orange.artgallery

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.orange.artgallery.gallery.GalleryFragment
import com.orange.artgallery.gallery.GalleryViewModel
import com.orange.artgallery.rule.createRule
import com.orange.domain.model.GalleryItem
import com.orange.domain.usecases.GetGalleryUseCase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module

class GalleryFragmentTest {
    private val fragment = GalleryFragment()
    private val getGalleryUseCase: GetGalleryUseCase = mock()
    private val galleryViewModel = GalleryViewModel(
        Schedulers.io(),
        AndroidSchedulers.mainThread(),
        getGalleryUseCase
    )

    @get:Rule
    val fragmentRule = createRule(fragment, module {
        single(override = true) {
            galleryViewModel
        }
    })

    @Test
    fun testAdapterItems() {
        whenever(getGalleryUseCase.getGallery()) doReturn Single.just(
            listOf(
                GalleryItem(1L, "image", "gallery item 1")
            )
        )

        onView(withId(R.id.gallery_fragment_recycler_view))
            .check(matches(hasChildCount(1)))
    }

    @Test
    fun testEmptyAdapterItems() {
        whenever(getGalleryUseCase.getGallery()) doReturn Single.just(
            emptyList()
        )

        onView(withId(R.id.gallery_fragment_recycler_view))
            .check(matches(hasChildCount(0)))
    }
}