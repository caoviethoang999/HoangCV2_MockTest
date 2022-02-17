package com.example.hoangcv2_mocktest.database

import androidx.lifecycle.LiveData
import com.example.hoangcv2_mocktest.model.Vehicle

class VehicleRepository(private val vehicleDatabase: VehicleDatabase) {

    suspend fun insertVehicle(vehicle: Vehicle):Long = vehicleDatabase.VehicleDAO().insertVehicle(vehicle)

    suspend fun updateVehicle(vehicleName: String?,vehicleType: String?,vehiclePrice: Double?, vehicleId: String?):Int =
        vehicleDatabase.VehicleDAO().updateVehicle(vehicleName, vehicleType,vehiclePrice,vehicleId)

    suspend fun deleteVehicle(vehicleId: String?):Int = vehicleDatabase.VehicleDAO().deleteVehicle(vehicleId)

    fun searchVehicle(vehicleName: String?): LiveData<MutableList<Vehicle>> =
        vehicleDatabase.VehicleDAO().searchVehicle(vehicleName)

    fun getAllVehicle(): LiveData<MutableList<Vehicle>> =  vehicleDatabase.VehicleDAO().getAllVehicle()

    fun getVehicleByName(): LiveData<MutableList<Vehicle>> =  vehicleDatabase.VehicleDAO().getVehicleByName()

    fun getVehicleByPrice(): LiveData<MutableList<Vehicle>> =  vehicleDatabase.VehicleDAO().getVehicleByPrice()

    fun getVehicleByInRangePrice(vehiclePrice: Double?): LiveData<MutableList<Vehicle>> =  vehicleDatabase.VehicleDAO().getVehicleByInRangePrice(vehiclePrice)
}