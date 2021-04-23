package com.capgemini.universityapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.capgemini.universityapp.R
import com.capgemini.universityapp.databinding.ActivityAboutBinding
import com.capgemini.universityapp.model.University
import com.capgemini.universityapp.viewModels.UniversityViewModel

class AboutActivity : AppCompatActivity() {
    lateinit var model : UniversityViewModel
    lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_about)

        val vmProvider = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
        model = vmProvider.get(UniversityViewModel::class.java)

        binding = DataBindingUtil.setContentView<ActivityAboutBinding>(this, R.layout.activity_about)
        binding.universityVM = model
        binding.lifecycleOwner = this
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add("Update Count")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //val count = (Math.random()*1000).toInt()
        model.updateCount()
//        Log.d("AboutActivity", "$count")
//        model.studentCount.value = count

        return super.onOptionsItemSelected(item)
    }
}