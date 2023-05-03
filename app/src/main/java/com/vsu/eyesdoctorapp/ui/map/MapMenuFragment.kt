package com.vsu.eyesdoctorapp.ui.map

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.vsu.eyesdoctorapp.R
import com.vsu.eyesdoctorapp.service.map.CustomWebChromeClient

/**
 * TODO
 * Сделать так, чтобы пользователь мог включить геолокацию,
 * и карта определила его местоположение
 * */

class MapMenuFragment : Fragment() {

    private val LOCATION_REQ_CODE = 1002

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map_menu, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkForPermission()

        val wvMap = view.findViewById<WebView>(R.id.wv_map)
        wvMap.webChromeClient = CustomWebChromeClient()
        wvMap.settings.javaScriptEnabled = true
        wvMap.settings.setGeolocationEnabled(true)
        wvMap.loadUrl("https://www.google.com/maps/search/ophthalmology/?authuser=0")
    }

    private fun checkForPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                return
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION) -> {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected, and what
                // features are disabled if it's declined. In this UI, include a
                // "cancel" or "no thanks" button that lets the user continue
                // using your app without granting the permission.
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Необходимо разрешене")
                builder.setMessage("Для получения точного местоположения необходимо разрешение использования геолокации")
                builder.setPositiveButton("Хорошо") { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.cancel()
                }
                builder.show()
            }
            else -> {
                // You can directly ask for the permission.
                ActivityCompat.requestPermissions(
                    this.requireActivity(),
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_REQ_CODE
                )
            }
        }
    }

}