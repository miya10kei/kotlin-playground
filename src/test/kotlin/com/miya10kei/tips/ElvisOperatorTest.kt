// Kotlin Tips: The Elvis Operator
// https://www.youtube.com/watch?v=L9wqYQ-fXaM
package com.miya10kei.tips

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.io.PrintStream

internal class ElvisOperatorTest {

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

    private fun getName(): String? = null

    @Test
    fun use_elvis_operator() {
        // Arrange

        val name = getName()

        // Act
        val actual1 = name ?: "undefined"

        // Assert
        assertThat(actual1).isEqualTo("undefined")

        // Act
        val actual2 = name ?: run {
            print("Oops, name was not set!")
            "undefined"
        }

        // Assert
        assertThat(actual2).isEqualTo("undefined")
        assertThat(outputStreamCapture.toString()).isEqualTo("Oops, name was not set!")
    }
}