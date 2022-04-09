package com.miya10kei.tips

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@OptIn(ExperimentalTime::class)
internal class TimingTest {

    @Test
    fun use_measureTimedValue() {
        // Arrange
        val longOperation = {
            repeat(times = 2_000_000) { print(".") }
            "Done"
        }

        // Act
        val (value, time) = measureTimedValue { longOperation() }
        println("It took $time to calculate $value.")

        // Assert
        assertThat(value).isEqualTo("Done")
        assertThat(time).isGreaterThan(Duration.ZERO)
        assertThat(time).isLessThan(Duration.INFINITE)
    }
}
