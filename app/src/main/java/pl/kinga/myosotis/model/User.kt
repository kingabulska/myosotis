package pl.kinga.myosotis.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @ColumnInfo(name = "name") val userName: String,
    @ColumnInfo(name = "age") val age: Int,
    @ColumnInfo(name = "periodLength") val periodLength: Int,
    @ColumnInfo(name = "cycleLength") val cycleLength: Int,
    @ColumnInfo(name = "cycleDay") val cycleDay: Int,
    @PrimaryKey val uid: Int
) {
}