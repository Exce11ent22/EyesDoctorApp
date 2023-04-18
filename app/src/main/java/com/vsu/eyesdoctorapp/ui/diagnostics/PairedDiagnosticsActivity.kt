package com.vsu.eyesdoctorapp.ui.diagnostics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.vsu.eyesdoctorapp.R

class PairedDiagnosticsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        setContentView(R.layout.activity_paired_diagnostics)

        findViewById<Button>(R.id.btn_letter_sh).setOnClickListener {
            findViewById<ImageView>(R.id.iv_main_letter).setImageResource(R.drawable.im_letter_sh)
        }
        findViewById<Button>(R.id.btn_letter_b).setOnClickListener {
            findViewById<ImageView>(R.id.iv_main_letter).setImageResource(R.drawable.im_letter_b)
        }
        findViewById<Button>(R.id.btn_letter_m).setOnClickListener {
            findViewById<ImageView>(R.id.iv_main_letter).setImageResource(R.drawable.im_letter_m)
        }
        findViewById<Button>(R.id.btn_letter_n).setOnClickListener {
            findViewById<ImageView>(R.id.iv_main_letter).setImageResource(R.drawable.im_letter_n)
        }
        findViewById<Button>(R.id.btn_letter_k).setOnClickListener {
            findViewById<ImageView>(R.id.iv_main_letter).setImageResource(R.drawable.im_letter_k)
        }
        findViewById<Button>(R.id.btn_letter_bi).setOnClickListener {
            findViewById<ImageView>(R.id.iv_main_letter).setImageResource(R.drawable.im_letter_bi)
        }
        findViewById<Button>(R.id.btn_letter_i).setOnClickListener {
            findViewById<ImageView>(R.id.iv_main_letter).setImageResource(R.drawable.im_letter_i)
        }
    }
}