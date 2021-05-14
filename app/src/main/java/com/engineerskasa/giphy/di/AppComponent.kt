package com.engineerskasa.giphy.di

import com.engineerskasa.giphy.repository.TrendingRepository
import com.engineerskasa.giphy.view.ui.MainActivity
import com.engineerskasa.giphy.viewmodel.TrendingViewModel
import dagger.Component
import javax.inject.Singleton
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(trendingRepository: TrendingRepository)

    fun inject(viewModel: TrendingViewModel)

    fun inject(mainActivity: MainActivity)
}