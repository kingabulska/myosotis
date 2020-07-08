package pl.kinga.myosotis.ui.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_start.ageText
import kotlinx.android.synthetic.main.fragment_start.cycleDayText
import kotlinx.android.synthetic.main.fragment_start.cycleLenText
import kotlinx.android.synthetic.main.fragment_start.nameText
import kotlinx.android.synthetic.main.fragment_start.periodLenText
import kotlinx.android.synthetic.main.fragment_start.saveButton
import pl.kinga.myosotis.*
import pl.kinga.myosotis.model.Muscus
import pl.kinga.myosotis.model.PeriodDaySettings
import pl.kinga.myosotis.model.Record
import pl.kinga.myosotis.model.User
import java.time.LocalDate
import java.time.Month

class StartFragment : Fragment(R.layout.fragment_start) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateData()
        saveButton.setOnClickListener { saveData() }


    }

    private fun saveData() {
        val nav = findNavController()
        var isOk = true

        val name = nameText.text?.toString()
        val age = ageText.text?.toString()
        val cycleLen = cycleLenText.text?.toString()
        val periodLen = periodLenText.text?.toString()
        val cycleDay = cycleDayText.text?.toString()

        if (name.isNullOrBlank()) {
            nameText.error = "This field cannot be empty"
            isOk = false
        } else {
            nameText.error = null
        }

        if (age.isNullOrBlank() || age.toInt() <= 0) {
            ageText.error = "This field cannot be empty"
            isOk = false
        } else {
            ageText.error = null
        }

        if (cycleLen.isNullOrBlank() || cycleLen.toInt() <= 0) {
            cycleLenText.error = "This field cannot be empty"
            isOk = false
        } else {
            cycleLenText.error = null
        }

        if (cycleDay.isNullOrBlank() || cycleDay.toInt() <= 0) {
            cycleDayText.error = "This field cannot be empty"
            isOk = false
        } else {
            cycleDayText.error = null
        }

        if (periodLen.isNullOrBlank() || periodLen.toInt() <= 0) {
            periodLenText.error = "This field cannot be empty"
            isOk = false
        } else {
            periodLenText.error = null
        }

        if (isOk) {

            val user = User(name!!, age!!.toInt(), periodLen!!.toInt(), cycleLen!!.toInt(), cycleDay!!.toInt(),1)
            (activity as MainActivity).db.userDao().insertUserData(user)

            val month = LocalDate.now().month
            val firstDay:LocalDate
            val periodDay:Int

            if(cycleDay.toInt() == 1){
                firstDay = LocalDate.now()
                periodDay = 1
            }else{
                firstDay = LocalDate.now().minusDays(cycleDay.toLong()-1)
                periodDay = cycleDay.toInt()

            }
            val periodSett = PeriodDaySettings(1, firstDay, periodDay, month)
            (activity as MainActivity).db.periodDaySettingsDao().insertSetting(periodSett)

            Toast.makeText(activity, "Saved", Toast.LENGTH_SHORT).show()
            nav.navigate(R.id.action_addFragment_to_navigation_dashboard)
        }


    }

    fun populateData(){
        val month = Month.MAY
        var data = LocalDate.of(2020, month, 2)
        val records = arrayOf(
            Record(36.2, Muscus.LACK, data.plusDays(1), month, 1 ),
            Record(36.3, Muscus.LACK, data.plusDays(2), month,2),
            Record(36.2, Muscus.LACK, data.plusDays(3), month,3),
            Record(36.4, Muscus.LACK, data.plusDays(4), month,4),
            Record(36.3, Muscus.LACK, data.plusDays(5), month,5),
            Record(36.4, Muscus.LACK, data.plusDays(6), month,6),
            Record(36.4, Muscus.WATERY, data.plusDays(7), month,7),
            Record(36.2, Muscus.WATERY, data.plusDays(8), month,8),
            Record(36.6, Muscus.WATERY, data.plusDays(9), month,9),
            Record(36.3, Muscus.WATERY, data.plusDays(10), month,10),
            Record(36.3, Muscus.WATERY, data.plusDays(11), month,11),
            Record(36.2, Muscus.VERY_WATERY, data.plusDays(12), month,12),
            Record(36.4, Muscus.VERY_WATERY, data.plusDays(13), month,13),
            Record(37.4, Muscus.VERY_WATERY, data.plusDays(14), month,14),
            Record(36.9, Muscus.VERY_WATERY, data.plusDays(15), month,15),
            Record(37.1, Muscus.VERY_WATERY, data.plusDays(16), month,16),
            Record(37.0, Muscus.WATERY, data.plusDays(17), month,17),
            Record(37.0, Muscus.DENSE, data.plusDays(18), month,18),
            Record(37.0, Muscus.DENSE, data.plusDays(19), month,19),
            Record(37.0, Muscus.DENSE, data.plusDays(20), month,20),
            Record(37.1, Muscus.DENSE, data.plusDays(21), month,21),
            Record(37.2, Muscus.VERY_DENSE, data.plusDays(22), month,22),
            Record(37.1, Muscus.VERY_DENSE, data.plusDays(23), month,23),
            Record(37.0, Muscus.VERY_DENSE, data.plusDays(24), month,24),
            Record(37.1, Muscus.VERY_DENSE, data.plusDays(25), month,25),
            Record(37.2, Muscus.VERY_DENSE, data.plusDays(26), month,26),
            Record(37.0, Muscus.DENSE, data.plusDays(27), month,27),
            Record(37.3, Muscus.DENSE, data.plusDays(28), month,28)
        )

       val recordDao = (activity as MainActivity).db.recordDao()
        recordDao.insertAll(*records)

    }

}


