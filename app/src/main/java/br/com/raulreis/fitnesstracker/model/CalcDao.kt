package br.com.raulreis.fitnesstracker.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CalcDao {

    @Insert
    fun insert(calc: Calc)

    @Query("SELECT * FROM Calc WHERE type = :type")
    fun getRegisterByType(type: String) : List<Calc>

    @Query("SELECT * FROM Calc")
    fun getRegisterAll() : List<Calc>

    @Delete
    fun delete(calc: Calc) : Int

}