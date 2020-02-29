package com.ecamarero.spacex.ui

import androidx.lifecycle.ViewModel
import com.ecamarero.spacex.domain.usecase.GetLaunches
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SpaceXViewModel @Inject constructor(
    private val getLaunches: GetLaunches
) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadLaunches() {
        getLaunches()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ (data, error) ->
                data?.let {
                    println(it.joinToString(", "))
                }
                error?.let {
                    println(it.message)
                }
            }, {
                println(it.message)
            }, {
                println("COMPLETE")
            }).addTo(compositeDisposable = compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}