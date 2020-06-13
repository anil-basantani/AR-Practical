package com.andy_dev.arpractical.view_model

import com.andy_dev.arpractical.model.Facts
import com.andy_dev.arpractical.remote.RestApis
import com.andy_dev.arpractical.room.FactsDao
import com.andy_dev.arpractical.utils.Utility
import io.reactivex.Observable

class FactsRepo constructor(private val restApis: RestApis, private val factsDao: FactsDao) {
    fun getFacts(): Observable<List<Facts>> {
        val apiObservable = getFactsFromApi()
        val dbObservable = getFactsFromDb()

        return if (Utility.hasInternet()) {
            Observable.concatArrayEager(apiObservable, dbObservable)
        } else {
            dbObservable
        }
    }

    private fun getFactsFromApi(): Observable<List<Facts>> {

        val response = restApis.callFactsListAPI()

        return response
            .map { res ->
                res.rows!!
            }.doOnNext {
                val facts = it
                for (fact in facts) {
                    factsDao.insertFact(fact)
                }
            }
    }

    private fun getFactsFromDb(): Observable<List<Facts>> {
        return factsDao.queryFacts().toObservable()
    }
}