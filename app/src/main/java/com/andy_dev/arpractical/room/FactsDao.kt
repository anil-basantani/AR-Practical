package com.andy_dev.arpractical.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andy_dev.arpractical.model.Facts
import io.reactivex.Single

@Dao
interface FactsDao {
    @Query("SELECT * FROM facts")
    fun queryFacts(): Single<List<Facts>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFact(fact: Facts)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllFacts(facts: List<Facts>)

}