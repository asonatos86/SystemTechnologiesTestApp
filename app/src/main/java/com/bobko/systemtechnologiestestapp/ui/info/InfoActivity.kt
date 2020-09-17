package com.bobko.systemtechnologiestestapp.ui.info

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bobko.systemtechnologiestestapp.R
import com.bobko.systemtechnologiestestapp.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {

    private lateinit var viewMode: InfoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_info)

        viewMode = ViewModelProvider(this).get(InfoViewModel::class.java)
        viewMode.getById(intent.getStringExtra("id").toString()).observe(this, Observer{
            Log.d("logger", it.name)
            binding.currency = it
        })
        binding.viewModel = viewMode

        viewMode.backClick.observe(this, Observer { this.finish() })
    }
}