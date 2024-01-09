package com.example.studentmanagement

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey
    val mssv: String,
    val name: String,
    val birthday: String,
    val hometown: String,
    val email: String = createEmail(mssv, name)
)

fun createEmail(mssv: String, name: String): String {
    val nameLower = name.lowercase()
    val nameSplit = nameLower.split(" ")
    val s = nameSplit.size
    var result = nameSplit[-1] + "."
    for (i in 0..s - 2) {
        result += nameSplit[i][0]
    }
    result += mssv.substring(2)
    result += "@sis.hust.edu.vn"
    return result
}