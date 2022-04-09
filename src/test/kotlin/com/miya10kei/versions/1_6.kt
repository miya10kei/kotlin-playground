// Kotlin Standard Library Changes in 1.6
// https://www.youtube.com/watch?v=GWWFCyfNGFs&t=322s
@file:Suppress("ClassName")

package com.miya10kei.versions

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import kotlin.reflect.KType
import kotlin.reflect.typeOf
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

internal class `1_6` {

    @Test
    fun handling_standard_input() {
        // Arrange
        val standardIn = System.`in`

        try {
            // Arrange
            val inputStream1 = ByteArrayInputStream("Hello\nkotlin 1.6!".toByteArray())
            System.setIn(inputStream1)

            // Act
            val actual1 = listOf(
                readLine(), // alias = readlnOrNull()
                readLine(),
                readLine(),
            )

            // Assert
            assertThat(actual1).containsExactly("Hello", "kotlin 1.6!", null)

            // Arrange
            val inputStream2 = ByteArrayInputStream("Hello\nkotlin 1.6!".toByteArray())
            System.setIn(inputStream2)

            // Act
            val actual2 = listOf(
                readln(),
                readln(),
            )
            val actual2Exception = catchThrowable { readln() }

            // Assert
            assertThat(actual2).containsExactly("Hello", "kotlin 1.6!")
            assertThat(actual2Exception)
                .isInstanceOf(java.lang.RuntimeException::class.java)
                .hasMessage("EOF has already been reached")

        } finally {
            System.setIn(standardIn)
        }
    }

    @Test
    fun collection_builders() {
        // Act
        val actual = buildList {
            add("Apple")
            add("Banana")
            addAll(listOf("Cherry", "Dorian"))
            set(1, "Blackcurrant")
            add(0, "Mystery fruit")
            reverse()
        }

        // Assert
        assertThat(actual).containsExactly("Dorian", "Cherry", "Blackcurrant", "Apple", "Mystery fruit")
    }

    @Test
    fun duration_api() {
        // Arrange
        val fortnight = 14.days
        val goneWithTheWindow = 3.hours + 57.minutes

        // Act
        val fortnightHours = fortnight.inWholeHours
        val calcResult = fortnight / goneWithTheWindow

        // Assert
        assertThat(fortnightHours).isEqualTo(336L)
        assertThat(calcResult).isEqualTo(85.0632911392405)

        // Arrange
        val iso = Duration.parseIsoString("P10DT2H30M")

        // Act
        iso.toComponents { hours, minutes, seconds, _ ->
            // Assert
            assertThat(hours).isEqualTo(242)
            assertThat(minutes).isEqualTo(30)
            assertThat(seconds).isEqualTo(0)
        }
    }

    @Test
    fun split_to_sequence() {
        // Arrange
        val text = "one OR two AND three&four BUT five MAYBE six"

        // Act
        val actual = text.splitToSequence("[A-Z]+".toRegex()).toList()

        // Assert
        assertThat(actual).containsExactly(
            "one ", " two ", " three&four ", " five ", " six"
        )
    }

    @Test
    fun rotating_bits() {
        // Arrange
        fun UInt.toPaddingBinaryString() =
            toString(2).padStart(32, '0')

        val number = 0b11111000000001110000000010110100u

        // Act
        val actual1 = number.rotateRight(1).toPaddingBinaryString()
        val actual2 = number.rotateRight(2).toPaddingBinaryString()

        // Assert
        assertThat(actual1).isEqualTo("01111100000000111000000001011010")
        assertThat(actual2).isEqualTo("00111110000000011100000000101101")

        // Act
        val actual3 = number.rotateLeft(1).toPaddingBinaryString()
        val actual4 = number.rotateLeft(2).toPaddingBinaryString()

        // Assert
        assertThat(actual3).isEqualTo("11110000000011100000000101101001")
        assertThat(actual4).isEqualTo("11100000000111000000001011010011")
    }

    @Test
    fun type_of() {
        // Act
        val actual1 = describe<List<Int>>()

        // Assert
        assertThat(actual1).isEqualTo("kotlin.Int")

        // Act
        val actual2 = describe<Float>()

        // Assert
        assertThat(actual2).isEqualTo("kotlin.Float")
    }

    private inline fun <reified T : Any> describe() =
        describe(typeOf<T>())

    private fun describe(type: KType): String =
        if (type.classifier == List::class) type.arguments.single().type.toString()
        else type.toString()
}
