package com.orange.artgallery.instances

import android.app.Application
import androidx.room.Room
import com.orange.artgallery.instances.modules.defaultModule
import com.orange.data.GalleryRepositoryImpl
import com.orange.data.room.GalleryDatabase
import com.orange.data.service.GalleryService
import com.orange.domain.repository.GalleryRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Instances {

    fun init(application: Application) {
        startKoin {
            androidContext(application)
            loadKoinModules(defaultModule)
        }
    }
}