package com.orange.artgallery.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.orange.domain.model.GalleryItem
import com.orange.domain.usecases.GetGalleryUseCase
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class GalleryViewModel(
    private val executionScheduler: Scheduler,
    private val postExecutionScheduler: Scheduler,
    private val getGalleryUseCase: GetGalleryUseCase
) : ViewModel() {
    val data = MutableLiveData<List<GalleryItem>>()
    val isLoading = MutableLiveData<Boolean>()
    val showError = MutableLiveData<Boolean>()
    private val disposable: CompositeDisposable = CompositeDisposable()

    fun getData() {
        isLoading.value = true
        getGalleryUseCase.getGallery()
            .subscribeOn(executionScheduler)
            .observeOn(postExecutionScheduler)
            .subscribe(
                { responseItems ->
                    data.postValue(responseItems)
                    isLoading.value = false
                },
                {
                    showError.value = true
                    isLoading.value = false
                }
            ).addTo(disposable)
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}