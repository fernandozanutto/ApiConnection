package com.fzanutto.apiconnection.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fzanutto.apiconnection.databinding.BottomSheetContentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheet : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "BottomSheet"
    }

    private lateinit var binding: BottomSheetContentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetContentBinding.inflate(layoutInflater)

        return binding.root
    }
}