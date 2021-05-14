package com.engineerskasa.giphy.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.engineerskasa.giphy.GiphyApplication
import com.engineerskasa.giphy.data.database.toDataEntityList
import com.engineerskasa.giphy.data.database.toDataList
import com.engineerskasa.giphy.data.network.GiphyApi
import com.engineerskasa.giphy.di.DaggerAppComponent
import com.engineerskasa.giphy.internal.API_KEY
import com.engineerskasa.giphy.internal.LIMIT
import com.engineerskasa.giphy.internal.RATING
import com.engineerskasa.giphy.model.Data
import com.engineerskasa.giphy.model.TrendingResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

class TrendingRepository {
    @Inject
    lateinit var giphyApi: GiphyApi

    init {
        DaggerAppComponent.create().inject(this)
    }

    private val _data by lazy { MutableLiveData<List<Data>>() }
    val data: LiveData<List<Data>>
    get() = _data

    private val _isInProgress by lazy { MutableLiveData<Boolean>() }
    val isInProgress: LiveData<Boolean>
    get() = _isInProgress

    private val _isError by lazy { MutableLiveData<Boolean>() }
    val isError: LiveData<Boolean>
    get() = _isError

    private fun insertData(): Disposable{
        return giphyApi.getTrending(API_KEY, LIMIT, RATING)
            .subscribeOn(Schedulers.io())
            .subscribeWith(subscribeToDatabase())
    }

    private fun subscribeToDatabase(): DisposableSubscriber<TrendingResult> {
        return object : DisposableSubscriber<TrendingResult>() {
            override fun onNext(trendingResult: TrendingResult) {
                if (trendingResult != null) {
                    val entityList = trendingResult.data.toList().toDataEntityList()
                    GiphyApplication.database.apply {
                        dataDao().insertData(entityList)
                    }
                }
            }

            override fun onError(t: Throwable?) {
               _isInProgress.postValue(true)
                Log.e("insertData()", "onError: ${t?.message}")
                _isError.postValue(true)
                _isInProgress.postValue(false)
            }

            override fun onComplete() {
                Log.v("insertData()", "insert success")
                getTrendingQuery()
            }

        }
    }

    private fun getTrendingQuery(): Disposable {
        return GiphyApplication.database.dataDao()
            .queryData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ dataEntityList ->
                _isInProgress.postValue(true)
                if (dataEntityList != null && dataEntityList.isNotEmpty()) {
                    _isError.postValue(false)
                    _data.postValue(dataEntityList.toDataList())
                } else {
                    insertData()
                }
                _isInProgress.postValue(false)
            }, {
                _isInProgress.postValue(true)
                Log.e("getTrendingQuery()", "Database error: ${it.message}")
                _isError.postValue(true)
                _isInProgress.postValue(false)
            })
    }

    fun fetchDataFromDatabase(): Disposable = getTrendingQuery()
}