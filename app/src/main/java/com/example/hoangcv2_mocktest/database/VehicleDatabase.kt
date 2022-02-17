package com.example.hoangcv2_mocktest.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hoangcv2_mocktest.model.Vehicle


@Database(
    entities = [Vehicle::class],
    version = 1,
    exportSchema = false
)

abstract class VehicleDatabase : RoomDatabase() {
    abstract fun VehicleDAO(): VehicleDAO

    companion object {
        private const val DB_NAME = "vehicle_database.db"
        @Volatile
        private var instance: VehicleDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            VehicleDatabase::class.java,
            DB_NAME
        ).build()
    }
}