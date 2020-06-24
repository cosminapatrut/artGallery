package com.orange.artgallery.util

import android.content.Context

fun Context.readAssetFile(fileName: String): String? {
    return try {
        val inputStream = assets.open(fileName)
        val inputString = inputStream.bufferedReader().use { it.readText() }
        inputString
    } catch (e: Exception) {
        null
    }
}