package pl.kinga.myosotis.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.Month

@Entity
data class Record(
    @ColumnInfo(name = "temperature") val temperature: Double,
    @ColumnInfo(name = "muscus") val muscus:Muscus,
    @ColumnInfo(name = "data") val date:LocalDate,
    @ColumnInfo(name = "month") val month:Month,
    @ColumnInfo(name = "periodDay2") val periodDay2: Int,
    @PrimaryKey(autoGenerate = true) val uid:Int = 0
){


}