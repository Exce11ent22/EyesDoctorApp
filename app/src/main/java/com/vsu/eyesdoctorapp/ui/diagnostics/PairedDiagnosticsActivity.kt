package com.vsu.eyesdoctorapp.ui.diagnostics

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.vsu.eyesdoctorapp.R
import com.vsu.eyesdoctorapp.service.diagnostics.LetterEnum
import com.vsu.eyesdoctorapp.service.diagnostics.LetterScale
import com.vsu.eyesdoctorapp.service.diagnostics.SequenceGenerator


class PairedDiagnosticsActivity : AppCompatActivity() {

    private lateinit var sequenceGenerator: SequenceGenerator
    private lateinit var sequence: ArrayList<LetterScale>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        setContentView(R.layout.activity_paired_diagnostics)

        // get sequence
        sequenceGenerator = SequenceGenerator.instance
        sequence = sequenceGenerator.generate()

        // initial image setup
        changeImageBySequence()

        // set onClick methods
        configureButtons()
    }

    private fun getResourceByLetter(letter: LetterEnum): Int {

        return when (letter) {
            LetterEnum.SH -> R.drawable.im_letter_sh
            LetterEnum.B -> R.drawable.im_letter_b
            LetterEnum.M -> R.drawable.im_letter_m
            LetterEnum.N -> R.drawable.im_letter_n
            LetterEnum.K -> R.drawable.im_letter_k
            LetterEnum.BI -> R.drawable.im_letter_bi
            LetterEnum.I -> R.drawable.im_letter_i
        }
    }

    private fun getSizeByMM(mm: Double): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_MM,
            mm.toFloat(),
            resources.displayMetrics
        ).toInt()
    }

    private fun getLetterSizeMM(scale: Double): Double {
        Log.d("SIZE_INFO", "size: ${(7.0 / scale)}; scale: $scale")
        // TODO full formula
        return (7.0 / scale)
    }

    private fun changeImageBySequence() {
        if (sequence.size > 0){
            // get element
            val currentLetter = sequence.removeLast()

            // change image
            val mainImage = findViewById<ImageView>(R.id.iv_main_letter)
            mainImage.setImageResource(getResourceByLetter(currentLetter.letter))

            val layoutParams = mainImage.layoutParams

            val size = getSizeByMM(getLetterSizeMM(currentLetter.scale))
            layoutParams.width = size
            layoutParams.height = size

            mainImage.layoutParams = layoutParams
        } else {
            // show
            findViewById<Button>(R.id.btn_back).isGone = false
            findViewById<TextView>(R.id.tv_result_percentage).isGone = false
            findViewById<TextView>(R.id.tv_result_text).isGone = false

            // hide
            findViewById<ImageView>(R.id.iv_main_letter).isGone = true
            findViewById<LinearLayout>(R.id.ll_top).isGone = true
            findViewById<LinearLayout>(R.id.ll_bottom).isGone = true
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (sequence.size == 0) {
            super.onBackPressed()
        }
    }

    private fun configureButtons() {
        findViewById<Button>(R.id.btn_letter_sh).setOnClickListener {
            changeImageBySequence()
        }
        findViewById<Button>(R.id.btn_letter_b).setOnClickListener {
            changeImageBySequence()
        }
        findViewById<Button>(R.id.btn_letter_m).setOnClickListener {
            changeImageBySequence()
        }
        findViewById<Button>(R.id.btn_letter_n).setOnClickListener {
            changeImageBySequence()
        }
        findViewById<Button>(R.id.btn_letter_k).setOnClickListener {
            changeImageBySequence()
        }
        findViewById<Button>(R.id.btn_letter_bi).setOnClickListener {
            changeImageBySequence()
        }
        findViewById<Button>(R.id.btn_letter_i).setOnClickListener {
            changeImageBySequence()
        }
        findViewById<Button>(R.id.btn_letter_none).setOnClickListener {
            changeImageBySequence()
        }

        findViewById<Button>(R.id.btn_back).setOnClickListener {
            onBackPressed()
        }
    }
}