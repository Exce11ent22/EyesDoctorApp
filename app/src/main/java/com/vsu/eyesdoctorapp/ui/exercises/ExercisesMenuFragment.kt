package com.vsu.eyesdoctorapp.ui.exercises

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.vsu.eyesdoctorapp.R
import com.vsu.eyesdoctorapp.ui.diagnostics.DiagnosticsMenuFragment
import com.vsu.eyesdoctorapp.ui.diagnostics.SingleDiagnosticsActivity


/**
 * A simple [Fragment] subclass.
 * Use the [ExercisesMenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExercisesMenuFragment : Fragment() {


    // daily usage
    private lateinit var pieChart: PieChart

    // exercises schedule
    private lateinit var barChart: BarChart

    private lateinit var partialBtn: Button
    private lateinit var avetisovBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercises_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        partialBtn = view.findViewById(R.id.btn_partial_complex)
        avetisovBtn = view.findViewById(R.id.btn_avetisov_complex)

        partialBtn.setOnClickListener {
            val i = Intent(context, ExercisesActivity::class.java)
            startActivity(i)
        }

        avetisovBtn.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Внимание!")
            builder.setMessage("Реализация не завершена")
            builder.setPositiveButton("Буду ждать!")
            { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.cancel()
            }
            builder.show()
        }
        drawPieChart(view)
        drawBarChart(view)
    }

    private fun drawBarChart(view: View) {
        barChart = view.findViewById(R.id.bc_weekly_amount_chart)

        val values: ArrayList<BarEntry> = ArrayList()
        values.add(BarEntry(1f, 2f))
        values.add(BarEntry(2f, 1f))
        values.add(BarEntry(3f, 0f))
        values.add(BarEntry(4f, 3f))
        values.add(BarEntry(5f, 2f))
        values.add(BarEntry(6f, 2f))
        values.add(BarEntry(7f, 1f))

        val set = BarDataSet(values, "Сделано упражнений")

        val dataSets: ArrayList<IBarDataSet> = ArrayList()
        dataSets.add(set)

        val data = BarData(dataSets)
        barChart.data = data
        barChart.description.isEnabled = false
        barChart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        barChart.animateY(1400, Easing.EaseInOutQuad)
    }

    private fun drawPieChart(view: View) {
        pieChart = view.findViewById(R.id.pc_daily_usage_chart)
        pieChart.description.isEnabled = false
        pieChart.isDrawHoleEnabled = true
        pieChart.transparentCircleRadius = 24f
        pieChart.holeRadius = 16f
        pieChart.isRotationEnabled = false
        pieChart.animateY(1400, Easing.EaseInOutQuad)
        val entries: ArrayList<PieEntry> = ArrayList()
        val usage = 7.4f
        entries.add(PieEntry(usage, "Время экрана [часов]"))
        entries.add(PieEntry((24f - usage), "Осталось") )

        val colors: ArrayList<Int> = ArrayList()
        colors.add(ColorTemplate.COLORFUL_COLORS[0])
        colors.add(ColorTemplate.COLORFUL_COLORS[1])

        val dataSet = PieDataSet(entries, "")
        dataSet.colors = colors
        val data = PieData(dataSet)
        data.setValueTextSize(16f)
        data.setValueTextColor(Color.WHITE)
        pieChart.data = data
        pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
        pieChart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        pieChart.legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        pieChart.setDrawEntryLabels(false)
    }
}