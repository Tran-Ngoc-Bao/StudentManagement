package com.example.studentmanagement

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface StudentDao {
    @Query("select mssv from students")
    suspend fun getAllMSSV(): Array<String>

    @Query("select name from students")
    suspend fun getAllName(): Array<String>

    @Query("select birthday from students")
    suspend fun getAllBirthday(): Array<String>

    @Query("select hometown from students")
    suspend fun getAllHometown(): Array<String>

    @Query("select email from students")
    suspend fun getAllEmail(): Array<String>

    @Insert
    suspend fun insert(student: Student): Int

    @Update
    suspend fun update(student: Student): Int

    @Delete
    suspend fun delete(student: Student): Int
}