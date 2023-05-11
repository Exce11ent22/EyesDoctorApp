package com.vsu.eyesdoctorapp.service.diagnostics

import kotlin.math.round

class ResultCalculator(private val originalSeq: ArrayList<LetterScale>) {

    private var currentIndex = 0
    private var maxIndex = originalSeq.size
    private var mistakes = 0
    private var remaining = SequenceGenerator.letterPerScale
    private var result : Double = 0.0
    private var wasMistake = false

    fun input(letter: String){
        if (isDone()) return

        if (originalSeq[currentIndex].letter.letter.lowercase() != letter.lowercase()) {
            mistakes++
            wasMistake = true
        }

        remaining--

        if (remaining == 0) {
            result += (10.0 - (10.0 / SequenceGenerator.letterPerScale) * mistakes)
            remaining = SequenceGenerator.letterPerScale
            mistakes = 0
        }

        currentIndex++
    }

    fun getResult() : Int {
        return round(result).toInt()
    }

    fun isDone() : Boolean {
        if (currentIndex == maxIndex) return true

        return remaining == SequenceGenerator.letterPerScale && wasMistake
    }

    class Answers(private val n: Int){

        var answers = generateAnswers()
        private fun generateAnswers() : ArrayList<String>{
            val answers = ArrayList<String>()
            for (i in 0 until n){
                answers.add("?")
            }
            return answers
        }

    }

}