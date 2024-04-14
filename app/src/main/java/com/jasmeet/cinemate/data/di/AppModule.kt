package com.jasmeet.cinemate.data.di

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jasmeet.cinemate.data.apiService.ApiService
import com.jasmeet.cinemate.data.repository.UserRepository
import com.jasmeet.cinemate.data.repository.movies.MovieDetailsRepository
import com.jasmeet.cinemate.data.repository.movies.NowPlayingMoviesRepository
import com.jasmeet.cinemate.data.repository.movies.TopRatedMoviesRepository
import com.jasmeet.cinemate.data.repository.movies.TrendingMoviesRepository
import com.jasmeet.cinemate.data.repository.movies.UpcomingMovieRepository
import com.jasmeet.cinemate.data.repository.series.AiringTodayRepository
import com.jasmeet.cinemate.data.repository.series.SeriesDetailsRepository
import com.jasmeet.cinemate.data.repository.series.TopRatedSeriesRepository
import com.jasmeet.cinemate.data.repository.series.TrendingSeriesRepository
import com.jasmeet.cinemate.data.repositoryImpl.UserRepositoryImpl
import com.jasmeet.cinemate.data.repositoryImpl.movies.MovieDetailsRepositoryImpl
import com.jasmeet.cinemate.data.repositoryImpl.movies.NowPlayingRepositoryImpl
import com.jasmeet.cinemate.data.repositoryImpl.movies.TopRatedMoviesImpl
import com.jasmeet.cinemate.data.repositoryImpl.movies.TrendingMoviesImpl
import com.jasmeet.cinemate.data.repositoryImpl.movies.UpcomingMoviesRepositoryImpl
import com.jasmeet.cinemate.data.repositoryImpl.series.AiringTodayRepositoryImpl
import com.jasmeet.cinemate.data.repositoryImpl.series.SeriesDetailsRepositoryImpl
import com.jasmeet.cinemate.data.repositoryImpl.series.TopRatedSeriesSeriesRepositoryImpl
import com.jasmeet.cinemate.data.repositoryImpl.series.TrendingSeriesRepositoryImpl
import com.jasmeet.cinemate.domain.useCase.movies.NowPlayingMoviesUseCase
import com.jasmeet.cinemate.domain.useCase.movies.TopRatedMoviesUseCase
import com.jasmeet.cinemate.domain.useCase.movies.TrendingMoviesUseCase
import com.jasmeet.cinemate.domain.useCase.movies.UpcomingMovieUseCase
import com.jasmeet.cinemate.domain.useCase.tvSeries.AiringTodayUseCase
import com.jasmeet.cinemate.domain.useCase.tvSeries.TopRatedSeriesUseCase
import com.jasmeet.cinemate.domain.useCase.tvSeries.TrendingSeriesUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)

            .build()
            .create(ApiService::class.java)
    }


    /**
     * Provides All repositories and use cases for movies
     */


    @Provides
    @Singleton
    fun provideTrendingMoviesRepository(apiService: ApiService): TrendingMoviesRepository =
        TrendingMoviesImpl(apiService)

    @Provides
    @Singleton
    fun providesTrendingMoviesUseCase(repository: TrendingMoviesRepository): TrendingMoviesUseCase =
        TrendingMoviesUseCase(repository)


    @Provides
    @Singleton
    fun providesNowPlayingMoviesRepository(apiService: ApiService): NowPlayingMoviesRepository =
        NowPlayingRepositoryImpl(apiService)

    @Provides
    @Singleton
    fun providesNowPlayingMoviesUseCase(repository: NowPlayingMoviesRepository): NowPlayingMoviesUseCase =
        NowPlayingMoviesUseCase(repository)


    @Provides
    @Singleton
    fun providesTopRatedMoviesRepository(apiService: ApiService): TopRatedMoviesRepository =
        TopRatedMoviesImpl(apiService)

    @Provides
    @Singleton
    fun providesTopRatedMoviesUseCase(repository: TopRatedMoviesRepository): TopRatedMoviesUseCase =
        TopRatedMoviesUseCase(repository)

    @Provides
    @Singleton
    fun providesUpcomingMoviesRepository(apiService: ApiService): UpcomingMovieRepository =
        UpcomingMoviesRepositoryImpl(apiService)

    @Provides
    @Singleton
    fun providesUpcomingMoviesUseCase(repository: UpcomingMovieRepository): UpcomingMovieUseCase =
        UpcomingMovieUseCase(repository)

    @Provides
    @Singleton
    fun providesMovieDetailsRepository(apiService: ApiService): MovieDetailsRepository =
        MovieDetailsRepositoryImpl(apiService)


    /**
     * Provides All repositories and use cases for series
     */

    @Provides
    @Singleton
    fun providesTrendingSeriesRepository(apiService: ApiService): TrendingSeriesRepository =
        TrendingSeriesRepositoryImpl(apiService)

    @Provides
    @Singleton
    fun providesTrendingSeriesUseCase(repository: TrendingSeriesRepository): TrendingSeriesUseCase =
        TrendingSeriesUseCase(repository)


    @Provides
    @Singleton
    fun providesAiringSeriesRepository(apiService: ApiService): AiringTodayRepository =
        AiringTodayRepositoryImpl(apiService)

    @Provides
    @Singleton
    fun providesAiringTodaySeriesUseCase(repository: AiringTodayRepository): AiringTodayUseCase =
        AiringTodayUseCase(repository)

    @Provides
    @Singleton
    fun providesTopRatedSeriesRepository(apiService: ApiService): TopRatedSeriesRepository =
        TopRatedSeriesSeriesRepositoryImpl(apiService)

    @Provides
    @Singleton
    fun providesTopRatedSeriesUseCase(repository: TopRatedSeriesRepository): TopRatedSeriesUseCase =
        TopRatedSeriesUseCase(repository)

    @Provides
    @Singleton
    fun providesSeriesDetailsRepository(apiService: ApiService): SeriesDetailsRepository =
        SeriesDetailsRepositoryImpl(apiService)
}



