package com.engineerskasa.giphy.di

import com.engineerskasa.giphy.data.network.GiphyApi
import com.engineerskasa.giphy.data.network.GiphyApiService
import com.engineerskasa.giphy.model.Data
import com.engineerskasa.giphy.repository.TrendingRepository
import com.engineerskasa.giphy.view.adapter.TrendingAdapter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    fun provideApi(): GiphyApi = GiphyApiService.getClient()

    @Provides
    fun provideTrendingRepository() = TrendingRepository()

    @Provides
    fun provideListData() = ArrayList<Data>()

    @Provides
    fun provideTrendingAdapter(data: ArrayList<Data>): TrendingAdapter = TrendingAdapter(data)
}