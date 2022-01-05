package com.fzanutto.apiconnection.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fzanutto.apiconnection.databinding.HomeActivityBinding
import com.fzanutto.apiconnection.network.ApiDummy
import com.fzanutto.apiconnection.viewmodel.MainViewModel
import com.fzanutto.apiconnection.viewmodel.MainViewModelFactory

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: HomeActivityBinding
    private lateinit var adapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, MainViewModelFactory(this.application, ApiDummy())).get(MainViewModel::class.java)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)

        binding.eventList.layoutManager = layoutManager

        adapter = EventAdapter(listOf())
        binding.eventList.adapter = adapter

        viewModel.eventList.observe(this, {
            adapter.updateEventList(it)
        })

        viewModel.callEventList()
    }
}