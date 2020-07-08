package pl.kinga.myosotis.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import pl.kinga.myosotis.model.PeriodDaySettings
import pl.kinga.myosotis.model.Record
import pl.kinga.myosotis.model.User

@Database (entities = arrayOf(Record::class, PeriodDaySettings::class, User ::class), version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recordDao() : RecordDao
    abstract fun periodDaySettingsDao() : PeriodDaySettingsDao
    abstract fun userDao(): UserDao
}