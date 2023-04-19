package com.vsu.eyesdoctorapp.ui.diagnostics

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import com.vsu.eyesdoctorapp.R
import com.vsu.eyesdoctorapp.databinding.ActivitySingleDiagnosticsBinding
import com.vsu.eyesdoctorapp.service.diagnostics.LetterEnum
import com.vsu.eyesdoctorapp.service.diagnostics.LetterScale
import com.vsu.eyesdoctorapp.service.diagnostics.SequenceGenerator

/* TODO
* 1. Сделать звуковые сигналы при смене буквы и по окончании основного таймера
* 2. Реализовать запись и проигрывание аудио в конце основного таймера
* 3. Реализовать пользовательский ввод
* 4. Реализовать алгоритм получения результатов
* 5. Вынести повторяющиеся методы в сервисы*/

class SingleDiagnosticsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleDiagnosticsBinding
    private lateinit var sequenceGenerator: SequenceGenerator
    private lateinit var sequence: ArrayList<LetterScale>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // hide top bar
        supportActionBar?.hide()

        binding = ActivitySingleDiagnosticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get sequence
        sequenceGenerator = SequenceGenerator.instance
        sequence = sequenceGenerator.generate()

        changeImage()

        object : CountDownTimer(5000, 1000) {
            override fun onTick(p0: Long) {
                binding.tvStartTimer.text = "${p0 / 1000}"
            }

            override fun onFinish() {
                binding.ivMainLetter.isGone = false
                binding.tvStartTimer.isGone = true
                startMainTimer()
            }

        }.start()

    }

    private fun changeImage() {
        if (sequence.isEmpty()) return

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

        binding.tvScale.text = String.format("V = %.1f", currentLetter.scale)
        binding.tvRemaining.text = "Remaining: ${sequence.size}"
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

    private fun startMainTimer() {
        object : CountDownTimer(5000, 100) {
            override fun onTick(p0: Long) {
                binding.tvTimer.text = String.format("TIMER %.1f s", p0.toDouble() / 1000)
            }

            override fun onFinish() {
                if (sequence.isNotEmpty()) {
                    changeImage()
                    this.start()
                } else {
                    binding.tvTimer.text = "DONE"
                }
            }

        }.start()
    }
}