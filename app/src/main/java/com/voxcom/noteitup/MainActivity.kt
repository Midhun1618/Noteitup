package com.voxcom.noteitup

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    private lateinit var fragmentChange: Button
    private var currentFragmentIndex = 0
    private val fragmentNames = arrayOf( "Sticky","To-Do", "Notify")
    private val fragments = arrayOf(TodoFragment(), StickyFragment(), NotifyFragment())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentChange = findViewById(R.id.fragmentChange)
        fragmentChange.text = fragmentNames[currentFragmentIndex]

        // Load initial fragment
        loadFragment(fragments[currentFragmentIndex])

        fragmentChange.setOnClickListener {
            currentFragmentIndex = (currentFragmentIndex + 1) % fragments.size
            fragmentChange.text = fragmentNames[currentFragmentIndex]
            loadFragment(fragments[currentFragmentIndex])
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commit()
    }

}

