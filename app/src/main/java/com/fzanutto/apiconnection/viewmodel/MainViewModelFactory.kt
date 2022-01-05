package com.fzanutto.apiconnection.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fzanutto.apiconnection.network.ApiConnection

class MainViewModelFactory(private val application: Application, private val api: ApiConnection): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(application, api) as T
    }
}