package com.andy_dev.arpractical.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.andy_dev.arpractical.model.Facts

@Database(entities = [Facts::class], version = 1)
abstract class FactDatabase : RoomDatabase() {
    abstract fun factsDao(): FactsDao

    companion object {
        private var INSTANCE: FactDatabase? = null
        fun getDatabase(context: Context): FactDatabase {
            if (INSTANCE == null) {
                synchronized(FactDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            FactDatabase::class.java, "facts_database"
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}