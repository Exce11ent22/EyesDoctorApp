package com.vsu.eyesdoctorapp.service.exercises

import android.content.Context
import com.vsu.eyesdoctorapp.R
import java.util.LinkedList
import java.util.Queue

class ExercisesComplexBuilder {

    companion object {
        fun getPartialComplex(context: Context) : ExercisesComplex {
            val exercises : Queue<Exercise> = LinkedList()
            exercises.add(Exercise(R.raw.blinking, context.getString(R.string.blinking_description), 35))
            exercises.add(Exercise(R.raw.up_down, context.getString(R.string.up_down_description), 65))
            exercises.add(Exercise(R.raw.left_right, context.getString(R.string.left_right_description), 65))
            exercises.add(Exercise(R.raw.zigzag, context.getString(R.string.zig_zag_description), 66))
            exercises.add(Exercise(R.raw.circle_right, context.getString(R.string.circle_right_description), 30))
            exercises.add(Exercise(R.raw.circle_left, context.getString(R.string.circle_left_description), 30))
            exercises.add(Exercise(R.raw.close_open, context.getString(R.string.close_open_description), 42))
            return ExercisesComplex(exercises)
        }
    }

}