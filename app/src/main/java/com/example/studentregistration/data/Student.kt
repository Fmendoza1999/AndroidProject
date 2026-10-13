package com.example.studentregistration.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
 * AI-ASSISTED CODE (Use 1 of 3) – Claude (Anthropic), 12/10/2026
 * Student entity: defines the "students" table in the Room database.
 * See "AI Use" section of report for screenshot.
 */

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val course: String,
    val year: Int
)