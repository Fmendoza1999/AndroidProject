package com.example.studentregistration.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(student: Student): Long

    @Query("SELECT * FROM students WHERE id = :studentId")
    fun getStudentById(studentId: Int): LiveData<Student?>
}