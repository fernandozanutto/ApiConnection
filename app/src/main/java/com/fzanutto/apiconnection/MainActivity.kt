package com.fzanutto.apiconnection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fzanutto.apiconnection.databinding.MainActivityBinding
import com.fzanutto.apiconnection.viewmodel.MainViewModel
import com.fzanutto.apiconnection.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainActivityBinding
    private lateinit var adapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, MainViewModelFactory(this.application)).get(MainViewModel::class.java)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)

        binding.eventList.layoutManager = layoutManager

        adapter = EventAdapter(viewModel.eventList)
        binding.eventList.adapter = adapter
    }
}