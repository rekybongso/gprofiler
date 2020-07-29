package me.rkyb.gprofiler.di

import javax.inject.Singleton
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import me.rkyb.gprofiler.data.remote.source.UserSearchService
import me.rkyb.gprofiler.utils.Constants

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    /* The Github base url and personal token can be found on
       Constants.kt at utils package. */

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor{ chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", Constants.GIT_PERSONAL_TOKEN)
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.GIT_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideUserSearchService(retrofit: Retrofit): UserSearchService {
        return retrofit.create(UserSearchService::class.java)
    }

}