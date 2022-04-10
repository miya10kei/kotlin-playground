@file:Suppress("TestFunctionName")

package com.miya10kei.kover

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class FizzBuzzTest {

    private val subject = FizzBuzz()

    @ParameterizedTest
    @ValueSource(ints = [3, 6, 9])
    fun Expect_Fizz__When__GivenMultipleOf3(value: Int) {
        // Act
        val actual = subject.calculate(value)

        // Assert
        assertThat(actual).isEqualTo("Fizz")
    }

    @ParameterizedTest
    @ValueSource(ints = [5, 10, 20])
    fun Expect_Buzz__When__GivenMultipleOf5(value: Int) {
        // Act
        val actual = subject.calculate(value)

        // Assert
        assertThat(actual).isEqualTo("Buzz")
    }

    @ParameterizedTest
    @ValueSource(ints = [15, 30, 45])
    fun Expect_Buzz__When__GivenMultipleOf3And5(value: Int) {
        // Act
        val actual = subject.calculate(value)

        // Assert
        assertThat(actual).isEqualTo("FizzBuzz")
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 2, 4, 7, 8, 11, 13, 14, 16])
    fun Expect_value__When__GivenNotMultipleOf3And5(value: Int) {
        // Act
        val actual = subject.calculate(value)

        // Assert
        assertThat(actual).isEqualTo(value.toString())
    }
}