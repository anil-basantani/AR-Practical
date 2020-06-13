package com.andy_dev.arpractical.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andy_dev.arpractical.model.Facts
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class FactsViewModel : ViewModel() {

    var factResults: MutableLiveData<List<Facts>> = MutableLiveData()
    var factError: MutableLiveData<String> = MutableLiveData()
    var factLoader: MutableLiveData<Boolean> = MutableLiveData()

    private lateinit var factsRepo: FactsRepo
    private val showNoData: MediatorLiveData<Boolean> = MediatorLiveData()
    private lateinit var disposableObserver: DisposableObserver<List<Facts>>

    init {
        factResults.value = listOf()
        factLoader.value = false

        showNoData.addSource(factResults) {
            showNoData.value = !factLoader.value!! && it!!.count() == 0
        }

        showNoData.addSource(factLoader) {
            showNoData.value = !it!! && factResults.value!!.count() == 0
        }
    }

    fun setFactRepo(factsRepo: FactsRepo) {
        this.factsRepo = factsRepo
    }

    fun factResults(): LiveData<List<Facts>> {
        return factResults
    }

    fun factError(): LiveData<String> {
        return factError
    }

    fun factLoader(): LiveData<Boolean> {
        return factLoader
    }

    fun showNoData(): LiveData<Boolean> {
        return showNoData
    }

    fun loadFacts(showProgress: Boolean) {
        if (showProgress)
            factLoader.postValue(true)

        disposableObserver = object : DisposableObserver<List<Facts>>() {
            override fun onComplete() {

            }

            override fun onNext(t: List<Facts>) {
                factResults.postValue(t)
                factLoader.postValue(false)
            }

            override fun onError(e: Throwable) {
                factLoader.postValue(false)
                factError.postValue(e.message)
            }
        }

        factsRepo.getFacts()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(400, TimeUnit.MILLISECONDS)
            .subscribe(disposableObserver)
    }

    fun disposeElements() {
        if (!disposableObserver.isDisposed) disposableObserver.dispose()
    }
}