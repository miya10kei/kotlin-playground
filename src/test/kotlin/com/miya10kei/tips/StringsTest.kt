// Kotlin Tips: Strings
// https://www.youtube.com/watch?v=IL3RLKvWJF4
package com.miya10kei.tips

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class StringsTest {

    private data class Cat(val name: String, val sound: String, val photo: String)

    private val cats = listOf(
        Cat("Momo", "meow", "momo_dancing.jpg"),
        Cat(" ", "purr", "cat_sleeping.jpg"),
        Cat("Bella", "MEOW", "bella_hunting.jpg")
    )

    @Test
    fun use_removePrefix() {
        // Act
        val actual = "__MEOW".removePrefix("__")

        // Act
        assertThat(actual).isEqualTo("MEOW")
    }

    @Test
    fun use_removeSuffix() {
        // Act
        val actual = cats.map { it.photo.removeSuffix(".jpg") }

        // Assert
        assertThat(actual).containsExactly("momo_dancing", "cat_sleeping", "bella_hunting")
    }

    @Test
    fun use_removeSurrounding() {
        // Act
        val actual = "__MEOW__".removeSurrounding("__")

        // Assert
        assertThat(actual).isEqualTo("MEOW")
    }

    @Test
    fun use_ifBlank() {
        // Act
        // val actual = cats.map { if(it.name.isBlank()) "the Cat" else it.name }
        val actual = cats.map { it.name.ifBlank { "the Cat" } }

        // Assert
        assertThat(actual).containsExactly("Momo", "the Cat", "Bella")
    }

    @Test
    fun use_ifEmpty() {
        // Act
        val actual = "".ifEmpty { "Empty!!!" }

        // Assert
        assertThat(actual).isEqualTo("Empty!!!")
    }

    @Test
    fun use_equals_with_ignoreCase() {
        // Act
        // val actual = cats.first().sound.lowercase() == cats.last().sound.lowercase()
        val actual = cats.first().sound.equals(cats.last().sound, ignoreCase = true)

        // Assert
        assertThat(actual).isTrue
    }
}