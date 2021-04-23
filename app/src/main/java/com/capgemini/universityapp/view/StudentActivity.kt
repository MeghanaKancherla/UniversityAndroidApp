package com.capgemini.universityapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.capgemini.universityapp.R
import com.capgemini.universityapp.model.Repository
import com.capgemini.universityapp.model.Student
import com.capgemini.universityapp.viewModels.StudentViewModel
import kotlinx.android.synthetic.main.activity_student.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentActivity : AppCompatActivity() {

    lateinit var model: StudentViewModel
    var operation = ""
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)

        val vmProvider = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
        model = vmProvider.get(StudentViewModel::class.java)

        operation = intent?.extras?.getString("operation") ?: ""
        when(operation){
            "add" -> {
                headingT.setText("ADD STUDENT")
                addB.setText("ADD")
            }
            "update" -> {
                headingT.setText("UPDATE STUDENT")
                addB.setText("UPDATE")
                val studentSelected: Student = intent?.extras?.getSerializable("selected") as Student
                fNameE.setText(studentSelected.firstName)
                lNameE.setText(studentSelected.LastName)
                marksE.setText("${studentSelected.marks}")
                id = studentSelected.id
            }
        }
    }

    fun buttonClick(view: View) {
        when(view.id){
            R.id.addB -> {
                val fName = fNameE.text.toString()
                val lName = lNameE.text.toString()
                val marks: Int = marksE.text.toString().toInt()
                when(operation){
                    "add" -> {
                        model.addStudent(Student(fName, lName, marks))
                        Toast.makeText(this, "Student $fName is added!", Toast.LENGTH_LONG).show()
                        finish()
                    }
                    "update" -> {
                        model.updateStudent(Student(fName, lName, marks, id))
                        Toast.makeText(this, "Student $fName is updated!", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
            }
            R.id.cancelB -> {
                finish()
            }
        }
    }

}