package com.vsu.eyesdoctorapp.service.exercises

import java.util.Queue

class ExercisesComplex(val exercises: Queue<Exercise>) {

    fun getNextExercise() : Exercise {
        return exercises.remove()
    }

    fun isItDone() : Boolean{
        return exercises.size == 0
    }

    fun remaining() : Int {
        return exercises.size
    }

}