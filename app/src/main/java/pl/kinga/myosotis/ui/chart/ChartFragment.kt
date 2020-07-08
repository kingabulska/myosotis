package pl.kinga.myosotis.ui.chart

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.fragment_chart.*
import pl.kinga.myosotis.MainActivity
import pl.kinga.myosotis.R
import pl.kinga.myosotis.model.Muscus
import pl.kinga.myosotis.model.Record
import java.time.Month

class ChartFragment : Fragment(), AdapterView.OnItemSelectedListener, OnChartValueSelectedListener {

    private lateinit var months:List<Month>
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_chart, container, false)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {

        })


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkFertility()
        checkPregnancy()

        months = (activity as MainActivity).db.recordDao().allMonth().reversed()

        val adapter = ArrayAdapter<Month>(requireContext(), android.R.layout.simple_spinner_item, months)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        chartMonth.adapter = adapter
        chartMonth.onItemSelectedListener = this




    }

    private fun checkPregnancy(){
        val periodSetting = (activity as MainActivity).db.periodDaySettingsDao().getAll()
        val month = periodSetting.month
        val dayOfCycle = periodSetting.dayOfCycle
        val records = (activity as MainActivity).db.recordDao().findByMonth(month)
        val last = records.firstOrNull()

        if(last != null) {

                if (last.temperature > 37.0 && dayOfCycle > 28) {
                    pregnancy.text = " Pregnancy: High probability"
                } else {
                    pregnancy.text = " Pregnancy: Low probability"
                }

        }
    }

    private fun checkFertility(){

        val periodSetting = (activity as MainActivity).db.periodDaySettingsDao().getAll()
        val month = periodSetting.month
        val dayOfCycle = periodSetting.dayOfCycle
        val records = (activity as MainActivity).db.recordDao().findByMonth(month)
        val last = records.firstOrNull()


        if(last != null) {
            if (last.temperature > 36.8 &&(dayOfCycle in 9..16) && (last.muscus == Muscus.WATERY || last.muscus == Muscus.VERY_WATERY)) {

                ferti.text = " Fertility: Fertility window"
            } else {
                ferti.text = " Fertility: Non- fertile day"
            }
        }
    }

    private fun loadD(month: Month){
        val records = (activity as MainActivity).db.recordDao().findByMonth(month)
        val entries = mutableListOf<Entry>()

        for(i in 0 until records.size){
            entries.add(Entry((records[i].periodDay2.toFloat()) , records[i].temperature.toFloat(), records[i]))
        }
        entries.sortBy { it.x }
        val dataSet =  LineDataSet(entries, "Temperature")
        dataSet.setDrawValues(false)
        val color = ContextCompat.getColor(requireContext(), R.color.colorChartLine)
        dataSet.color = color

        val lineData = LineData(dataSet)
        chart.data = lineData

        val des:Description = chart.description
        des.isEnabled = false

        chart.isHighlightPerDragEnabled = true
        chart.setOnChartValueSelectedListener(this)


        chart.invalidate()

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        loadD(months[position])
    }

    override  fun onNothingSelected(){}

    override fun onValueSelected(e: Entry, h: Highlight?) {
        val record = (e.data as Record)
        val date = record.date
        val temp = record.temperature
        val mus = record.muscus
        val perDay = record.periodDay2

        (activity as MainActivity).db.userDao().getUser()?.let{
            val period = it.periodLength

            var periodTime:String = "Non period time"

            if (perDay <= period){
                periodTime = "Period time"
            }


            Toast.makeText(context, "$date\n$perDay th day of cycle\n$periodTime\n$temp *C\nmuscus: $mus\n", Toast.LENGTH_LONG).show()
        }


    }




}

