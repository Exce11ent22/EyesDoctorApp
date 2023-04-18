package com.vsu.eyesdoctorapp.ui.diagnostics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vsu.eyesdoctorapp.R
import com.vsu.eyesdoctorapp.service.diagnostics.LetterScale
import com.vsu.eyesdoctorapp.service.diagnostics.SequenceGenerator

class DiagnosticsResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diagnostics_result)
    }
}