package pl.kinga.myosotis.database

import androidx.room.TypeConverter
import pl.kinga.myosotis.model.Muscus
import java.time.LocalDate
import java.time.Month

class Converter {
    @TypeConverter
    fun fromMuscus(value: String?): Muscus?{
        return value?.let { Muscus.valueOf(value) }
    }

    @TypeConverter
    fun toMuscus(muscus: Muscus?): String?{
        return muscus.toString()
    }

    @TypeConverter
    fun fromTimestamp(value: String?): LocalDate? {
        return value?.let {  LocalDate.parse(value) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): String? {
        return date.toString()
    }

    @TypeConverter
    fun fromMonth(value: String?): Month?{
        return value?.let { Month.valueOf(value) }
    }

    @TypeConverter
    fun toMonth(month: Month?): String? {
        return month.toString()
    }

}