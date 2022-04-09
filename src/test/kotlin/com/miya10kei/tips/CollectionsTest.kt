// Kotlin Tips: Kotlin Collections
// https://www.youtube.com/watch?v=ApXbm1T_eI4
package com.miya10kei.tips

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class CollectionsTest {

    private data class Fruit(val name: String, val suger: Int)

    private val fruits = listOf(
        Fruit("banana", 12),
        Fruit("apple", 10),
        Fruit("orange", 9),
        Fruit("pineapple", 10),
        Fruit("peach", 8),
        Fruit("lemon", 2),
        Fruit("mango", 13),
    ).sortedBy(Fruit::suger)

    @Test
    fun use_take() {
        // Act
        val actual = fruits.take(2)

        // Assert
        assertThat(actual).containsExactly(
            Fruit("lemon", 2),
            Fruit("peach", 8),
        )
    }

    @Test
    fun use_takeLast() {
        // Act
        val actual = fruits.takeLast(3)

        // Assert
        assertThat(actual).containsExactly(
            Fruit("pineapple", 10),
            Fruit("banana", 12),
            Fruit("mango", 13),
        )
    }

    @Test
    fun use_drop() {
        // Act
        val actual = fruits.drop(2)

        // Assert
        assertThat(actual).containsExactly(
            Fruit("orange", 9),
            Fruit("apple", 10),
            Fruit("pineapple", 10),
            Fruit("banana", 12),
            Fruit("mango", 13),
        )
    }

    @Test
    fun use_partition() {
        // Act
        val (actualFirstHalf, actualSecondHalf) = fruits.partition { it.suger < 10 }

        // Assert
        assertThat(actualFirstHalf).containsExactly(
            Fruit("lemon", 2),
            Fruit("peach", 8),
            Fruit("orange", 9),
        )
        assertThat(actualSecondHalf).containsExactly(
            Fruit("apple", 10),
            Fruit("pineapple", 10),
            Fruit("banana", 12),
            Fruit("mango", 13),
        )
    }

    @Test
    fun use_joinToString() {
        // Act
        val actual = fruits.reversed()
            .joinToString(
                separator = " + ",
                prefix = "😀 = [",
                postfix = "]",
                limit = 3,
                truncated = "MORE",
                transform = Fruit::name
            )

        // Assert
        assertThat(actual).isEqualTo("😀 = [mango + banana + pineapple + MORE]")
    }
}