package com.example.comics.di

import com.example.comics.data.network.ComicApiAuthInterceptor
import com.example.comics.data.network.ComicsService
import okhttp3.OkHttpClient
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val networkModule: Module
    get() = module {
        single {
            get<Retrofit>().create<ComicsService>()
        } bind ComicsService::class

        factory {
            Retrofit.Builder()
                .client(get())
                .baseUrl(getProperty<String>("api_base_url"))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        } bind Retrofit::class

        single {
            OkHttpClient.Builder()
                .addInterceptor(get<ComicApiAuthInterceptor>())
                .build()
        } bind OkHttpClient::class
    }