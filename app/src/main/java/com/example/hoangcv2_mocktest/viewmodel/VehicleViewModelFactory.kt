package com.example.hoangcv2_mocktest.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hoangcv2_mocktest.database.VehicleRepository

class VehicleViewModelFactory(
    private val vehicleRepository: VehicleRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            val constructor = modelClass.getDeclaredConstructor(VehicleRepository::class.java)
            return constructor.newInstance(vehicleRepository)
        } catch (e: Exception) {
            Log.e(ContentValues.TAG, e.message.toString())
        }
        return super.create(modelClass)
    }
}