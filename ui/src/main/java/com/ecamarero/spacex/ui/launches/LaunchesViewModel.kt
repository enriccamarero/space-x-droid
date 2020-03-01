package com.ecamarero.spacex.ui.launches

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ecamarero.spacex.domain.company.usecase.GetCompanyInfo
import com.ecamarero.spacex.domain.launches.repository.LaunchParams.Order.Ascending
import com.ecamarero.spacex.domain.launches.repository.LaunchParams.Order.Descending
import com.ecamarero.spacex.domain.launches.usecase.GetLaunches
import com.ecamarero.spacex.ui.company.model.CompanyInfoState
import com.ecamarero.spacex.ui.company.model.CompanyUIMapper.toCompanyString
import com.ecamarero.spacex.ui.launches.model.LaunchesActivityState
import com.ecamarero.spacex.ui.launches.model.LaunchesFilterState
import com.ecamarero.spacex.ui.launches.model.LaunchesUIMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LaunchesViewModel @Inject constructor(
    private val getLaunches: GetLaunches,
    private val getCompanyInfo: GetCompanyInfo
) : ViewModel() {

    private val _filterLiveData =
        MutableLiveData<LaunchesFilterState>().apply { value = LaunchesFilterState() }
    internal val filterLiveData = _filterLiveData

    private val _launchesLiveData =
        MutableLiveData<LaunchesActivityState>().apply { value = LaunchesActivityState() }
    internal val launchesLiveData = _launchesLiveData

    private val _companyInfoLiveData =
        MutableLiveData<CompanyInfoState>().apply {
            value = CompanyInfoState()
        }
    internal val companyInfoLiveData = _companyInfoLiveData

    private val inputSubject: Subject<LaunchesIntention> =
        BehaviorSubject.create<LaunchesIntention>()

    private val stateSubject: BehaviorSubject<LaunchesFilterState> =
        BehaviorSubject.createDefault<LaunchesFilterState>(LaunchesFilterState())

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        setUpInputSubject()
        setUpStateSubject()
        loadCompanyInfo()
        loadLaunches()
    }

    private fun setUpStateSubject() {
        stateSubject
            .doOnNext {
                loadLaunches(it.sorting, it.onlySuccessfulLaunches, it.years)
            }
            .doOnError { it.printStackTrace() }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _filterLiveData.value = it
            }
            .addTo(compositeDisposable)
    }

    private fun setUpInputSubject() {
        inputSubject
            .debounce(300, TimeUnit.MILLISECONDS)
            .map {
                stateSubject.onNext(
                    when (it) {
                        is LaunchesIntention.AddYear -> {
                            stateSubject.value!!.copy(
                                years = stateSubject.value!!.years.plus(it.year)
                            )
                        }
                        is LaunchesIntention.RemoveYear -> {
                            stateSubject.value!!.copy(
                                years = stateSubject.value!!.years.minus(it.year)
                            )
                        }
                        is LaunchesIntention.OnlySuccess -> {
                            stateSubject.value!!.copy(
                                onlySuccessfulLaunches = it.onlySuccess
                            )
                        }
                        LaunchesIntention.ReverseSorting -> {
                            stateSubject.value!!.copy(
                                sorting = if (stateSubject.value!!.sorting == Sorting.Ascending) Sorting.Descending else Sorting.Ascending
                            )
                        }
                        LaunchesIntention.Refresh -> stateSubject.value!!
                    }
                )
            }
            .doOnError { it.printStackTrace() }
            .subscribeOn(Schedulers.computation())
            .subscribe()
            .addTo(compositeDisposable)
    }

    internal fun loadCompanyInfo() {
        getCompanyInfo()
            .map {
                toCompanyString(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _companyInfoLiveData.postValue(_companyInfoLiveData.value?.copy(loading = true))
            }
            .subscribe({
                _companyInfoLiveData.value =
                    _companyInfoLiveData.value?.copy(companyInfo = it, loading = false)
            }, {
                _companyInfoLiveData.value =
                    _companyInfoLiveData.value?.copy(error = it, loading = false)
            })
            .addTo(compositeDisposable = compositeDisposable)
    }

    private fun loadLaunches(
        sorting: Sorting,
        onlySuccessfulLaunches: Boolean,
        years: Set<String>
    ) {
        getLaunches(
            onlySuccessful = onlySuccessfulLaunches,
            launchYears = years.map { it.toInt() },
            order = if (sorting == Sorting.Descending) Descending else Ascending
        )
            .map(LaunchesUIMapper::toUI)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _launchesLiveData.postValue(_launchesLiveData.value?.copy(loading = true))
            }
            .subscribe({
                _launchesLiveData.value =
                    _launchesLiveData.value?.copy(launches = it, error = null, loading = false)
            }, {
                _launchesLiveData.value = _launchesLiveData.value?.copy(loading = false, error = it)
            })
            .addTo(compositeDisposable = compositeDisposable)
    }

    fun removeYear(year: String) {
        if (year.replace("-", "").isNotBlank()) {
            inputSubject.onNext(LaunchesIntention.RemoveYear(year))
        }
    }

    fun onYearAdded(year: String) {
        if (year.replace("-", "").isNotBlank()) {
            inputSubject.onNext(LaunchesIntention.AddYear(year))
        }
    }

    fun onSortingChanged() {
        inputSubject.onNext(LaunchesIntention.ReverseSorting)
    }

    fun onOnlySuccessfulLaunchesChanged(checked: Boolean) {
        if (stateSubject.value?.onlySuccessfulLaunches != checked) {
            inputSubject.onNext(LaunchesIntention.OnlySuccess(checked))
        }
    }

    fun loadLaunches() {
        inputSubject.onNext(LaunchesIntention.Refresh)
    }

    sealed class Sorting {
        object Ascending : Sorting()
        object Descending : Sorting()
    }

    sealed class LaunchesIntention {
        data class AddYear(val year: String) : LaunchesIntention()
        data class RemoveYear(val year: String) : LaunchesIntention()
        data class OnlySuccess(val onlySuccess: Boolean) : LaunchesIntention()
        object ReverseSorting : LaunchesIntention()
        object Refresh : LaunchesIntention()
    }
}