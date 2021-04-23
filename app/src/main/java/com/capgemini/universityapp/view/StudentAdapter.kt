package com.capgemini.universityapp.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capgemini.universityapp.R
import com.capgemini.universityapp.model.Student

class StudentAdapter(val studentData: List<Student>, val listener: (Student, Int) -> Unit) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {


    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val firstName = view.findViewById<TextView>(R.id.firstT)
        val lastName = view.findViewById<TextView>(R.id.lastT)
        val marks = view.findViewById<TextView>(R.id.scoreT)
        val checkBox = view.findViewById<CheckBox>(R.id.checkB)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.student_item_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val std = studentData[position]
        Log.d("Student Adapter", "Data: $std")
        holder.checkBox.isChecked = false
        holder.firstName.text = std.firstName
        holder.lastName.text = std.LastName
        holder.marks.setText("${std.marks}")
        holder.checkBox.setOnClickListener {
            if(it is CheckBox) {
                if (it.isChecked) {
                    listener(std, 1)
                }
                else{
                    listener(std, 0)
                }
            }
        }
    }

    override fun getItemCount() = studentData.size
}