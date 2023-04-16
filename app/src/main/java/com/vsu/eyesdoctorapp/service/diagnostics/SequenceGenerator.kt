package com.vsu.eyesdoctorapp.service.diagnostics

import com.vsu.eyesdoctorapp.R

class SequenceGenerator {

    companion object {
        val instance = SequenceGenerator()
        val alphabet = R.string.diagnostic_alphabet.toString()
        const val letterPerScale = 3
    }

    fun generate() : ArrayList<LetterScale> {
        var sequence = ArrayList<LetterScale>()
        var tempLetter = '$'
        for (scale in 1..10) {
            repeat (letterPerScale) {
                var currentLetter = '$'
                while (currentLetter == tempLetter) {
                    currentLetter = alphabet.random()
                }
                tempLetter = currentLetter
                sequence.add(LetterScale(currentLetter, scale.toDouble()/10))
            }
        }
        sequence.reverse()
        return sequence
    }

}