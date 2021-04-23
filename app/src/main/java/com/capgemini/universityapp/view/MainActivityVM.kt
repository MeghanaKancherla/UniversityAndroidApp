package com.capgemini.universityapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capgemini.universityapp.R
import com.capgemini.universityapp.model.Repository
import com.capgemini.universityapp.model.Student
import com.capgemini.universityapp.viewModels.StudentViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityVM : AppCompatActivity() {

    lateinit var model : StudentViewModel
    var selectedList = mutableListOf<Student>()
    lateinit var studentSelected: Student

    lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // never instantiate with the constructor of the class instead use ViewModelProvider
        //model = StudentViewModel(application)

        val vmProvider = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
        model = vmProvider.get(StudentViewModel::class.java)

        //updateList()
        setUpRecyclerView()

        model.studentList.observe(this, Observer {
            val stdList = it
            Log.d("MainActivity", "Observer: $stdList")
            // setup adapter
            selectedList.clear()
            adapter = StudentAdapter(stdList) { student: Student, flag: Int ->
                if (flag == 1) {
                    selectedList.add(student)
                    studentSelected = student
                    Toast.makeText(this@MainActivityVM, "$selectedList", Toast.LENGTH_LONG).show()
                } else {
                    selectedList.remove(student)
                    Toast.makeText(this@MainActivityVM, "$selectedList", Toast.LENGTH_LONG).show()
                }
            }
            rView.adapter = adapter
        })
    }

    override fun onResume() {
        super.onResume()
        model.getStudents()
    }

    private fun setUpRecyclerView() {
        rView.layoutManager = LinearLayoutManager(this@MainActivityVM)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add("Add Student")
        menu?.add("Update Student")
        menu?.add("Delete Student")
        menu?.add("Delete All Students")
        menu?.add("About us")
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
                model.deleteAll()
            }
            "About us" -> {
                val i = Intent(this, AboutActivity::class.java)
                startActivity(i)
            }
        }
        return super.onOptionsItemSelected(item)
    }

//    fun updateList(){
//        val stdList = model.getStudents()
//            rView.layoutManager = LinearLayoutManager(this@MainActivityVM)
//            adapter = StudentAdapter(stdList) { student: Student, flag: Int ->
//                Toast.makeText(this@MainActivityVM, "${student}", Toast.LENGTH_LONG).show()
//                if (flag == 1) {
//                    selectedList.add(student)
//                    studentSelected = student
//                } else {
//                    selectedList.remove(student)
//                }
//            }
//        rView.adapter = adapter
//        adapter.notifyDataSetChanged()
//        Log.d("MainActivityVM", "list: $stdList")
//    }


    fun deleteRecords(){
        CoroutineScope(Dispatchers.Default).launch {
            for(i in selectedList)
            {
                model.deleteStudent(i)
            }
        }
    }
}