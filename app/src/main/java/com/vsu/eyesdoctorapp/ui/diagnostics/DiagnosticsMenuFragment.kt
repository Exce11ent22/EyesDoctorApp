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
import com.vsu.eyesdoctorapp.R
import kotlin.math.sin

/* TODO
* 1. Доделать информацию на экране
* 2. Сделать свич на парную и одиночную диагностику
* 3. Прокидывать вверх параметр, какой это глаз
* 4. сделать настройку расстояния*/

class DiagnosticsMenuFragment : Fragment() {

    private val RECORD_AUDIO_REQ_CODE = 1001
    private var isPaired = true

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
            startDiagnostics(view)
        }
        view.findViewById<Button>(R.id.btn_right).setOnClickListener {
            startDiagnostics(view)
        }

        // TODO refactor this
        val chooseDistanceSpinner = view.findViewById<Spinner>(R.id.spn_choose_distance)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.distances,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            chooseDistanceSpinner.adapter = adapter
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
        val lineDataSet = LineDataSet(dataList, "Последние N измерений")
        val lineDataSet2 = LineDataSet(dataList2, "Последние N измерений")
        val data = LineData(lineDataSet)
        val data2 = LineData(lineDataSet2)
        leftChart.animateX(3000, Easing.EaseOutSine)
        rightChart.animateX(4500, Easing.EaseInSine)

        leftChart.description.isEnabled = false
        rightChart.description.isEnabled = false

        leftChart.data = data
        rightChart.data = data2
    }

    private fun startDiagnostics(view: View) {
        Log.d("SWITCH", "${view.findViewById<SwitchMaterial>(R.id.sw_friend).isChecked}")
        if (view.findViewById<SwitchMaterial>(R.id.sw_friend).isChecked) {
            startPairedDiagnostics()
        } else {
            startSingleDiagnostics()
        }
    }

    private fun startPairedDiagnostics() {
        val i = Intent(this.context, PairedDiagnosticsActivity::class.java)
        startActivity(i)
    }

    private fun startSingleDiagnostics() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED -> {
                val i = Intent(this.context, SingleDiagnosticsActivity::class.java)
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
}