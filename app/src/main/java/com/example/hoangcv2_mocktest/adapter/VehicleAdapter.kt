package com.example.hoangcv2_mocktest.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hoangcv2_mocktest.OnItemClickListener
import com.example.hoangcv2_mocktest.databinding.ItemVehicleBinding
import com.example.hoangcv2_mocktest.model.Vehicle
import kotlin.collections.ArrayList


class VehicleAdapter(private var onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<VehicleAdapter.ItemViewHolder>() {
    private var list: MutableList<Vehicle>
    fun getAll(list: MutableList<Vehicle>) {
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            ItemVehicleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var vehicle: Vehicle = list[position]
            holder.binding.txtVehicleType.text = vehicle.vehicleType
            holder.binding.txtVehicleName.text = vehicle.vehicleName
            holder.binding.txtVehicleID.text = vehicle.vehicleId
        holder.binding.txtVehiclePrice.text = String.format("%.0f", vehicle.vehiclePrice) + "đ"
        holder.binding.txtViewDetail.setOnClickListener {
            onItemClickListener.onItemClick(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ItemViewHolder(val binding: ItemVehicleBinding) :
        RecyclerView.ViewHolder(binding.root)

    init {
        list = ArrayList<Vehicle>()
    }
}