package com.vsu.eyesdoctorapp.ui.diagnostics

import android.app.AlertDialog
import android.content.DialogInterface
import android.media.MediaRecorder
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsu.eyesdoctorapp.R
import com.vsu.eyesdoctorapp.databinding.ActivitySingleDiagnosticsBinding
import com.vsu.eyesdoctorapp.service.diagnostics.LetterEnum
import com.vsu.eyesdoctorapp.service.diagnostics.LetterScale
import com.vsu.eyesdoctorapp.service.diagnostics.ResultCalculator
import com.vsu.eyesdoctorapp.service.diagnostics.SequenceGenerator
import com.vsu.eyesdoctorapp.ui.diagnostics.recycler.InputAdapter
import java.io.IOException

/* TODO
* 1. Сделать звуковые сигналы при смене буквы и по окончании основного таймера
* 2. Вынести повторяющиеся методы в сервисы
* 3. Яркость на 100% */

class SingleDiagnosticsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleDiagnosticsBinding
    private lateinit var sequenceGenerator: SequenceGenerator
    private lateinit var sequence: ArrayList<LetterScale>
    private lateinit var answers: ResultCalculator.Answers
    private var currentIndex = 0

    private var recorder: MediaRecorder? = null
    private lateinit var audioPaths: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // hide top bar
        supportActionBar?.hide()

        binding = ActivitySingleDiagnosticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get sequence
        sequenceGenerator = SequenceGenerator.instance
        sequence = sequenceGenerator.generate()

        answers = ResultCalculator.Answers(sequence.size)

        // get audio paths
        audioPaths = generateAudioPaths(sequence.size)

        changeImage()

        // start timer -> diagnostics -> input -> result
        showStartTimer()

    }

    override fun onStop() {
        super.onStop()
        recorder?.stop()
        recorder?.release()
        recorder = null
    }

    private fun changeImage() {
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

            // additional info
            binding.tvScale.text = String.format("V = %.1f", currentLetter.scale)
            binding.tvRemaining.text = String.format("Remaining: %d", sequence.size - currentIndex - 1)
        }
        currentIndex++
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

    private fun startMainTimer() {
        object : CountDownTimer(5000, 100) {

            override fun onTick(p0: Long) {
                binding.tvTimer.text = String.format("%.1f s", p0.toDouble() / 1000)
            }

            override fun onFinish() {
                if (currentIndex < sequence.size) {
                    stopRecording()
                    changeImage()
                    startRecording()
                    this.start()
                } else {
                    stopRecording()
                    showInputLayout()
                }
            }

        }.start()
    }

    // audio
    private fun generateAudioPaths(n: Int) : ArrayList<String> {
        val list = ArrayList<String>()
        for (i in 0 until n) {
            list.add("${externalCacheDir?.absolutePath}/letter_$i.mp4")
        }
        return list
    }

    private fun startRecording() {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioSamplingRate(44100)
            setAudioEncodingBitRate(384000)
            setOutputFile(audioPaths[currentIndex - 1])
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)

            try {
                prepare()
            } catch (e: IOException) {
                Log.e("AUDIO", "prepare() failed")
            }

            start()
        }
    }

    private fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
    }

    // showStartTimer -> showDiagnostics
    private fun showStartTimer(){
        binding.clStartTimer.isGone = false
        binding.clDiagnostics.isGone = true
        binding.clInput.isGone = true
        binding.clResults.isGone = true

        val startTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(p0: Long) {
                val t = p0 + 1000
                binding.tvStartTimer.text = "${(t / 1000)}"
            }

            override fun onFinish() {
                showDiagnostics()
            }
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.dlg_paired_diagnostics_header))
        builder.setMessage(getString(R.string.dlg_paired_diagnostics_instruction))
        builder.setPositiveButton(R.string.dlg_paired_diagnostics_ok)
        { dialogInterface: DialogInterface, i: Int ->
            dialogInterface.cancel()
            startTimer.start()
        }
        builder.show()
    }


    // showDiagnostics -> showInputLayout
    private fun showDiagnostics(){
        binding.clStartTimer.isGone = true
        binding.clDiagnostics.isGone = false
        binding.clInput.isGone = true
        binding.clResults.isGone = true

        startRecording()
        startMainTimer()
    }

    // showInputLayout -> showResults
    private fun showInputLayout(){
        binding.clStartTimer.isGone = true
        binding.clDiagnostics.isGone = true
        binding.clInput.isGone = false
        binding.clResults.isGone = true

        binding.btnConfirm.setOnClickListener {
            showResults()
        }

        // configure recycler view
        binding.rvInput.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvInput.layoutManager = layoutManager

        binding.rvInput.adapter = InputAdapter(
            this,
            audioPaths,
            answers,
            resources.getStringArray(R.array.letters)
        )
    }

    private fun showResults(){
        binding.clStartTimer.isGone = true
        binding.clDiagnostics.isGone = true
        binding.clInput.isGone = true
        binding.clResults.isGone = false

        val resultCalculator = ResultCalculator(sequence)
        for (el in answers.answers) {
            resultCalculator.input(el)
        }
        binding.tvResultPercentage.text = "${resultCalculator.getResult()} %"

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        if (intent.extras?.getString(DiagnosticsMenuFragment.SIDE) == DiagnosticsMenuFragment.RIGHT_SIDE) {
            binding.tvResultText.text = getString(R.string.result_side, "правого")
        } else {
            binding.tvResultText.text = getString(R.string.result_side, "левого")
        }
    }
}