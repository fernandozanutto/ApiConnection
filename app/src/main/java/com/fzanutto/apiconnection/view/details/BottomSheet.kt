package com.fzanutto.apiconnection.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.fzanutto.apiconnection.R
import com.fzanutto.apiconnection.databinding.BottomSheetContentBinding
import com.fzanutto.apiconnection.model.CheckIn
import com.fzanutto.apiconnection.network.ApiConnection
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

class BottomSheet(
    private val parentView: View,
    private val eventId: Int,
    private val api: ApiConnection
) : BottomSheetDialogFragment() {

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

        setButtonClick()

        return binding.root
    }

    private fun setButtonClick() {
        binding.apply {

            sendButton.setOnClickListener {
                val email = emailInput.text.toString()
                val name = nameInput.text.toString()

                val checkIn = CheckIn(
                    email = email,
                    eventId = eventId,
                    name = name
                )

                api.sendCheckIn(checkIn, {
                    Snackbar.make(parentView, "Check-in feito com sucesso!", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(
                            ContextCompat.getColor(
                                parentView.context,
                                R.color.green
                            )
                        )
                        .setTextColor(ContextCompat.getColor(parentView.context, R.color.white))
                        .show()

                    dismiss()
                }, {
                    Snackbar.make(
                        parentView,
                        "Erro ao realizar check-in. Código: $it",
                        Snackbar.LENGTH_SHORT
                    )
                        .setBackgroundTint(ContextCompat.getColor(parentView.context, R.color.red))
                        .setTextColor(ContextCompat.getColor(parentView.context, R.color.white))
                        .show()

                    dismiss()
                })
            }
        }
    }
}