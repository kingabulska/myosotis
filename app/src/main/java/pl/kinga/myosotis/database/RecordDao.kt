package pl.kinga.myosotis.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import pl.kinga.myosotis.model.PeriodDaySettings
import pl.kinga.myosotis.model.Record
import pl.kinga.myosotis.model.User
import java.time.LocalDate
import java.time.Month

@Dao
interface RecordDao {
    @Query("SELECT * FROM record")
    fun getAll(): List<Record>

    @Query("SELECT * FROM record LIMIT 1")
    fun getOneRec(): Record?

    @Query("SELECT * FROM record WHERE month LIKE (:month) ORDER BY data DESC")
    fun findByMonth(month: Month): List<Record>

    @Query("SELECT DISTINCT month FROM record")
    fun allMonth(): List<Month>

    @Query("SELECT * FROM record WHERE data LIKE (:localDate) ")
    fun findByDate(localDate: LocalDate):Record?
    @Insert
    fun insertAll (vararg  record: Record)
    @Update
    fun update(record: Record)

}