package com.fzanutto.apiconnection.view.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fzanutto.apiconnection.databinding.HomeActivityBinding
import com.fzanutto.apiconnection.network.ApiConnectionImpl
import com.fzanutto.apiconnection.network.ApiDummy
import com.fzanutto.apiconnection.viewmodel.MainViewModel
import com.fzanutto.apiconnection.viewmodel.MainViewModelFactory

class HomeActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: HomeActivityBinding
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var adapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HomeActivityBinding.inflate(layoutInflater)
        swipeRefreshLayout = binding.swipeRefresh
        swipeRefreshLayout.setOnRefreshListener(this)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(this.application, ApiDummy())
        )[MainViewModel::class.java]

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)

        binding.eventList.layoutManager = layoutManager

        adapter = EventAdapter(arrayListOf())
        binding.eventList.adapter = adapter

        viewModel.eventList.observe(this, {
            adapter.updateEventList(it)
            swipeRefreshLayout.isRefreshing = false
        })

        viewModel.callEventList()
    }

    override fun onRefresh() {
        viewModel.callEventList()
    }
}