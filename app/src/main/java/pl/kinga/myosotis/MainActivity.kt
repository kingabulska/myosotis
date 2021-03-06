package pl.kinga.myosotis

import android.os.Bundle
import android.view.View
import android.view.Window
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.room.Database
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import pl.kinga.myosotis.database.AppDatabase

class MainActivity : AppCompatActivity() {

   lateinit var  db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView:BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        //setupActionBarWithNavController(navController, appBarConfiguration)

        navView.setupWithNavController(navController)

        hideBottomNav()

       db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "AppDatabase"
        )
           .allowMainThreadQueries()
           .build()
    }

    fun hideBottomNav(){
        nav_view.visibility = View.GONE

        actionBar?.hide()
    }

    fun showBottomNav(){
        nav_view.visibility = View.VISIBLE
    }
}
