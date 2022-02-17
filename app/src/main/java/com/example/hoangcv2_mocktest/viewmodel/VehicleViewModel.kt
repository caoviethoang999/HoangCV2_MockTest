package com.example.hoangcv2_mocktest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hoangcv2_mocktest.database.VehicleRepository
import com.example.hoangcv2_mocktest.model.Vehicle
import kotlinx.coroutines.launch

class VehicleViewModel(
    private val vehicleRepository: VehicleRepository
) : ViewModel() {
    val rowsInserted: MutableLiveData<Long> = MutableLiveData()
    val rowDeleted: MutableLiveData<Int> = MutableLiveData()
    val rowUpdated: MutableLiveData<Int> = MutableLiveData()
    fun insertVehicle(vehicle: Vehicle)= viewModelScope.launch {
        val result=vehicleRepository.insertVehicle(vehicle)
        rowsInserted.postValue(result)}

    fun updateVehicle(vehicleName: String?,vehicleType: String?,vehiclePrice: Double?, vehicleId: String?) =
        viewModelScope.launch {
            val result=vehicleRepository.updateVehicle(vehicleName, vehicleType,vehiclePrice,vehicleId)
            rowUpdated.postValue(result)}

    fun deleteVehicle(vehicleId: String?) = viewModelScope.launch {
        val result=vehicleRepository.deleteVehicle(vehicleId)
        rowDeleted.postValue(result)
    }

    fun searchVehicle(vehicleName: String?): LiveData<MutableList<Vehicle>> =
        vehicleRepository.searchVehicle(vehicleName)

    fun getAllVehicle(): LiveData<MutableList<Vehicle>> =  vehicleRepository.getAllVehicle()

    fun getVehicleByName(): LiveData<MutableList<Vehicle>> =  vehicleRepository.getVehicleByName()

    fun getVehicleByPrice(): LiveData<MutableList<Vehicle>> =  vehicleRepository.getVehicleByPrice()

    fun getVehicleByInRangePrice(vehiclePrice: Double?): LiveData<MutableList<Vehicle>> =  vehicleRepository.getVehicleByInRangePrice(vehiclePrice)
}