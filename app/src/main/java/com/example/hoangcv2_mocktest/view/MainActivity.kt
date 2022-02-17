package com.example.hoangcv2_mocktest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hoangcv2_mocktest.R
import com.example.hoangcv2_mocktest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recyclerFragment = ListVehicleFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, recyclerFragment)
            .commit()
    }
}