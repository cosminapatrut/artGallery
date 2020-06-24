package com.orange.artgallery.instances.modules

import androidx.room.Room
import com.orange.artgallery.gallery.GalleryViewModel
import com.orange.artgallery.gallery.detail.GalleryDetailViewModel
import com.orange.data.GalleryRepositoryImpl
import com.orange.data.RoomGalleryRepositoryImpl
import com.orange.data.room.GalleryDatabase
import com.orange.data.service.GalleryService
import com.orange.domain.repository.GalleryRepository
import com.orange.domain.repository.RoomGalleryRepository
import com.orange.domain.usecases.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val defaultModule = module {
    single {
        Room.databaseBuilder(
            get(),
            GalleryDatabase::class.java,
            "gallery.db"
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    single<GalleryService> {
        Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(GalleryService::class.java)
    }

    single<GalleryRepository> { GalleryRepositoryImpl(get()) }
    single<RoomGalleryRepository> { RoomGalleryRepositoryImpl(get()) }

    single<GetGalleryUseCase> { GetGalleryUseCaseImpl(get()) }
    single<GetGalleryDetailUseCase> { GetGalleryDetailUseCaseImpl(get()) }
    single<AddToFavoritesUseCase> { AddToFavoritesUseCasImpl(get()) }
    single<GetFavoritesUseCase> { GetFavoritesUseCaseImpl(get()) }
    single<IsInFavoritesUseCase> { IsInFavoritesUseCaseImpl(get()) }
    single<RemoveFavoriteUseCase> { RemoveFavoriteUseCaseImpl(get()) }

    single(named("IO_SCHEDULER")) { Schedulers.io() }
    single(named("MAIN_SCHEDULER")) { AndroidSchedulers.mainThread() }

    viewModel {
        GalleryViewModel(
            get(named("IO_SCHEDULER")),
            get(named("MAIN_SCHEDULER")),
            get()
        )
    }
    viewModel {
        GalleryDetailViewModel(
            get(named("IO_SCHEDULER")),
            get(named("MAIN_SCHEDULER")),
            get(), get(), get(), get()
        )
    }
}