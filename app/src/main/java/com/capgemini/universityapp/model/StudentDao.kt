package com.capgemini.universityapp.model

import androidx.room.*

@Dao
interface StudentDao {
    // Define methods for queries

    @Insert
    suspend fun insert(std: Student)

    @Update
    suspend fun update(std: Student)

    @Delete
    suspend fun delete(std: Student)

    @Query("DELETE FROM student_table")
    suspend fun deleteAll()

    @Query("select * from student_table order by marks desc")
    suspend fun getStudents() : List<Student>
}