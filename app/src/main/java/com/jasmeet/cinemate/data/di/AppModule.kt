package com.jasmeet.cinemate.data.di

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jasmeet.cinemate.BuildConfig
import com.jasmeet.cinemate.data.apiService.ApiService
import com.jasmeet.cinemate.data.repository.PopularMoviesRepository
import com.jasmeet.cinemate.data.repository.UserRepository
import com.jasmeet.cinemate.data.repositoryImpl.PopularMoviesImpl
import com.jasmeet.cinemate.data.repositoryImpl.UserRepositoryImpl
import com.jasmeet.cinemate.domain.useCase.PopularMoviesUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth() : FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore() : FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun providesFirebaseAnalytics(@ApplicationContext context: Context) : FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(context)
    }

    @Provides
    fun provideUserRepository(auth: FirebaseAuth, db: FirebaseFirestore): UserRepository {
        return UserRepositoryImpl(auth, db)
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideApiService(moshi: Moshi): ApiService {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(
                Interceptor { chain->
                    val builder = chain.request().newBuilder()
                    builder.header("accept", "application/json")
                    builder.header("Authorization", "Bearer ${BuildConfig.bearerToken} ")
                    return@Interceptor chain.proceed(builder.build())
                }
            )
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePopularMoviesRepository(apiService: ApiService): PopularMoviesRepository =
        PopularMoviesImpl(apiService)

    @Provides
    @Singleton
    fun providesPopularMoviesUseCase(repository: PopularMoviesRepository): PopularMoviesUseCase =
        PopularMoviesUseCase(repository)
}



