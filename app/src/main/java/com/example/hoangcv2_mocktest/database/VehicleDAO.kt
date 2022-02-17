package com.example.hoangcv2_mocktest.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.hoangcv2_mocktest.model.Vehicle

@Dao
interface VehicleDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertVehicle(vehicle: Vehicle):Long

    @Query("UPDATE vehicle SET vehicleName = :vehicleName,vehicleType =:vehicleType,vehiclePrice =:vehiclePrice WHERE vehicleId=:vehicleId")
    suspend fun updateVehicle(vehicleName: String?,vehicleType: String?,vehiclePrice: Double?, vehicleId: String?):Int

    @Query("DELETE FROM vehicle WHERE vehicleId=:vehicleId")
    suspend fun deleteVehicle(vehicleId: String?):Int

    @Query("SELECT * FROM vehicle WHERE vehicleName LIKE '%' || :vehicleName || '%'")
    fun searchVehicle(vehicleName: String?): LiveData<MutableList<Vehicle>>

    @Query("SELECT * FROM vehicle")
    fun getAllVehicle(): LiveData<MutableList<Vehicle>>

    @Query("SELECT * FROM vehicle ORDER BY vehicleName ASC")
    fun getVehicleByName(): LiveData<MutableList<Vehicle>>

    @Query("SELECT * FROM vehicle ORDER BY vehiclePrice ASC")
    fun getVehicleByPrice(): LiveData<MutableList<Vehicle>>

    @Query("SELECT * FROM vehicle WHERE vehiclePrice > :vehiclePrice")
    fun getVehicleByInRangePrice(vehiclePrice: Double?): LiveData<MutableList<Vehicle>>
}