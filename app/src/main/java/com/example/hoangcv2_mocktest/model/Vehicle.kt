package com.example.hoangcv2_mocktest.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicle")
class Vehicle(
    @PrimaryKey
    @ColumnInfo(name = "vehicleId")
    val vehicleId: String,
    @ColumnInfo(name = "vehicleName")
    val vehicleName: String,
    @ColumnInfo(name = "vehicleType")
    val vehicleType: String,
    @ColumnInfo(name = "vehiclePrice")
    val vehiclePrice: Double
)