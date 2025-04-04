package com.voxcom.noteitup

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(
    private val context: Context,
    private val taskList: MutableList<Task>,
    private val completedTasks: MutableList<Task>
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskText: TextView = itemView.findViewById(R.id.itaskText)
        val taskLabel: TextView = itemView.findViewById(R.id.ilabelmark)
        val taskDone: ImageButton = itemView.findViewById(R.id.taskDone) // Add this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val labelsInn = arrayOf("🖇️", "❤️", "💎", "⏳", "💀")
        val backgroundDrawables = arrayOf(
            R.drawable.all_item,
            R.drawable.fav_item,
            R.drawable.imp_item,
            R.drawable.free_item,
            R.drawable.sos_item
        )

        val task = taskList[position]
        holder.taskText.text = task.title
        holder.taskLabel.text = labelsInn.getOrElse(task.label.toInt()) { "🖇️" }
        val backgroundRes = backgroundDrawables.getOrElse(task.label.toInt()) { R.drawable.all_item }
        holder.itemView.setBackgroundResource(backgroundRes)

        holder.taskDone.setOnClickListener {
            taskdonesfx()
            holder.taskDone.setBackgroundResource(R.drawable.taskf)
            val removedTask = taskList.removeAt(position)
            notifyItemRemoved(position)
            completedTasks.add(removedTask)
        }
    }

    override fun getItemCount(): Int = taskList.size

    fun addTask(task: Task) {
        taskList.add(task)
        notifyItemInserted(taskList.size - 1)
    }

    private fun taskdonesfx() {
        val mediaPlayer = MediaPlayer.create(context, R.raw.completed)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener { mp -> mp.release() }
    }


}
