package com.vsu.eyesdoctorapp.ui.map

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.vsu.eyesdoctorapp.R

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
        wvMap.settings.javaScriptEnabled = true
        wvMap.loadUrl("https://www.google.com/maps/search/ophthalmology/?authuser=0")
    }
}