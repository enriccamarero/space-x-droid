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

    private val _filterLiveData =
        MutableLiveData<LaunchesFilterState>().apply { value = LaunchesFilterState() }
    internal val filterLiveData = _filterLiveData

    private val _launchesLiveData =
        MutableLiveData<LaunchesActivityState>().apply { value = LaunchesActivityState() }
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

    fun removeYear(year: String) {
        _filterLiveData.value = _filterLiveData.value?.copy(
            years = _filterLiveData.value?.years?.minus(year) ?: emptySet()
        ) ?: LaunchesFilterState()
    }

    fun onYearAdded(year: String) {
        if (year.isNotBlank()) {
            _filterLiveData.value = _filterLiveData.value?.copy(
                years = _filterLiveData.value?.years?.plus(year) ?: setOf(year)
            ) ?: LaunchesFilterState(years = setOf(year))
        }
    }

    fun onSortingChanged() {
        _filterLiveData.value = _filterLiveData.value?.copy(
            sorting = if (_filterLiveData.value?.sorting is Sorting.Ascending) Sorting.Descending else Sorting.Ascending
        ) ?: LaunchesFilterState()
    }

    fun onOnlySuccessfulLaunchesChanged(checked: Boolean) {
        _filterLiveData.value = _filterLiveData.value?.copy(
            onlySuccessfulLaunches = checked
        ) ?: LaunchesFilterState(onlySuccessfulLaunches = checked)
    }
}

data class LaunchesFilterState(
    val years: Set<String> = emptySet(),
    val sorting: Sorting = Sorting.Ascending,
    val onlySuccessfulLaunches: Boolean = false
)

sealed class Sorting {
    object Ascending : Sorting()
    object Descending : Sorting()
}