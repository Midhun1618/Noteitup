package com.voxcom.noteitup

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment

class TodoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout and store it in a variable
        val view = inflater.inflate(R.layout.fragment_todo, container, false)

        return view // Return the correct view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner: Spinner = view.findViewById(R.id.todoSort)
        val addButton: Button = view.findViewById(R.id.addTodo)

        addButton.setOnClickListener{
            click()
        }

        // Set up the adapter
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.sort_options,
            R.layout.sort_items
        )
        adapter.setDropDownViewResource(R.layout.sort_items)
        spinner.adapter = adapter

        spinner.setOnTouchListener { _, _ ->
            dropdown()
            false
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                Toast.makeText(requireContext(), "Selected: $selectedItem", Toast.LENGTH_SHORT).show()
                changedone()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

    }
    private fun dropdown() {
        val mediaPlayer = MediaPlayer.create(requireContext(), R.raw.dropdown)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener { mp ->
            mp.release()
        }
    }

    private fun changedone() {
        val mediaPlayer = MediaPlayer.create(requireContext(), R.raw.changedone)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener { mp ->
            mp.release()
        }
    }
    private fun click() {
        val mediaPlayer = MediaPlayer.create(requireContext(), R.raw.clicked)
        mediaPlayer?.start()
        ontap()
        mediaPlayer?.setOnCompletionListener { mp ->
            mp.release()
        }
    }
    private fun ontap() {
        val vibrator = requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(30) // Works on older Android versions
        }
    }

}
