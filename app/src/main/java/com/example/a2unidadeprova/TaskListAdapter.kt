package com.example.a2unidadeprova

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.task_item.view.*

class TaskListAdapter (private  val tasks: ArrayList<Task>, private val context: Context, private val deleteListener: OnDeleteListener, private val editListener: OnEditListener): RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    var onItemClick: ((Task) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val noteview = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false)

        return ViewHolder(noteview, context)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]
        holder.name.text = task.name
        holder.description.text = task.description
        holder.checked.isChecked = (task.checked == 1)

    }

    inner class ViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView){

        val name = itemView.taskname
        val description = itemView.taskdescription
        val checked = itemView.taskname
        init {
            itemView.setOnClickListener()
            {
                var checkedint = if(checked.isChecked) 1 else 0
                editListener.editTask(layoutPosition, name.text.toString(),description.text.toString(), checkedint)
            }
            itemView.setOnLongClickListener()
            {
                deleteListener.deleteTask(layoutPosition)
                true
            }
        }
    }
}