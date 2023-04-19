package com.vsu.eyesdoctorapp

import com.vsu.eyesdoctorapp.service.diagnostics.SequenceGenerator
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun test_repetitions() {
        val sequenceGenerator = SequenceGenerator()
        val sequence = sequenceGenerator.generate()
        for (element in sequence){
            println(element)
        }
        for (i in 1 until sequence.size){
            assertNotEquals(sequence[i - 1], sequence[i])
        }
    }

    @Test
    fun test_string_equals() {
        val l1 = "Ш"
        val l2 = "Ш"
        val l3 = "Б"
        assertTrue(l1 == l2)
        assertFalse(l1 == l3)
    }
}