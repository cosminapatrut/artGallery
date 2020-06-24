package com.orange.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GalleryEntity::class], version = 1)
abstract class GalleryDatabase : RoomDatabase() {
    abstract fun galleryDao(): GalleryDao
}