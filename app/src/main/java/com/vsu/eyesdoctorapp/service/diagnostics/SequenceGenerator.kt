package com.vsu.eyesdoctorapp.service.diagnostics


class SequenceGenerator {

    companion object {
        val instance = SequenceGenerator()
        val alphabet = LetterEnum.values()
        const val letterPerScale = 3
        const val startScale = 1.0
        const val endScale = 0.1
        const val scaleStep = 0.1
    }

    /**
     * generating sequence of LetterScale elements
     * using while from startScale to endScale
     * decreasing scaleStep
     * without repetitions of nearby elements
     */
    fun generate() : ArrayList<LetterScale> {
        val sequence = ArrayList<LetterScale>()
        var tempLetter: LetterEnum? = null
        var currentScale = startScale
        while (currentScale >= endScale) {
            repeat (letterPerScale) {
                var currentLetter = alphabet.random()
                while (currentLetter == tempLetter) {
                    currentLetter = alphabet.random()
                }
                tempLetter = currentLetter
                sequence.add(LetterScale(currentLetter, currentScale))
            }
            currentScale -= scaleStep
        }
        sequence.reverse()
        return sequence
    }
}