package com.example.studentmanagement

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val studentDao = StudentDatabase.getInstance(application).studentDao()

        lateinit var names: Array<String>
        lateinit var mssvs: Array<String>
        lateinit var emails: Array<String>
        lateinit var birthdays: Array<String>
        lateinit var hometowns: Array<String>

        lifecycleScope.launch(Dispatchers.IO) {
            studentDao.insert(Student("20215529", "Trần Ngọc Bảo", "2003 6 12", "Nam Định"))
            names = studentDao.getAllName()
            mssvs = studentDao.getAllMSSV()
            emails = studentDao.getAllEmail()
            birthdays = studentDao.getAllBirthday()
            hometowns = studentDao.getAllHometown()
        }

        val studentInfoS = arrayListOf<StudentInfo>()

        for (i in names.indices) {
            studentInfoS.add(StudentInfo(names[i], mssvs[i], emails[i]))
        }

        val listStudent = findViewById<ListView>(R.id.list_student)
        listStudent.adapter = ListStudent(studentInfoS)

        listStudent.setOnItemClickListener { _, _, i, _ ->
            val intent = Intent(this, StudentInfoDetail::class.java)
            intent.putExtra("name", names[i])
            intent.putExtra("mssv", mssvs[i])
            intent.putExtra("birthday", birthdays[i])
            intent.putExtra("hometown", hometowns[i])
            startActivity(intent)
        }

        val addStudent = findViewById<Button>(R.id.add_student)
        addStudent.setOnClickListener {
            val intent = Intent(this, AddStudent::class.java)
            startActivity(intent)
        }
    }
}

data class StudentInfo(val name: String, val mssv: String, val email: String)

class ListStudent(private val list: ArrayList<StudentInfo>) : BaseAdapter() {
    override fun getCount(): Int = list.size
    override fun getItem(p0: Int): Any = list[p0]
    override fun getItemId(p0: Int): Long = p0.toLong()
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val col: View = LayoutInflater.from(p2?.context).inflate(R.layout.student_info, p2, false)

        val name = col.findViewById<TextView>(R.id.student_name)
        val mssv = col.findViewById<TextView>(R.id.student_msssv)
        val email = col.findViewById<TextView>(R.id.student_email)

        name.text = list[p0].name
        mssv.text = list[p0].mssv
        email.text = list[p0].email

        return col
    }
}