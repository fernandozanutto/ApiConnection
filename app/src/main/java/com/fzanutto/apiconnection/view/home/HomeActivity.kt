package com.fzanutto.apiconnection.view.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
            MainViewModelFactory(this.application, ApiConnectionImpl(this))
//            MainViewModelFactory(this.application, ApiDummy())
        )[MainViewModel::class.java]

        setupEventList()
        setupObservers()
    }

    override fun onStart() {
        super.onStart()

        swipeRefreshLayout.isRefreshing = true
        viewModel.callEventList()
    }

    private fun setupEventList() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)

        binding.eventList.layoutManager = layoutManager

        adapter = EventAdapter(arrayListOf())
        binding.eventList.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.eventList.observe(this, {
            adapter.updateEventList(it)
            swipeRefreshLayout.isRefreshing = false

            if (it.isEmpty()) {
                binding.noEventFound.visibility = View.VISIBLE
            } else {
                binding.noEventFound.visibility = View.GONE
            }

            binding.requestError.visibility = View.GONE
        })

        viewModel.requestError.observe(this, {
            swipeRefreshLayout.isRefreshing = false
            binding.noEventFound.visibility = View.GONE
            binding.requestError.visibility = View.VISIBLE
        })
    }

    override fun onRefresh() {
        viewModel.callEventList()
    }
}