package com.vsu.eyesdoctorapp.ui.diagnostics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.vsu.eyesdoctorapp.R

class PairedDiagnosticsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        setContentView(R.layout.activity_paired_diagnostics)

        findViewById<Button>(R.id.btn_letter_sh).setOnClickListener {
            findViewById<TextView>(R.id.tv_main_letter).text = "Ш"
        }
        findViewById<Button>(R.id.btn_letter_b).setOnClickListener {
            findViewById<TextView>(R.id.tv_main_letter).text = "Б"
        }
        findViewById<Button>(R.id.btn_letter_m).setOnClickListener {
            findViewById<TextView>(R.id.tv_main_letter).text = "М"
        }
        findViewById<Button>(R.id.btn_letter_n).setOnClickListener {
            findViewById<TextView>(R.id.tv_main_letter).text = "Н"
        }
        findViewById<Button>(R.id.btn_letter_k).setOnClickListener {
            findViewById<TextView>(R.id.tv_main_letter).text = "К"
        }
        findViewById<Button>(R.id.btn_letter_bi).setOnClickListener {
            findViewById<TextView>(R.id.tv_main_letter).text = "Ы"
        }
        findViewById<Button>(R.id.btn_letter_i).setOnClickListener {
            findViewById<TextView>(R.id.tv_main_letter).text = "И"
        }
    }
}