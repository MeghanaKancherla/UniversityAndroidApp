package com.capgemini.universityapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.capgemini.universityapp.model.RepositoryVM
import com.capgemini.universityapp.model.University
import kotlinx.coroutines.launch

class UniversityViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = RepositoryVM(app)

    init {
        updateCount()
    }

    val univ = University(
        "New Horizon College of Engineering",
        "Marathahalli, Bangalore, Karnataka",
        "nhce@nhce.edu"
    )

    var studentCount = MutableLiveData<Int>()

    fun updateCount(){
        // get count from db
        viewModelScope.launch {
            val list = repo.allStudents()
            studentCount.postValue(list.size)
        }
    }
}