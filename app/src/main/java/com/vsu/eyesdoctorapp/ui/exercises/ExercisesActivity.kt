package com.vsu.eyesdoctorapp.ui.exercises

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import com.vsu.eyesdoctorapp.databinding.ActivityExercisesBinding
import com.vsu.eyesdoctorapp.service.exercises.Exercise
import com.vsu.eyesdoctorapp.service.exercises.ExercisesComplex
import com.vsu.eyesdoctorapp.service.exercises.ExercisesComplexBuilder
import java.io.IOException

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityExercisesBinding
private lateinit var complex: ExercisesComplex
private lateinit var currentExercise: Exercise

private var player: MediaPlayer? = null
private var timer: CountDownTimer? = null

class ExercisesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // hide top bar
        supportActionBar?.hide()

        binding = ActivityExercisesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        complex = ExercisesComplexBuilder.getPartialComplex(this)

        binding.btnStartStop.text = "Начать"
        loadExercise()

        binding.btnStartStop.setOnClickListener {
            if (player != null) {
                return@setOnClickListener
            }
            if (timer != null) {
                timer!!.cancel()
                timer = null
            }
            player = MediaPlayer.create(this, currentExercise.resourceId)
            player?.apply {
                try {
                    start()
                    setOnCompletionListener {
                        loadExercise()
                    }
                } catch (e: IOException) {
                    Log.e("AUDIO", "prepare() failed")
                }
            }
            timer = object : CountDownTimer((currentExercise.durationSec * 1000).toLong(), 100){
                override fun onTick(p0: Long) {
                    binding.btnStartStop.text = String.format("%.1f сек.", p0.toDouble() / 1000)
                }

                override fun onFinish() {
                    binding.btnStartStop.text = "Начать"
                }

            }
            timer!!.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (player != null) {
            player!!.stop()
            player!!.release()
            player = null
        }
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
    }

    private fun loadExercise() {
        if (complex.isItDone()) {
            // write to database
            onBackPressed()
            return
        }
        if (player != null) {
            player!!.stop()
            player!!.release()
            player = null
        }
        binding.tvExercisesRemaining.text = "Осталось упражнений: ${complex.remaining()}"

        currentExercise = complex.getNextExercise()
        binding.tvExerciseInstruction.text = currentExercise.description
    }
}