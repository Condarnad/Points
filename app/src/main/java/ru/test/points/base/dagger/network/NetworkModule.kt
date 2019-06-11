package ru.test.points.base.dagger.network

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.test.points.BuildConfig
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    private val HTTP_LOG_TAG = "OkHttp"

    @Provides
    @Named("BASE_URL")
    @Singleton
    fun provideBaseUrl() = "https://api.tinkoff.ru/v1/"

    @Provides
    @Singleton
    internal fun provideRetrofit(
        okHttpClient: OkHttpClient,
        //callAdapterFactory: CallAdapterFactory,
        gson: Gson,
        @Named("BASE_URL") apiUrl: String
    ) =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(apiUrl)
            .addConverterFactory(CustomConverterFactory(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    @Singleton
    internal fun provideGson() = GsonBuilder().create()

    @Provides
    @Singleton
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {

        val logging = HttpLoggingInterceptor { message -> Log.d(HTTP_LOG_TAG, message) }

        logging.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.BASIC

        return logging
    }

}