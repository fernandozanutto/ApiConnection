package com.fzanutto.apiconnection.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fzanutto.apiconnection.databinding.ExpandingBottomSheetContentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheet : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "BottomSheet"
    }

    private lateinit var binding: ExpandingBottomSheetContentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExpandingBottomSheetContentBinding.inflate(layoutInflater)

        return binding.root
    }
}