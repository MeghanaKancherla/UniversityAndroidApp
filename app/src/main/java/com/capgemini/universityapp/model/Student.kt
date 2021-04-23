package com.capgemini.universityapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "student_table")
data class Student (@ColumnInfo(name = "first_name") var firstName: String,
                    var LastName: String,
                    var marks: Int,
                    @PrimaryKey(autoGenerate = true) var id: Int = 0): Serializable