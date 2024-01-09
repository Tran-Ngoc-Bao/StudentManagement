package com.example.studentmanagement

import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddStudent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_student)

        val studentDao = StudentDatabase.getInstance(application).studentDao()

        val addStudent = findViewById<Button>(R.id.add_student)
        addStudent.setOnClickListener {
            val name = findViewById<EditText>(R.id.student_name)
            val mssv = findViewById<EditText>(R.id.student_msssv)
            val birthday = findViewById<DatePicker>(R.id.student_birthday)
            val hometown = findViewById<AutoCompleteTextView>(R.id.student_hometown)

            val year = birthday.year
            val month = birthday.month
            val day = birthday.dayOfMonth
            val birthdayReal = "$year $month $day"

            lifecycleScope.launch(Dispatchers.IO) {
                studentDao.insert(
                    Student(
                        mssv.text.toString(),
                        name.text.toString(),
                        birthdayReal,
                        hometown.text.toString()
                    )
                )
            }
        }
    }
}