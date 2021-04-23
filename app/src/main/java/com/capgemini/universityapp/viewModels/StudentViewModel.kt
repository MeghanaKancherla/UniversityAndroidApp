package com.capgemini.universityapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.capgemini.universityapp.model.RepositoryVM
import com.capgemini.universityapp.model.Student
import kotlinx.coroutines.launch

class StudentViewModel(app: Application) : AndroidViewModel(app){

    // reference to repository
    private val repo = RepositoryVM(app)
    var studentList = MutableLiveData<List<Student>>()
        //listOf<Student>()

    init {
        getStudents()
    }

    fun addStudent(std: Student){
        viewModelScope.launch {
            repo.addStudent(std)
            getStudents()
        }
    }

    fun updateStudent(std: Student){
        viewModelScope.launch {
            repo.updateStudent(std)
            getStudents()
        }
    }

    fun deleteStudent(std: Student){
        viewModelScope.launch {
            repo.deleteStudent(std)
            getStudents()
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            repo.deleteAllStudents()
            getStudents()
        }
    }

    fun getStudents(){

        viewModelScope.launch {
            val list = repo.allStudents()
            studentList.postValue(list)
        }
    }
}