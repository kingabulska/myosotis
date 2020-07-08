package pl.kinga.myosotis.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlinx.android.synthetic.main.fragment_edit.ageText
import kotlinx.android.synthetic.main.fragment_edit.cycleDayText
import kotlinx.android.synthetic.main.fragment_edit.cycleLenText
import kotlinx.android.synthetic.main.fragment_edit.nameText
import kotlinx.android.synthetic.main.fragment_edit.periodLenText
import pl.kinga.myosotis.*
import pl.kinga.myosotis.model.PeriodDaySettings
import pl.kinga.myosotis.model.User
import java.time.LocalDate

class EditFragment : Fragment() {

    lateinit var user:User

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?


    ): View? {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_edit, container, false)
        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveButton.setOnClickListener { saveData() }
    }

    override fun onResume() {
        super.onResume()

        loadData()
    }

    private fun loadData() {

        if((activity as MainActivity).db.userDao().getUser() != null){

            user = (activity as MainActivity).db.userDao().getUser()!!

            val userName = user.userName
            val userAge = user.age
            val cycleLen = user.cycleLength
            val periodLen = user.periodLength
            val cycleDay = user.cycleDay

            nameText.setText(userName)
            ageText.setText(userAge.toString())
            cycleLenText.setText(cycleLen.toString())
            periodLenText.setText(periodLen.toString())
            cycleDayText.setText(cycleDay.toString())
        }

    }
    private fun saveData(){
        val nav = findNavController()
        var isOk = true

        val name = nameText.text?.toString()
        val age = ageText.text?.toString()
        val cycleLen = cycleLenText.text?.toString()
        val periodLen = periodLenText.text?.toString()
        val cycleDay = cycleDayText.text?.toString()

        if(name.isNullOrBlank()){
            nameText.error = "This field cannot be empty"
            isOk = false
        }else{
            nameText.error = null
        }

        if(age.isNullOrBlank() || age.toInt() <= 0){
            ageText.error = "This field cannot be empty"
            isOk = false
        }else{
            ageText.error = null
        }

        if(cycleLen.isNullOrBlank() || cycleLen.toInt() <= 0){
            cycleLenText.error = "This field cannot be empty"
            isOk = false
        }else{
            cycleLenText.error = null
        }

        if(cycleDay.isNullOrBlank() || cycleDay.toInt() <= 0){
            cycleDayText.error = "This field cannot be empty"
            isOk = false
        }else{
            cycleDayText.error = null
        }

        if(periodLen.isNullOrBlank() || periodLen.toInt() <= 0){
            periodLenText.error = "This field cannot be empty"
            isOk = false
        }else{
            periodLenText.error = null
        }

        if(isOk){

            val user = user.copy(
                userName = name!!,
                age = age!!.toInt(),
                periodLength = periodLen!!.toInt(),
                cycleLength = cycleLen!!.toInt(),
                cycleDay = cycleDay!!.toInt()
            )
           (activity as MainActivity).db.userDao().update(user)
            val month = LocalDate.now().month
            val firstDay:LocalDate
            val periodDay:Int

            if(cycleDay.toInt() == 1){
                firstDay = LocalDate.now()
                periodDay = 1
            }else{
                firstDay = LocalDate.now().minusDays(cycleDay.toLong())
                periodDay = cycleDay.toInt()

            }
            val periodSett = PeriodDaySettings(1, firstDay, periodDay, month)
            (activity as MainActivity).db.periodDaySettingsDao().updatePeriodSettings(periodSett)

            }

            Toast.makeText(activity, "Saved", Toast.LENGTH_SHORT).show()

        }

    }

