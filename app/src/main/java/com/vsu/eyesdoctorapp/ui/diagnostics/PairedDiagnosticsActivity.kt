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
import com.vsu.eyesdoctorapp.service.diagnostics.ResultCalculator
import com.vsu.eyesdoctorapp.service.diagnostics.SequenceGenerator

/* TODO
* 1. Сделать сохранение результатов
* 2. Вынести общие методы в тулзы
* */

class PairedDiagnosticsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPairedDiagnosticsBinding
    private lateinit var sequenceGenerator: SequenceGenerator
    private lateinit var sequence: ArrayList<LetterScale>
    private lateinit var resultCalculator: ResultCalculator
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityPairedDiagnosticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get sequence
        sequenceGenerator = SequenceGenerator.instance
        sequence = sequenceGenerator.generate()

        // initialize the result calculator
        resultCalculator = ResultCalculator(sequence)

        // set diagnostics layout
        showDiagnosticsLayout()

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
        if (intent.extras != null) {
            val distance = intent.extras!!.getDouble(DiagnosticsMenuFragment.DISTANCE)
            return (7.0 / scale) * (distance / 5.0)
        }
        return (7.0 / scale)
    }

    private fun changeImageBySequence() {
        if (currentIndex < sequence.size) {
            // get element
            val currentLetter = sequence[currentIndex]

            // change image
            val mainImage = binding.ivMainLetter
            mainImage.setImageResource(getResourceByLetter(currentLetter.letter))

            // set image size
            val layoutParams = mainImage.layoutParams

            val size = getSizeByMM(getLetterSizeMM(currentLetter.scale))
            layoutParams.width = size
            layoutParams.height = size

            mainImage.layoutParams = layoutParams
        }
        currentIndex++

        // if results is done -> show results layout
        if (resultCalculator.isDone()) {
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
            resultCalculator.input(LetterEnum.SH.letter)
            changeImageBySequence()
        }
        binding.btnLetterB.setOnClickListener {
            resultCalculator.input(LetterEnum.B.letter)
            changeImageBySequence()
        }
        binding.btnLetterM.setOnClickListener {
            resultCalculator.input(LetterEnum.M.letter)
            changeImageBySequence()
        }
        binding.btnLetterN.setOnClickListener {
            resultCalculator.input(LetterEnum.N.letter)
            changeImageBySequence()
        }
        binding.btnLetterK.setOnClickListener {
            resultCalculator.input(LetterEnum.K.letter)
            changeImageBySequence()
        }
        binding.btnLetterBi.setOnClickListener {
            resultCalculator.input(LetterEnum.BI.letter)
            changeImageBySequence()
        }
        binding.btnLetterI.setOnClickListener {
            resultCalculator.input(LetterEnum.I.letter)
            changeImageBySequence()
        }
        binding.btnLetterNone.setOnClickListener {
            resultCalculator.input("?")
            changeImageBySequence()
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showResultLayout() {
        // hide diagnostics layout
        binding.clInput.isGone = true

        // show results layout
        binding.clResults.isGone = false

        // set the results
        binding.tvResultPercentage.text = getString(R.string.result_percentage, resultCalculator.getResult().toString())
        if (intent.extras?.getString(DiagnosticsMenuFragment.SIDE) == DiagnosticsMenuFragment.RIGHT_SIDE) {
            binding.tvResultText.text = getString(R.string.result_side, "правого")
        } else {
            binding.tvResultText.text = getString(R.string.result_side, "левого")
        }
    }

    private fun showDiagnosticsLayout() {
        // show diagnostics layout
        binding.clInput.isGone = false

        // hide results layout
        binding.clResults.isGone = true
    }

}