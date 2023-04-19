package com.vsu.eyesdoctorapp.ui.diagnostics

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.vsu.eyesdoctorapp.R
import kotlin.math.sin

/* TODO
* 1. Доделать информацию на экране
* 2. Сделать свич на парную и одиночную диагностику
* 3. Прокидывать вверх параметр, какой это глаз
* 4. сделать настройку расстояния*/

class DiagnosticsMenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diagnostics_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drawCharts(view)

        view.findViewById<Button>(R.id.btn_left).setOnClickListener {
            val i = Intent(this.context, PairedDiagnosticsActivity::class.java)
            startActivity(i)
        }
        view.findViewById<Button>(R.id.btn_right).setOnClickListener {
            val i = Intent(this.context, SingleDiagnosticsActivity::class.java)
            startActivity(i)
        }
    }

    private fun drawCharts(view: View) {
        val leftChart: LineChart = view.findViewById(R.id.lc_left)
        val rightChart: LineChart = view.findViewById(R.id.lc_right)

        val dataList = ArrayList<Entry>()
        val dataList2 = ArrayList<Entry>()

        for (i in 1..100) {
            dataList.add(Entry(i.toFloat(), sin(i.toFloat()/10)))
            dataList2.add(Entry(i.toFloat(), sin(i.toFloat()/4)))
        }
        val lineDataSet = LineDataSet(dataList, "Hello world!")
        val lineDataSet2 = LineDataSet(dataList2, "Hello world!")
        val data = LineData(lineDataSet)
        val data2 = LineData(lineDataSet2)
        leftChart.animateX(3000, Easing.EaseOutSine)
        rightChart.animateX(4500, Easing.EaseInSine)

        leftChart.description.isEnabled = false
        rightChart.description.isEnabled = false

        leftChart.data = data
        rightChart.data = data2
    }
}