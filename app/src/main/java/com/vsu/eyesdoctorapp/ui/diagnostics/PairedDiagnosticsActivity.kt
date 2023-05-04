package com.vsu.eyesdoctorapp.ui.diagnostics

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.vsu.eyesdoctorapp.R
import com.vsu.eyesdoctorapp.databinding.ActivityPairedDiagnosticsBinding
import com.vsu.eyesdoctorapp.service.diagnostics.LetterEnum
import com.vsu.eyesdoctorapp.service.diagnostics.LetterScale
import com.vsu.eyesdoctorapp.service.diagnostics.SequenceGenerator

/* TODO
* 1. Доделать алгоритм результатов
* 2. получать параметр, какой это глаз*/

class PairedDiagnosticsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPairedDiagnosticsBinding
    private lateinit var sequenceGenerator: SequenceGenerator
    private lateinit var sequence: ArrayList<LetterScale>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityPairedDiagnosticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        if (sequence.size > 0) {
            // get element
            val currentLetter = sequence.removeLast()

            // change image
            val mainImage = binding.ivMainLetter
            mainImage.setImageResource(getResourceByLetter(currentLetter.letter))

            val layoutParams = mainImage.layoutParams

            val size = getSizeByMM(getLetterSizeMM(currentLetter.scale))
            layoutParams.width = size
            layoutParams.height = size

            mainImage.layoutParams = layoutParams
        } else {
            showResultLayout()
        }
    }

//    @Deprecated("Deprecated in Java")
//    override fun onBackPressed() {
//        if (sequence.size == 0) {
//            super.onBackPressed()
//        }
//    }

    private fun configureButtons() {
        binding.btnLetterSh.setOnClickListener {
            changeImageBySequence()
        }
        binding.btnLetterB.setOnClickListener {
            changeImageBySequence()
        }
        binding.btnLetterM.setOnClickListener {
            changeImageBySequence()
        }
        binding.btnLetterN.setOnClickListener {
            changeImageBySequence()
        }
        binding.btnLetterK.setOnClickListener {
            changeImageBySequence()
        }
        binding.btnLetterBi.setOnClickListener {
            changeImageBySequence()
        }
        binding.btnLetterI.setOnClickListener {
            changeImageBySequence()
        }
        binding.btnLetterNone.setOnClickListener {
            changeImageBySequence()
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showResultLayout() {
        binding.clInput.isGone = true
        binding.clResults.isGone = false
    }

}