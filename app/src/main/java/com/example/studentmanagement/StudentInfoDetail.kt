package com.example.studentmanagement

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentInfoDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_info_detail)

        val name = intent.getStringExtra("name")
        val mssv = intent.getStringExtra("mssv")
        val birthday = intent.getStringExtra("birthday")
        val hometown = intent.getStringExtra("hometown")

        val birthdaySplit = birthday?.split(" ")
        val year = birthdaySplit?.get(0)?.toInt()
        val month = birthdaySplit?.get(1)?.toInt()
        val day = birthdaySplit?.get(2)?.toInt()

        val n = findViewById<EditText>(R.id.student_name)
        n.hint = name
        val m = findViewById<EditText>(R.id.student_msssv)
        m.hint = mssv

        val b = findViewById<DatePicker>(R.id.student_birthday)
        if (year != null) {
            if (month != null) {
                if (day != null) {
                    b.updateDate(year, month, day)
                }
            }
        }

        val hometowns: Array<String> = resources.getStringArray(R.array.hometowns)
        val h = findViewById<AutoCompleteTextView>(R.id.student_hometown)
        val adapterHometown: ArrayAdapter<String> =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, hometowns)
        h.setAdapter(adapterHometown)
        h.hint = hometown

        val studentDao = StudentDatabase.getInstance(application).studentDao()

        val updateStudent = findViewById<Button>(R.id.update_student)
        updateStudent.setOnClickListener {
            val nu = findViewById<EditText>(R.id.student_name)
            val mu = findViewById<EditText>(R.id.student_msssv)
            val bu = findViewById<DatePicker>(R.id.student_birthday)
            val hu = findViewById<AutoCompleteTextView>(R.id.student_hometown)

            var nuReal = nu.hint.toString()
            if (nu.text != null) {
                nuReal = nu.text.toString()
            }

            var muReal = mu.hint.toString()
            if (mu.text != null) {
                muReal = mu.text.toString()
            }

            val y = bu.year
            val m = bu.month
            val d = bu.dayOfMonth
            val buReal = "$y $m $d"

            var huReal = hu.hint.toString()
            if (hu.text != null) {
                huReal = hu.text.toString()
            }

            lifecycleScope.launch(Dispatchers.IO) {
                studentDao.update(Student(muReal, nuReal, buReal, huReal))
            }
        }

        val deleteStudent = findViewById<Button>(R.id.delete_student)
        deleteStudent.setOnClickListener {
            val nd = findViewById<EditText>(R.id.student_name)
            val md = findViewById<EditText>(R.id.student_msssv)
            val bd = findViewById<DatePicker>(R.id.student_birthday)
            val hd = findViewById<AutoCompleteTextView>(R.id.student_hometown)

            var ndReal = nd.hint.toString()
            if (nd.text != null) {
                ndReal = nd.text.toString()
            }

            var mdReal = md.hint.toString()
            if (md.text != null) {
                mdReal = md.text.toString()
            }

            val y = bd.year
            val m = bd.month
            val d = bd.dayOfMonth
            val bdReal = "$y $m $d"

            var hdReal = hd.hint.toString()
            if (hd.text != null) {
                hdReal = hd.text.toString()
            }

            lifecycleScope.launch(Dispatchers.IO) {
                studentDao.delete(Student(mdReal, ndReal, bdReal, hdReal))
            }
        }
    }
}