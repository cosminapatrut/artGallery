package com.orange.artgallery.gallery.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.orange.domain.model.GalleryDetailModel
import com.orange.domain.model.GalleryEntityModel
import com.orange.domain.usecases.AddToFavoritesUseCase
import com.orange.domain.usecases.GetGalleryDetailUseCase
import com.orange.domain.usecases.IsInFavoritesUseCase
import com.orange.domain.usecases.RemoveFavoriteUseCase
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class GalleryDetailViewModel(
    private val executionScheduler: Scheduler,
    private val postExecutionScheduler: Scheduler,
    private val getGalleryDetailUseCase: GetGalleryDetailUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val isInFavoritesUseCase: IsInFavoritesUseCase
) : ViewModel() {
    val data = MutableLiveData<GalleryDetailModel>()
    val isInFavorites = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()
    val showError = MutableLiveData<Boolean>()

    private val disposable: CompositeDisposable = CompositeDisposable()

    fun getData(objectId: Long) {
        isLoading.value = true
        getGalleryDetailUseCase.getGalleryDetail(objectId)
            .subscribeOn(executionScheduler)
            .observeOn(postExecutionScheduler)
            .subscribe(
                { galleryDetail ->
                    data.postValue(galleryDetail)
                    isLoading.value = false
                },
                {
                    showError.value = true
                    isLoading.value = false
                }
            ).addTo(disposable)
    }

    fun addToFavorites(galleryEntityModel: GalleryEntityModel) {
        addToFavoritesUseCase.addToFavorites(galleryEntityModel)
            .subscribeOn(executionScheduler)
            .observeOn(postExecutionScheduler)
            .subscribe({
                isInFavorites.value = true
            }, {
                showError.value = true
            }).addTo(disposable)
    }

    fun removeFavorite(id: Long) {
        removeFavoriteUseCase.removeFromFavorites(id)
            .subscribeOn(executionScheduler)
            .observeOn(postExecutionScheduler)
            .subscribe({
                isInFavorites.value = false
            }, {
                showError.value = true
            }).addTo(disposable)
    }

    fun isItemInFavorites(id: Long) {
        isInFavoritesUseCase.isInFavorites(id)
            .subscribeOn(executionScheduler)
            .observeOn(postExecutionScheduler)
            .subscribe({
                isInFavorites.value = it
            }, {
                showError.value = true
            }).addTo(disposable)
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}