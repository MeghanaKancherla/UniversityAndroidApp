package com.capgemini.universityapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.capgemini.universityapp.R
import com.capgemini.universityapp.model.Repository
import com.capgemini.universityapp.model.Student
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var repository: Repository
    var studentList = listOf<Student>()
    var selectedList = mutableListOf<Student>()
    lateinit var studentSelected: Student

    lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repository = Repository(this)

        updateList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add("Add Student")
        menu?.add("Update Student")
        menu?.add("Delete Student")
        menu?.add("Delete All Students")
        menu?.add("Refresh")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.title){
            "Add Student" -> {
                val i = Intent(this, StudentActivity::class.java)
                i.putExtra("operation", "add")
                startActivity(i)
            }
            "Update Student" -> {
                val i = Intent(this, StudentActivity::class.java)
                i.putExtra("operation", "update")
                i.putExtra("selected", studentSelected)
                startActivity(i)
            }
            "Delete Student" -> {
                deleteRecords()
            }
            "Delete All Students" -> {
                repository.deleteAllStudents()
            }
            "Refresh" -> {
                updateList()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun updateList(){
        CoroutineScope(Dispatchers.Default).launch {
            studentList = repository.allStudents()
            Log.d("MainActivity", "Student List: $studentList")
            CoroutineScope(Dispatchers.Main).launch {
                rView.layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = StudentAdapter(studentList) { student: Student, flag: Int ->
                    Toast.makeText(this@MainActivity, "${student}", Toast.LENGTH_LONG).show()
                    if (flag == 1) {
                        selectedList.add(student)
                        studentSelected = student
                    } else {
                        selectedList.remove(student)
                    }
                }
                rView.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }
    }

    fun deleteRecords(){
        CoroutineScope(Dispatchers.Default).launch {
            for(i in selectedList)
            {
                repository.deleteStudent(i)
            }
            studentList = repository.allStudents()
            CoroutineScope(Dispatchers.Main).launch {
                updateList()
            }
        }
    }
}