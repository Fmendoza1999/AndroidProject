package com.example.studentregistration.data

import androidx.lifecycle.LiveData

// Middle layer between the ViewModel and the database.
// If we later added an online API, only this class would change.
class StudentRepository(private val studentDao: StudentDao) {

    suspend fun insert(student: Student): Long = studentDao.insert(student)

    fun getStudentById(id: Int): LiveData<Student?> = studentDao.getStudentById(id)
}