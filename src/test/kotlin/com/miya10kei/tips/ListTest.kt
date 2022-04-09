// Kotlin Tips: Improving Loops
// https://www.youtube.com/watch?v=i-kyPp1qFBA
package com.miya10kei.tips

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.io.PrintStream

internal class ListTest {

    private val standardOut: PrintStream = System.out
    private val outputStreamCapture: OutputStream = ByteArrayOutputStream()

    @BeforeEach
    fun setup() {
        System.setOut(PrintStream(outputStreamCapture))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(standardOut)
    }

    private val fruits = listOf("Apple", "Banana", "Cherry", "Dorian")
    private val expected = """
        0: Apple
        1: Banana
        2: Cherry
        3: Dorian
        
    """.trimIndent()

    @Test
    fun use_size() {
        // Act
        for (index in 0..fruits.size - 1) {
            val fruit = fruits[index]
            println("$index: $fruit")
        }

        // Assert
        assertThat(outputStreamCapture.toString()).isEqualTo(expected)
    }

    @Test
    fun use_until() {
        // Act
        for (index in 0 until fruits.size) {
            val fruit = fruits[index]
            println("$index: $fruit")
        }

        // Assert
        assertThat(outputStreamCapture.toString()).isEqualTo(expected)
    }

    @Test
    fun use_lastIndex() {
        // Act
        for (index in 0..fruits.lastIndex) {
            val fruit = fruits[index]
            println("$index: $fruit")
        }

        // Assert
        assertThat(outputStreamCapture.toString()).isEqualTo(expected)
    }

    @Test
    fun use_indices() {
        // Act
        for (index in fruits.indices) {
            val fruit = fruits[index]
            println("$index: $fruit")
        }

        // Assert
        assertThat(outputStreamCapture.toString()).isEqualTo(expected)
    }

    @Test
    fun use_withIndex() {
        // Act
        for ((index, fruit) in fruits.withIndex()) {
            println("$index: $fruit")
        }

        // Assert
        assertThat(outputStreamCapture.toString()).isEqualTo(expected)
    }

    @Test
    fun use_forEachIndexed() {
        // Act
        fruits.forEachIndexed { index, fruit ->
            println("$index: $fruit")
        }

        // Assert
        assertThat(outputStreamCapture.toString()).isEqualTo(expected)
    }
}
