package pl.kinga.myosotis.ui.splash

import android.os.Handler
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pl.kinga.myosotis.*

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : Fragment(R.layout.fragment_splash) {


    override fun onResume() {
        super.onResume()

        Handler().postDelayed({checkData()}, 3000) //dlugosc wyswietlania splash screen

    }

    private fun checkData(){

        val user = (activity as MainActivity).db.userDao().getUser()
        val nav = findNavController()
        /*
        val sharedPref = activity?.getSharedPreferences(
            USER_DATA, Context.MODE_PRIVATE)

        val userName = sharedPref?.getString(USER_NAME, null)
        val userAge = sharedPref?.getInt(AGE, -1)
        val cycleLen = sharedPref?.getInt(CYCLE_LENGTH, -1)
        val periodLen = sharedPref?.getInt(PERIOD_LENGTH, -1)
        val cycleDay = sharedPref?.getInt(CYCLE_DAY, -1)*/

        if(user == null){
            nav.navigate(R.id.action_splashFragment_to_startFragment)
        }else{
            nav.navigate(R.id.action_splashFragment_to_navigation_dashboard)
        }
    }
}
