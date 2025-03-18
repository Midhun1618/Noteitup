package com.voxcom.noteitup

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        val textView: TextView = findViewById(R.id.myTextView)

        // Set initial position outside screen (left side)
        textView.translationX = -textView.width.toFloat()

        // Animate the text sliding in with a feathery effect
        val animator = ObjectAnimator.ofFloat(textView, "translationX", 0f)
        animator.duration = 1500 // 1.5 sec for smooth effect
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()
    }
}