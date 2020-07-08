package pl.kinga.myosotis.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.Month

@Entity
data class PeriodDaySettings(
    @PrimaryKey val uid:Int,
    @ColumnInfo(name = "firstDaydata") val firstDaydata: LocalDate,
    @ColumnInfo(name = "day") val dayOfCycle: Int,
    @ColumnInfo(name = "month") val month: Month
)