package com.voxcom.noteitup

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Vibrator
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    private var selectedLabel: String = "None"
    private lateinit var fragmentChange: Button
    private var currentFragmentIndex = 0
    private val fragmentNames = arrayOf("To-Do", "Sticky", "Notify")
    private val fragments = arrayOf(TodoFragment(), StickyFragment(), NotifyFragment())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentChange = findViewById(R.id.fragmentChange)
        fragmentChange.text = fragmentNames[currentFragmentIndex]

        // Load initial fragment
        loadFragment(fragments[currentFragmentIndex])

        fragmentChange.setOnClickListener {
            fragchangeSfx()
            vibratePhone()
            currentFragmentIndex = (currentFragmentIndex + 1) % fragments.size
            fragmentChange.text = fragmentNames[currentFragmentIndex]
            loadFragment(fragments[currentFragmentIndex])
        }

        // Monitor fragment changes and handle addTodo button inside TodoFragment
        supportFragmentManager.addOnBackStackChangedListener {
            val todoFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as? TodoFragment
            todoFragment?.view?.findViewById<Button>(R.id.addTodo)?.setOnClickListener {
                showAddTodoDialog()
            }
        }
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        if (fragment is TodoFragment) {
            fragment.todoAddedListener = object : TodoFragment.OnTodoAddedListener {
                override fun onTodoAdded(task: String, label: String) {
                    fragment.addTaskToList(task, label)
                }
            }
        }
    }

    fun showAddTodoDialog() {
        val dialogView = layoutInflater.inflate(R.layout.todo_dialog, null)
        val editText: EditText = dialogView.findViewById(R.id.todoEditText)
        val labelButton: Button = dialogView.findViewById(R.id.labelButton)
        val selectedLabelText: TextView = dialogView.findViewById(R.id.label_info)
        val addButton: Button = dialogView.findViewById(R.id.addTodo)
        val closeButton: ImageButton = dialogView.findViewById(R.id.close_dilog)

        val dialog = AlertDialog.Builder(this, R.style.TransparentDialog)
            .setView(dialogView)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        labelButton.setOnClickListener {
            showLabelSelectionDialog(selectedLabelText)
        }

        addButton.setOnClickListener {
            val task = editText.text.toString().trim()
            if (task.isNotEmpty()) {
                (supportFragmentManager.findFragmentById(R.id.fragmentContainer) as? TodoFragment)?.addTaskToList(
                    task, selectedLabelText.toString()
                )
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun vibratePhone() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(80)
        }
    }

    private fun fragchangeSfx() {
        val mediaPlayer = MediaPlayer.create(this, R.raw.fragmentchangesfx)
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener { mp -> mp.release() }
    }

    private fun showLabelSelectionDialog(selectedLabelText: TextView) {
        val labels = arrayOf("None", "❤️", "💎", "⏳", "💀")
        val messages = arrayOf(
            "Don't forget to complete it!",
            "Remember, it's your favourite task!",
            "Important task: do it in 6 hours!",
            "Do it whenever you are free.",
            "Urgent task: only 2 hours left."
        )

        AlertDialog.Builder(this)
            .setTitle("Select Label")
            .setItems(labels) { _, which ->
                selectedLabel = which.toString()
                selectedLabelText.text = messages[which]
            }
            .show()
    }

}
