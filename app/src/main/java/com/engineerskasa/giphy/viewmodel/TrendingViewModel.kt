package com.engineerskasa.giphy.viewmodel

import androidx.lifecycle.ViewModel
import com.engineerskasa.giphy.di.DaggerAppComponent
import com.engineerskasa.giphy.repository.TrendingRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class TrendingViewModel: ViewModel() {
    @Inject
    lateinit var repository: TrendingRepository

    private val compositeDisposable by lazy { CompositeDisposable() }

    init {
        DaggerAppComponent.create().inject(this)
        compositeDisposable.add(repository.fetchDataFromDatabase())
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}