package com.example.hoangcv2_mocktest.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.hoangcv2_mocktest.*
import com.example.hoangcv2_mocktest.database.VehicleDatabase
import com.example.hoangcv2_mocktest.database.VehicleRepository
import com.example.hoangcv2_mocktest.databinding.FragmentAddVehicleBinding
import com.example.hoangcv2_mocktest.model.Vehicle
import com.example.hoangcv2_mocktest.viewmodel.VehicleViewModel
import com.example.hoangcv2_mocktest.viewmodel.VehicleViewModelFactory

class AddVehicleFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentAddVehicleBinding
    private lateinit var viewModel: VehicleViewModel
    private var listAllVehicle:MutableList<Vehicle> = ArrayList<Vehicle>()
    private var vehicleName: String? = null
    private var vehicleType: String? = null
    private var vehicleId: String? = null
    private var vehiclePrice: Double? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllVehicle().observe(viewLifecycleOwner, {
            listAllVehicle=it
        })
        passData()
        if (vehicleId != null){
            setUpUiForUpdateCase()
        }
        val appCompatActivity = activity as AppCompatActivity?
        appCompatActivity?.setSupportActionBar(binding.toolbar)
        appCompatActivity?.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.btnAdd.setOnClickListener(this)
        binding.btnUpdate.setOnClickListener(this)
    }
    private fun passData() {
        val bundle = this.arguments
        if (bundle != null) {
            vehicleName = bundle.getString("vehicleName")
            vehicleType = bundle.getString("vehicleType")
            vehicleId = bundle.getString("vehicleId")
            vehiclePrice = bundle.getDouble("vehiclePrice")
        }
    }
    private fun setUpUiForUpdateCase(){
        passData()
        if (vehicleId!= null){
            binding.edtVehicleID.isEnabled=false
            binding.edtVehicleID.setText(vehicleId)
            binding.edtVehicleName.setText(vehicleName)
            binding.edtVehicleType.setText(vehicleType)
            binding.edtVehiclePrice.setText(String.format("%.0f", vehiclePrice))
            binding.btnAdd.visibility=View.GONE
            binding.btnUpdate.visibility=View.VISIBLE
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val vehicleRepository = VehicleRepository(VehicleDatabase(requireContext()))
        val factory = VehicleViewModelFactory(vehicleRepository)
        viewModel = ViewModelProvider(requireActivity(), factory).get(VehicleViewModel::class.java)
        binding = FragmentAddVehicleBinding.inflate(inflater, container, false)
        return binding.root
    }
    private fun checkVehicleId():Boolean{
        var check:Boolean=true
        for (i in 0 until listAllVehicle.size) {
            if (binding.edtVehicleID.text.toString().equals(listAllVehicle[i].vehicleId,true)) {
                check=false
            }
        }
        return check
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btnAdd ->{
                if(checkVehicleId()){
                    viewModel.insertVehicle(Vehicle(binding.edtVehicleID.text.toString(),binding.edtVehicleName.text.toString(),binding.edtVehicleType.text.toString(),binding.edtVehiclePrice.text.toString().toDouble()))
                    viewModel.rowsInserted.observe(viewLifecycleOwner,{
                        if(it >0){
                            Toast.makeText(requireContext(),"Add successfully", Toast.LENGTH_LONG).show()
                        }
                    })
                }else{
                    Toast.makeText(requireContext(),"This ID is Already Exist", Toast.LENGTH_LONG).show()
                }
            }
            R.id.btnUpdate ->{
                viewModel.updateVehicle(binding.edtVehicleName.text.toString(),binding.edtVehicleType.text.toString(),binding.edtVehiclePrice.text.toString().toDouble(),binding.edtVehicleID.text.toString())
                viewModel.rowUpdated.observe(viewLifecycleOwner,{
                    if(it >0){
                        Toast.makeText(requireContext(),"Update successfully", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }
}