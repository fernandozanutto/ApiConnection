package com.fzanutto.apiconnection.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.fzanutto.apiconnection.R
import com.fzanutto.apiconnection.databinding.CheckInBottomSheetBinding
import com.fzanutto.apiconnection.model.CheckIn
import com.fzanutto.apiconnection.network.ApiConnection
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

class CheckInBottomSheet(
    private val parentView: View,
    private val eventId: Int,
    private val api: ApiConnection
) : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "BottomSheet"
    }

    private lateinit var binding: CheckInBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CheckInBottomSheetBinding.inflate(layoutInflater)

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

                val snackBar = Snackbar.make(parentView, "", Snackbar.LENGTH_SHORT)
                    .setTextColor(ContextCompat.getColor(parentView.context, R.color.white))

                api.sendCheckIn(checkIn, {
                    snackBar
                        .setText("Check-in feito com sucesso!")
                        .setBackgroundTint(
                            ContextCompat.getColor(parentView.context, R.color.green)
                        )

                        .show()

                    dismiss()
                }, {
                    snackBar
                        .setText("Erro ao realizar check-in. Código: $it")
                        .setBackgroundTint(ContextCompat.getColor(parentView.context, R.color.red))
                        .show()

                    dismiss()
                })
            }
        }
    }
}