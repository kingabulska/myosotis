package pl.kinga.myosotis.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import pl.kinga.myosotis.model.PeriodDaySettings
import pl.kinga.myosotis.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): User?


    @Insert
    fun insertUserData (  user: User)

    @Update
    fun update(user: User)
}