package com.voxcom.noteitup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(private val taskList: MutableList<Task>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskText: TextView = itemView.findViewById(R.id.itaskText)
        val taskLabel: TextView = itemView.findViewById(R.id.ilabelmark)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val labelsInn = arrayOf("🖇️", "❤️", "💎", "⏳", "💀")
        val backgroundDrawables = arrayOf(
            R.drawable.all_item,  // 0 - All
            R.drawable.fav_item,  // 1 - Favorite
            R.drawable.imp_item,  // 2 - Important
            R.drawable.free_item, // 3 - Free Time
            R.drawable.sos_item   // 4 - SOS
        )

        val task = taskList[position]

        holder.taskText.text = task.title
        holder.taskLabel.text = labelsInn.getOrElse(task.label.toInt()) { "🖇️" } // Default to "🖇️"

        // Set background dynamically
        val backgroundRes = backgroundDrawables.getOrElse(task.label.toInt()) { R.drawable.all_item } // Default
        holder.itemView.setBackgroundResource(backgroundRes)
    }

    override fun getItemCount(): Int = taskList.size

    fun addTask(task: Task) {
        taskList.add(task)
        notifyItemInserted(taskList.size - 1)
    }
}
