package com.ecamarero.spacex.ui.launches

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ecamarero.spacex.domain.usecase.GetLaunches
import com.ecamarero.spacex.ui.launches.model.LaunchesActivityState
import com.ecamarero.spacex.ui.launches.model.LaunchesUIMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class LaunchesViewModel @Inject constructor(
    private val getLaunches: GetLaunches
) : ViewModel() {

    private val _launchesLiveData = MutableLiveData<LaunchesActivityState>().apply { value = LaunchesActivityState() }
    internal val launchesLiveData = _launchesLiveData

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadLaunches() {
        getLaunches()
            .map(LaunchesUIMapper::toUI)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _launchesLiveData.postValue(_launchesLiveData.value?.copy(loading = true))
            }
            .subscribe({
                _launchesLiveData.value = _launchesLiveData.value?.copy(launches = it, error = null)
            }, {
                _launchesLiveData.value = _launchesLiveData.value?.copy(error = it)
            }, {
                _launchesLiveData.value = _launchesLiveData.value?.copy(loading = false)
            }).addTo(compositeDisposable = compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}