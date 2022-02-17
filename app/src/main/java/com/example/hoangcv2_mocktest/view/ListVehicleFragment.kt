package com.example.hoangcv2_mocktest.view

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hoangcv2_mocktest.*
import com.example.hoangcv2_mocktest.adapter.VehicleAdapter
import com.example.hoangcv2_mocktest.database.VehicleDatabase
import com.example.hoangcv2_mocktest.database.VehicleRepository
import com.example.hoangcv2_mocktest.databinding.FragmentListVehicleBinding
import com.example.hoangcv2_mocktest.model.Vehicle
import com.example.hoangcv2_mocktest.viewmodel.VehicleViewModel
import com.example.hoangcv2_mocktest.viewmodel.VehicleViewModelFactory


class ListVehicleFragment : Fragment(), OnItemClickListener, View.OnClickListener,
    SearchView.OnQueryTextListener {

    private lateinit var binding:FragmentListVehicleBinding
    private lateinit var viewModel: VehicleViewModel
    private var listAllVehicle:MutableList<Vehicle> = ArrayList<Vehicle>()
    private var listSerachByPrice:MutableList<Vehicle> = ArrayList<Vehicle>()
    private var vehicleAdapter: VehicleAdapter = VehicleAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewVehicle.layoutManager = LinearLayoutManager(requireContext())
        showData()
        val appCompatActivity = activity as AppCompatActivity?
        appCompatActivity?.setSupportActionBar(binding.toolbar)
        appCompatActivity?.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.btnSearch.setOnClickListener(this)
        binding.btnDelete.setOnClickListener(this)
    }

    private fun showData() {
        viewModel.getAllVehicle().observe(viewLifecycleOwner, Observer {
            listAllVehicle = it
            vehicleAdapter.getAll(listAllVehicle)
            binding.recyclerViewVehicle.adapter = vehicleAdapter
        })
    }

    private fun showDataByName() {
        viewModel.getVehicleByName().observe(viewLifecycleOwner, Observer {
            listAllVehicle = it
            vehicleAdapter.getAll(listAllVehicle)
            binding.recyclerViewVehicle.adapter = vehicleAdapter
        })
    }

    private fun showDataByPrice() {
        viewModel.getVehicleByPrice().observe(viewLifecycleOwner, Observer {
            listAllVehicle = it
            vehicleAdapter.getAll(listAllVehicle)
            binding.recyclerViewVehicle.adapter = vehicleAdapter
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val vehicleRepository = VehicleRepository(VehicleDatabase(requireContext()))
        val factory = VehicleViewModelFactory(vehicleRepository)
        viewModel = ViewModelProvider(requireActivity(), factory).get(VehicleViewModel::class.java)
        binding = FragmentListVehicleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onItemClick(position: Int) {
        val fragment = AddVehicleFragment()
        val bundle = Bundle()
        if(binding.btnSearch.isSelected){
            bundle.putString("vehicleName", listSerachByPrice[position].vehicleName)
            bundle.putString("vehicleId", listSerachByPrice[position].vehicleId)
            bundle.putDouble("vehiclePrice", listSerachByPrice[position].vehiclePrice)
            bundle.putString("vehicleType", listSerachByPrice[position].vehicleType)
        }else{
            bundle.putString("vehicleName", listAllVehicle[position].vehicleName)
            bundle.putString("vehicleId", listAllVehicle[position].vehicleId)
            bundle.putDouble("vehiclePrice", listAllVehicle[position].vehiclePrice)
            bundle.putString("vehicleType", listAllVehicle[position].vehicleType)
        }
        fragment.arguments = bundle
        activity?.supportFragmentManager?.beginTransaction()
            ?.addToBackStack(null)?.replace(R.id.fragment_container, fragment)?.commit()
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btnSearch ->{
                viewModel.getVehicleByInRangePrice(binding.edtSearchPrice.text.toString().toDouble()).observe(viewLifecycleOwner, Observer {
                    listSerachByPrice=it
                    vehicleAdapter.getAll(listSerachByPrice)
                    binding.recyclerViewVehicle.adapter = vehicleAdapter
                })
                binding.btnSearch.isSelected=true
            }
            R.id.btnDelete ->{
                viewModel.deleteVehicle(binding.edtDeleteById.text.toString())
                viewModel.rowDeleted.observe(viewLifecycleOwner,{
                    if(it >0){
                        binding.txtDeleteWarning.visibility=View.INVISIBLE
                    }else{
                        binding.txtDeleteWarning.visibility=View.VISIBLE
                    }
                })
                }
            }
        }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        val menuItem = menu.findItem(R.id.mnSearch)
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                true
            }
            R.id.mnFilter -> {
                //initiate popup menu
                val popupMenu = PopupMenu(context, requireActivity().findViewById(R.id.mnFilter))
                popupMenu.menuInflater.inflate(R.menu.menu_filter, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.all -> {
                            showData()
                            binding.btnSearch.isSelected=false
                        }
                        R.id.name -> showDataByName()
                        R.id.price -> showDataByPrice()
                    }
                    true
                }
                // Showing the popup menu
                popupMenu.show()
                true
            }
            R.id.mnSetting ->{
                val popupMenu = PopupMenu(context, requireActivity().findViewById(R.id.mnSetting))
                popupMenu.menuInflater.inflate(R.menu.menu_setting, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.add -> {
                            val fragment = AddVehicleFragment()
                            val bundle = Bundle()
                            fragment.arguments = bundle
                            activity?.supportFragmentManager?.beginTransaction()
                                ?.addToBackStack(null)?.replace(R.id.fragment_container, fragment)?.commit()
                            true
                        }
                    }
                    true
                }
                // Showing the popup menu
                popupMenu.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        viewModel.searchVehicle(p0).observe(viewLifecycleOwner,{
            vehicleAdapter.getAll(it)
            binding.recyclerViewVehicle.adapter=vehicleAdapter
        })
        return true
    }
}