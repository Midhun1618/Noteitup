package com.voxcom.noteitup

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TodoFragment : Fragment() {

    private lateinit var todoAdapter: TodoAdapter
    private val taskList = mutableListOf<Task>()

    interface OnTodoAddedListener {
        fun onTodoAdded(task: String, label: String)
    }

    var todoAddedListener: OnTodoAddedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.todoRecyclerView)
        todoAdapter = TodoAdapter(taskList)
        recyclerView.adapter = todoAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val spinner: Spinner = view.findViewById(R.id.todoSort)
        val addButton: Button = view.findViewById(R.id.addTodo)

        addButton.setOnClickListener {
            click()
            (activity as? MainActivity)?.showAddTodoDialog()
        }

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

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    fun addTaskToList(task: String, label: String) {
        val newTask = Task(task, label)
        taskList.add(newTask)
        todoAdapter.notifyItemInserted(taskList.size - 1)
    }

    private fun dropdown() {
        val mediaPlayer = MediaPlayer.create(requireContext(), R.raw.dropdown)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener { mp -> mp.release() }
    }

    private fun changedone() {
        val mediaPlayer = MediaPlayer.create(requireContext(), R.raw.changedone)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener { mp -> mp.release() }
    }

    private fun click() {
        val mediaPlayer = MediaPlayer.create(requireContext(), R.raw.clicked)
        mediaPlayer?.start()
        ontap()
        mediaPlayer?.setOnCompletionListener { mp -> mp.release() }
    }

    private fun ontap() {
        val vibrator = requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(30)
        }
    }
}
