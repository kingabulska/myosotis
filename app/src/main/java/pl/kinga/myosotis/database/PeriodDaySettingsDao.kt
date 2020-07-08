package pl.kinga.myosotis.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import pl.kinga.myosotis.model.PeriodDaySettings
import pl.kinga.myosotis.model.Record
import java.time.Month

@Dao
interface PeriodDaySettingsDao {
    @Query("SELECT * FROM perioddaysettings LIMIT 1")
    fun getAll():PeriodDaySettings


    @Insert
    fun insertSetting (  periodDaySettings: PeriodDaySettings)

    @Update
    fun updatePeriodSettings(periodDaySettings: PeriodDaySettings)
}