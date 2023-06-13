package com.vsu.eyesdoctorapp.ui.diagnostics

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Switch
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.transition.SlideDistanceProvider
import com.vsu.eyesdoctorapp.R
import kotlin.math.sin

/* TODO
* 1. Доделать информацию на экране
* 2. Сделать свич на парную и одиночную диагностику
* 3. Прокидывать вверх параметр, какой это глаз
* 4. сделать настройку расстояния*/

class DiagnosticsMenuFragment : Fragment() {

    companion object {
        private const val RECORD_AUDIO_REQ_CODE = 1001

        const val LEFT_SIDE = "left"
        const val RIGHT_SIDE = "right"
        const val DISTANCE = "distance"
        const val SIDE = "side"
    }

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

        val chooseDistanceSpinner = view.findViewById<Spinner>(R.id.spn_choose_distance)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.distances,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            chooseDistanceSpinner.adapter = adapter
        }

        view.findViewById<Button>(R.id.btn_left).setOnClickListener {
            startDiagnostics(view, getDistanceFromSpinner(chooseDistanceSpinner), LEFT_SIDE)
        }
        view.findViewById<Button>(R.id.btn_right).setOnClickListener {
            startDiagnostics(view, getDistanceFromSpinner(chooseDistanceSpinner), RIGHT_SIDE)
        }

    }

    private fun startDiagnostics(view: View, distance: Double, side: String) {
        Log.d("SWITCH", "${view.findViewById<SwitchMaterial>(R.id.sw_friend).isChecked}")
        if (view.findViewById<SwitchMaterial>(R.id.sw_friend).isChecked) {
            startPairedDiagnostics(distance, side)
        } else {
            startSingleDiagnostics(distance, side)
        }
    }

    private fun startPairedDiagnostics(distance: Double, side: String) {
        val i = Intent(this.context, PairedDiagnosticsActivity::class.java)
        val b = Bundle()
        b.putDouble(DISTANCE, distance)
        b.putString(SIDE, side)
        i.putExtras(b)
        startActivity(i)
    }

    private fun startSingleDiagnostics(distance: Double, side: String) {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED -> {
                val i = Intent(this.context, SingleDiagnosticsActivity::class.java)
                val b = Bundle()
                b.putDouble(DISTANCE, distance)
                b.putString(SIDE, side)
                i.putExtras(b)
                startActivity(i)
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.RECORD_AUDIO) -> {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected, and what
            // features are disabled if it's declined. In this UI, include a
            // "cancel" or "no thanks" button that lets the user continue
            // using your app without granting the permission.
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Необходимо разрешене")
                builder.setMessage("Для проведения диагностики необходимо записывать аудио. Добавьте разрешение на запись аудио.")
                builder.setPositiveButton("Хорошо") { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.cancel()
                }
                builder.show()
            }
            else -> {
                // You can directly ask for the permission.
                ActivityCompat.requestPermissions(
                    this.requireActivity(),
                    arrayOf(android.Manifest.permission.RECORD_AUDIO),
                    RECORD_AUDIO_REQ_CODE
                )
            }
        }
    }

    private fun getDistanceFromSpinner(spinner: Spinner) : Double {
        var distanceStr = spinner.selectedItem.toString()
        distanceStr = distanceStr.split(" ")[0]
        Log.d("DISTANCE", distanceStr)
        return distanceStr.toDouble()
    }

    private fun drawCharts(view: View) {
        val leftChart: LineChart = view.findViewById(R.id.lc_left)
        val rightChart: LineChart = view.findViewById(R.id.lc_right)

        val dataList = getDiagnosticsData1()
        val dataList2 = getDiagnosticsData2()

        val lineDataSet = LineDataSet(dataList, "Последние ${dataList.size} измерений")
        val lineDataSet2 = LineDataSet(dataList2, "Последние ${dataList2.size} измерений")
        val data = LineData(lineDataSet)
        val data2 = LineData(lineDataSet2)
        leftChart.animateX(1400, Easing.EaseInOutQuad)
        rightChart.animateX(1400, Easing.EaseInOutQuad)

        leftChart.description.isEnabled = false
        rightChart.description.isEnabled = false

        leftChart.data = data
        rightChart.data = data2
    }

    private fun getDiagnosticsData1() : ArrayList<Entry> {
        val dataList = ArrayList<Entry>()
        dataList.add(Entry(1f, 83f))
        dataList.add(Entry(2f, 80f))
        dataList.add(Entry(3f, 80f))
        dataList.add(Entry(4f, 80f))
        dataList.add(Entry(5f, 76f))
        dataList.add(Entry(6f, 70f))
        dataList.add(Entry(7f, 70f))
        dataList.add(Entry(8f, 70f))
        dataList.add(Entry(9f, 76f))
        dataList.add(Entry(10f, 66f))
        dataList.add(Entry(11f, 63f))
        dataList.add(Entry(12f, 60f))
        dataList.add(Entry(13f, 60f))
        dataList.add(Entry(14f, 56f))
        dataList.add(Entry(15f, 56f))
        return dataList
    }

    private fun getDiagnosticsData2() : ArrayList<Entry> {
        val dataList = ArrayList<Entry>()
        dataList.add(Entry(1f, 73f))
        dataList.add(Entry(2f, 70f))
        dataList.add(Entry(3f, 70f))
        dataList.add(Entry(4f, 66f))
        dataList.add(Entry(5f, 66f))
        dataList.add(Entry(6f, 60f))
        dataList.add(Entry(7f, 60f))
        dataList.add(Entry(8f, 60f))
        dataList.add(Entry(9f, 50f))
        dataList.add(Entry(10f, 50f))
        dataList.add(Entry(11f, 46f))
        dataList.add(Entry(12f, 46f))
        dataList.add(Entry(13f, 46f))
        dataList.add(Entry(14f, 40f))
        dataList.add(Entry(15f, 40f))
        return dataList
    }
}