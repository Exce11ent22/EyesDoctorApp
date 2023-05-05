package com.vsu.eyesdoctorapp

import com.vsu.eyesdoctorapp.service.diagnostics.LetterEnum
import com.vsu.eyesdoctorapp.service.diagnostics.LetterScale
import com.vsu.eyesdoctorapp.service.diagnostics.ResultCalculator
import org.junit.Assert.assertEquals
import org.junit.Test

class ResultCalculatorUnitTest {

    @Test
    fun testResultCalculator1(){
        val sequence = ArrayList<LetterScale>()
        sequence.add(LetterScale(LetterEnum.SH, 1.0))
        sequence.add(LetterScale(LetterEnum.SH, 1.0))
        sequence.add(LetterScale(LetterEnum.SH, 1.0))

        sequence.add(LetterScale(LetterEnum.SH, 1.0))
        sequence.add(LetterScale(LetterEnum.SH, 1.0))
        sequence.add(LetterScale(LetterEnum.SH, 1.0))

        sequence.add(LetterScale(LetterEnum.SH, 1.0))
        sequence.add(LetterScale(LetterEnum.SH, 1.0))
        sequence.add(LetterScale(LetterEnum.SH, 1.0))

        val resultCalculator = ResultCalculator(sequence)

        resultCalculator.input("Ш")
        resultCalculator.input("Ш")
        resultCalculator.input("Ш")

        println("test 1")
        assertEquals(resultCalculator.isDone(), false)
        assertEquals(resultCalculator.getResult(), 10.0, 0.1)

        resultCalculator.input("Ш")
        resultCalculator.input("Ш")
        resultCalculator.input("Ш")

        println("test 2")
        assertEquals(resultCalculator.isDone(), false)
        assertEquals(resultCalculator.getResult(), 20.0, 0.1)

        resultCalculator.input("Ш")
        resultCalculator.input("Б")
        resultCalculator.input("Ш")

        println("test 3")
        assertEquals(resultCalculator.isDone(), true)
        assertEquals(resultCalculator.getResult(), 27.0, 0.1)
    }

    @Test
    fun testResultCalculator2(){
        val sequence = ArrayList<LetterScale>()
        sequence.add(LetterScale(LetterEnum.SH, 1.0))
        sequence.add(LetterScale(LetterEnum.SH, 1.0))
        sequence.add(LetterScale(LetterEnum.SH, 1.0))

        sequence.add(LetterScale(LetterEnum.SH, 1.0))
        sequence.add(LetterScale(LetterEnum.SH, 1.0))
        sequence.add(LetterScale(LetterEnum.SH, 1.0))

        sequence.add(LetterScale(LetterEnum.SH, 1.0))
        sequence.add(LetterScale(LetterEnum.SH, 1.0))
        sequence.add(LetterScale(LetterEnum.SH, 1.0))

        val resultCalculator = ResultCalculator(sequence)

        resultCalculator.input("Ш")
        resultCalculator.input("Ш")
        resultCalculator.input("Ш")

        println("test 1")
        assertEquals(resultCalculator.isDone(), false)
        assertEquals(resultCalculator.getResult(), 10.0, 0.1)

        resultCalculator.input("Ш")
        resultCalculator.input("Б")
        resultCalculator.input("Ш")

        println("test 2")
        assertEquals(resultCalculator.isDone(), true)
        assertEquals(resultCalculator.getResult(), 17.0, 0.1)

        resultCalculator.input("Ш")
        resultCalculator.input("Ш")
        resultCalculator.input("Ш")

        println("test 3")
        assertEquals(resultCalculator.isDone(), true)
        assertEquals(resultCalculator.getResult(), 17.0, 0.1)
    }

}