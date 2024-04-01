package com.jasmeet.cinemate

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class CineMateApplication:Application(),ImageLoaderFactory {

    override fun onCreate() {
        FirebaseApp.initializeApp(this)
        FirebaseAnalytics.getInstance(this)
        super.onCreate()
    }


    override fun newImageLoader(): ImageLoader {
        return ImageLoader(this)
            .newBuilder()
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache{

                MemoryCache.Builder(this)
                    .maxSizePercent(0.2)
                    .strongReferencesEnabled(true)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .maxSizePercent(0.03)
                    .directory(cacheDir)
                    .build()
            }
            .logger(DebugLogger())
            .build()

    }
}
