package com.example.studentregistration.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.studentregistration.data.Student
import com.example.studentregistration.data.StudentDatabase
import com.example.studentregistration.data.StudentRepository
import kotlinx.coroutines.launch


class StudentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: StudentRepository

    init {
        val dao = StudentDatabase.getDatabase(application).studentDao()
        repository = StudentRepository(dao)
    }

    fun insert(student: Student, onInserted: (Long) -> Unit) {
        viewModelScope.launch {
            val newId = repository.insert(student)
            onInserted(newId)
        }
    }

    fun getStudentById(id: Int): LiveData<Student?> = repository.getStudentById(id)
}