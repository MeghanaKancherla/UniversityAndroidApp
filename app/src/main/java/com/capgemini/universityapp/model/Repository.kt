package com.capgemini.universityapp.model

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.concurrent.CountDownLatch

class Repository(context: Context) {

    private val studentDao = StudentDatabase.getInstance(context).studentDao()

    fun addStudent(std: Student){
        CoroutineScope(Dispatchers.Default).launch {
            studentDao.insert(std)
        }
    }

    fun updateStudent(std: Student){
        CoroutineScope(Dispatchers.Default).launch {
            studentDao.update(std)
        }
    }

    fun deleteStudent(std: Student){
        CoroutineScope(Dispatchers.Default).launch {
            studentDao.delete(std)
        }
    }

    fun deleteAllStudents(){
        CoroutineScope(Dispatchers.Default).launch {
            studentDao.deleteAll()
        }
    }

    suspend fun allStudents(): List<Student>{
        var stdList : List<Student>? = null
        val result = CoroutineScope(Dispatchers.Default).async {
            studentDao.getStudents()
        }
        stdList = result.await()

        return stdList
    }
}