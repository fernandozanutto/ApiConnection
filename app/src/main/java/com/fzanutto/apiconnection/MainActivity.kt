package com.fzanutto.apiconnection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fzanutto.apiconnection.databinding.MainActivityBinding
import com.fzanutto.apiconnection.model.Event
import com.fzanutto.apiconnection.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainActivityBinding
    private lateinit var adapter: EventAdapter

    private val eventList = arrayListOf<Event>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        eventList.add(Event("Teste 1"))
        eventList.add(Event("Teste 2"))
        eventList.add(Event("Teste 3"))

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)

        binding.eventList.layoutManager = layoutManager

        adapter = EventAdapter(eventList)
        binding.eventList.adapter = adapter
    }
}