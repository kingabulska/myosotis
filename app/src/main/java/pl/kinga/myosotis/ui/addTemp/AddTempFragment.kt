package pl.kinga.myosotis.ui.addTemp

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_add.*
import pl.kinga.myosotis.MainActivity
import pl.kinga.myosotis.R
import pl.kinga.myosotis.model.Muscus
import pl.kinga.myosotis.model.Record
import java.time.LocalDate


class AddTempFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_add, container, false)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
        })

        (activity as MainActivity).showBottomNav()
        return root
    }

    override fun onResume() {
        super.onResume()

        loadName()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val names = Muscus.values().map { it.printName }
        val adapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, names)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        userMuscus.adapter = adapter

        saveTempButton.setOnClickListener { saveTempData() }
    }

    private fun loadName() {

        val user = (activity as MainActivity).db.userDao().getUser()
        val userName = user!!.userName
        //val userName = sharedPref?.getString(USER_NAME, null)

        myUsername.setText("Hello $userName!")
    }

    private fun saveTempData() {


        val periodDaySet = (activity as MainActivity).db.periodDaySettingsDao().getAll()!!
        val records = (activity as MainActivity).db.recordDao().findByDate(LocalDate.now())
        var periodDay: Int
        var firstDay = periodDaySet.firstDaydata
        var month = periodDaySet.month

        var isOk = true
        val isPeriod = userIsPeriod.isChecked


        if (isPeriod) {
            firstDay = LocalDate.now()
            periodDay = 1
            month = month.plus(1)
        } else {
            periodDay = (LocalDate.now().dayOfMonth - firstDay.dayOfMonth + 1)
            if (periodDay <= 0) {
                periodDay += month.length(LocalDate.now().isLeapYear)
            }
        }
        if (records != null) {
            isOk = false
            showDialog()
            //userTemp.error = "Today's temperature is already saved"
        }


        if (isOk) {
            val newPeriodDay = periodDaySet.copy(
                dayOfCycle = periodDay,
                firstDaydata = firstDay,
                month = month
            )
            (activity as MainActivity).db.periodDaySettingsDao().updatePeriodSettings(newPeriodDay)

            val temperature = userTemp.text.toString().toDouble()
            val item = userMuscus.selectedItem.toString()
            val muscus = Muscus.fromString(item)
            val data = LocalDate.now()

            val record = Record(temperature, muscus, data, month, periodDay)
            (activity as MainActivity).db.recordDao().insertAll(record)

            Toast.makeText(activity, "Saved", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDialog() {

        activity?.let {
            val builder = AlertDialog.Builder(it)
                .setPositiveButton("YES") { dialog, id ->
                    yesDialog()
                }
                .setNegativeButton("NO") { dialog, id ->

                }
                .setMessage("Today's temperature is already saved. Do you want to overwrite temperature?")
                .setTitle("Overwriting")

            builder.create()
            val dialog: AlertDialog? = builder?.create()
            dialog?.show()
        }



    }

    private fun yesDialog() {

        val date = LocalDate.now()
        val record = (activity as MainActivity).db.recordDao().findByDate(date)

        val item = userMuscus.selectedItem.toString()
        val muscus = Muscus.fromString(item)

        val record2 = record?.copy(
            temperature = userTemp.text.toString().toDouble(),
            muscus = muscus

        )
        if (record2 != null) {
            (activity as MainActivity).db.recordDao().update(record2)
        }

        Toast.makeText(activity, "Saved", Toast.LENGTH_SHORT).show()
    }



}
