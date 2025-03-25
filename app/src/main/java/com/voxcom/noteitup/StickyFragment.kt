package com.voxcom.noteitup

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment

class StickyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sticky, container, false)

        val payButton: Button = view.findViewById(R.id.premium)
        payButton.setOnClickListener {
            makeGooglePayPayment()
        }

        return view
    }

    private fun makeGooglePayPayment() {
        val upiId = "kasinathsg@okaxis"  // Replace with actual UPI ID
        val name = "Kasinath SG"  // Replace with the recipient name
        val amount = "99.00"  // Set the payment amount
        val currency = "INR"
        val transactionNote = "Payment for services"
        val transactionId = "TXN" + System.currentTimeMillis()  // Unique transaction ID

        val uri = Uri.parse(
            "upi://pay?pa=$upiId&pn=$name&mc=&tid=$transactionId&tr=$transactionId&tn=$transactionNote&am=$amount&cu=$currency"
        )

        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.google.android.apps.nbu.paisa.user")  // Google Pay package name

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "Google Pay not installed", Toast.LENGTH_SHORT).show()
        }
    }
}
