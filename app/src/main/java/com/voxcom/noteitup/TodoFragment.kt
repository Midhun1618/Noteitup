package com.voxcom.noteitup

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TodoFragment : Fragment() {

    private lateinit var todoAdapter: TodoAdapter
    private val taskList = mutableListOf<Task>()
    private lateinit var todoSort: Button
    private var currentSortIndex = 0
    private val sortItems = arrayOf("All", "❤️", "💎", "⌛", "💀")

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
        todoSort = view.findViewById(R.id.todoSort)
        val addButton: Button = view.findViewById(R.id.addTodo)

        taskList.addAll(loadTodos())

        todoAdapter = TodoAdapter(taskList)
        recyclerView.adapter = todoAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())



        todoSort.text = sortItems[currentSortIndex] // Set initial text

        addButton.setOnClickListener {
            click()
            (activity as? MainActivity)?.showAddTodoDialog()
        }

        todoSort.setOnClickListener {
            changedone()
            currentSortIndex = (currentSortIndex + 1) % sortItems.size
            todoSort.text = sortItems[currentSortIndex]
            sortTodoList(sortItems[currentSortIndex])

        }
    }

    fun addTaskToList(task: String, label: String) {
        val newTask = Task(task, label)
        taskList.add(newTask)
        todoAdapter.notifyItemInserted(taskList.size - 1)
        saveTodos(taskList)
    }

    private fun sortTodoList(filter: String) {
        if (filter == "All") {
//            todoAdapter.resetList() // Show all items TODO(filtering adapter)
        } else {
//            todoAdapter.filterListByLabel(filter) TODO(filtering adapter)
        }
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
    private fun addedsfx() {
        val mediaPlayer = MediaPlayer.create(requireContext(), R.raw.dropdown)
        mediaPlayer?.start()
        ontap()
        mediaPlayer?.setOnCompletionListener { mp -> mp.release() }
    }
    private fun saveTodos(list: List<Task>) {
        val sharedPreferences = requireContext().getSharedPreferences("ToDoPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val jsonString = Gson().toJson(list)  // Convert list to JSON
        editor.putString("todos", jsonString)
        editor.apply()
    }

    // ✅ Load tasks from SharedPreferences
    private fun loadTodos(): MutableList<Task> {
        val sharedPreferences = requireContext().getSharedPreferences("ToDoPrefs", Context.MODE_PRIVATE)
        val jsonString = sharedPreferences.getString("todos", "[]")
        val type = object : TypeToken<MutableList<Task>>() {}.type
        return Gson().fromJson(jsonString, type) ?: mutableListOf()
    }
}
