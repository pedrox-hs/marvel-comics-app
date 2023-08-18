package com.example.comics.di

import com.example.comics.repository.Api
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val networkModule: Module
    get() = module {
        single {
            get<Retrofit>().create<Api>()
        } bind Api::class

        factory {
            Retrofit.Builder()
                .baseUrl(getProperty<String>("api_base_url"))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        } bind Retrofit::class
    }