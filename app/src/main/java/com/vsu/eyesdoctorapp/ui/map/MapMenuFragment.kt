package com.vsu.eyesdoctorapp.ui.map

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.core.content.ContextCompat
import com.vsu.eyesdoctorapp.R
import com.vsu.eyesdoctorapp.service.map.CustomWebChromeClient
import com.vsu.eyesdoctorapp.ui.diagnostics.SingleDiagnosticsActivity

/**
 * TODO
 * Сделать так, чтобы пользователь мог включить геолокацию,
 * и карта определила его местоположение
 * */

class MapMenuFragment : Fragment() {

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

        val wvMap = view.findViewById<WebView>(R.id.wv_map)
        wvMap.webChromeClient = CustomWebChromeClient()
        wvMap.settings.javaScriptEnabled = true
        wvMap.settings.setGeolocationEnabled(true)
        wvMap.loadUrl("https://www.google.com/maps/search/ophthalmology/?authuser=0")
    }
}