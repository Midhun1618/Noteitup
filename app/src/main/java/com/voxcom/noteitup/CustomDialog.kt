package com.voxcom.noteitup

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton

class CustomDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Remove default title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        // Set the layout file for the dialog
        setContentView(R.layout.todo_dialog)

        // Set transparent background
        window?.setBackgroundDrawableResource(android.R.color.transparent)

        // Find and set the close button functionality
        val btnClose: ImageButton = findViewById(R.id.close_dilog)
        btnClose.setOnClickListener { dismiss() }
    }
}
