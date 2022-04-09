// Advanced Collection Operations in Kotlin
// https://www.youtube.com/watch?v=N4CpLxGJlq0
@file:Suppress("ClassName")

package com.miya10kei.tips

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class AdvancedCollectionsTest {

    @Nested
    inner class Any_None_All_Test {

        private inner class Person(val name: String, val age: Int, val driverLicense: Boolean = false)

        private val friendGroup = listOf(
            Person("Joe", 19),
            Person("Mic", 15),
            Person("Hay", 33, true),
            Person("Cal", 25),
        )

        private val nobody = emptyList<Person>()

        @Test
        fun use_any_with_friendGroup() {
            // Act
            val actual = friendGroup.any(Person::driverLicense)

            // Assert
            assertThat(actual).isTrue
        }

        @Test
        fun use_any_with_nobody() {
            // Act
            val actual = nobody.any(Person::driverLicense)

            // Assert
            assertThat(actual).isFalse
        }

        @Test
        fun use_none_with_friendGroup() {
            // Act
            val actual = friendGroup.none { it.age < 18 }

            // Assert
            assertThat(actual).isFalse
        }

        @Test
        fun use_none_with_nobody() {
            // Act
            val actual = nobody.none { it.age < 18 }

            // Assert
            assertThat(actual).isTrue
        }

        @Test
        fun use_all_with_friendGroup() {
            // Act
            val actual = friendGroup.all { it.name.length < 4 }

            // Assert
            assertThat(actual).isTrue
        }

        @Test
        fun use_all_with_nobody() {
            // Act
            val actual = nobody.all { it.name.length < 4 }

            // Assert
            assertThat(actual).isTrue
        }
    }

    @Nested
    inner class Chunked_Windowed_Flatten_Test {

        private val emojis = listOf("🌱", "🚀", "💡", "🐧", "⚙", "🤖", "📚")

        @Test
        fun use_chunked() {
            // Act
            val actual1 = emojis.chunked(3)

            // Assert
            assertThat(actual1).containsExactly(
                listOf("🌱", "🚀", "💡"),
                listOf("🐧", "⚙", "🤖"),
                listOf("📚"),
            )

            // Act
            val actual2 = emojis.chunked(3, List<String>::reversed)

            // Assert
            assertThat(actual2).containsExactly(
                listOf("💡", "🚀", "🌱"),
                listOf("🤖", "⚙", "🐧"),
                listOf("📚"),
            )
        }

        @Test
        fun use_windowed() {
            // Act
            val actual1 = emojis.windowed(3)

            // Assert
            assertThat(actual1).containsExactly(
                listOf("🌱", "🚀", "💡"),
                listOf("🚀", "💡", "🐧"),
                listOf("💡", "🐧", "⚙"),
                listOf("🐧", "⚙", "🤖"),
                listOf("⚙", "🤖", "📚"),
            )

            // Act
            val actual2 = emojis.windowed(4, 2, true)

            // Assert
            assertThat(actual2).containsExactly(
                listOf("🌱", "🚀", "💡", "🐧"),
                listOf("💡", "🐧", "⚙", "🤖"),
                listOf("⚙", "🤖", "📚"),
                listOf("📚"),
            )

            // Act
            val actual3 = emojis.windowed(4, 2, false)

            // Assert
            assertThat(actual3).containsExactly(
                listOf("🌱", "🚀", "💡", "🐧"),
                listOf("💡", "🐧", "⚙", "🤖"),
            )

            // Act
            val actual4 = emojis.windowed(4, 2, true, List<String>::reversed)

            // Assert
            assertThat(actual4).containsExactly(
                listOf("🐧", "💡", "🚀", "🌱"),
                listOf("🤖", "⚙", "🐧", "💡"),
                listOf("📚", "🤖", "⚙"),
                listOf("📚"),
            )
        }

        @Test
        fun use_flatten() {
            // Act
            val actual = emojis.windowed(3).flatten()

            // Assert
            assertThat(actual).containsExactly(
                "🌱", "🚀", "💡",
                "🚀", "💡", "🐧",
                "💡", "🐧", "⚙",
                "🐧", "⚙", "🤖",
                "⚙", "🤖", "📚",
            )
        }
    }

    @Nested
    inner class FlatMap_Test {

        private val names = listOf("Lou", "Mel", "Cyn")

        @Test
        fun use_flatMap() {
            // Act
            val actual = names.flatMap(String::toList)

            // Assert
            assertThat(actual).containsExactly('L', 'o', 'u', 'M', 'e', 'l', 'C', 'y', 'n')
        }
    }

    @Nested
    inner class Zip_Unzip_Test {

        private val germanCities = listOf("Aachen", "Bielefeld", "München")
        private val germanLicensePlates = listOf("AC", "BI", "M")

        @Test
        fun use_zip() {
            // Act
            val actual1 = germanCities.zip(germanLicensePlates)

            // Assert
            assertThat(actual1).containsExactly(
                Pair("Aachen", "AC"), Pair("Bielefeld", "BI"), Pair("München", "M")
            )

            // Act(infix)
            val actual2 = germanCities zip germanLicensePlates

            // Assert
            assertThat(actual2).containsExactly(
                Pair("Aachen", "AC"), Pair("Bielefeld", "BI"), Pair("München", "M")
            )

            // Act
            val actual3 = germanCities.zip(germanLicensePlates) { city, plate ->
                Pair(city.uppercase(), plate.lowercase())
            }

            // Assert
            assertThat(actual3).containsExactly(
                Pair("AACHEN", "ac"), Pair("BIELEFELD", "bi"), Pair("MÜNCHEN", "m")
            )
        }

        @Test
        fun use_unzip() {
            // Arrange
            val citiesToPlates = germanCities.zip(germanLicensePlates) { city, plate ->
                Pair(city.uppercase(), plate.lowercase())
            }

            // Act
            val (cities, plates) = citiesToPlates.unzip()

            // Assert
            assertThat(cities).containsExactly("AACHEN", "BIELEFELD", "MÜNCHEN")
            assertThat(plates).containsExactly("ac", "bi", "m")
        }
    }

    @Nested
    inner class ZipWithNext_Sum_Average_Reduce_Test {

        private val random = listOf(3, 1, 4, 1, 5, 9, 2, 6, 5, 4)

        @Test
        fun use_zipWithNext() {
            // Act
            val actual1 = random.zipWithNext()

            // Assert
            assertThat(actual1).containsExactly(
                Pair(3, 1),
                Pair(1, 4),
                Pair(4, 1),
                Pair(1, 5),
                Pair(5, 9),
                Pair(9, 2),
                Pair(2, 6),
                Pair(6, 5),
                Pair(5, 4),
            )

            // Act
            val actual2 = random.zipWithNext { a, b -> b - a }

            // Assert
            assertThat(actual2).containsExactly(-2, 3, -3, 4, 4, -7, 4, -1, -1)
        }

        @Test
        fun use_sum() {
            // Act
            val actual = random.sum()

            // Assert
            assertThat(actual).isEqualTo(40)
        }

        @Test
        fun use_average() {
            // Act
            val actual = random.average()

            // Assert
            assertThat(actual).isEqualTo(4.0)
        }

        @Test
        fun use_reduce() {
            // Act
            val actual = random.reduce { acc, value ->
                acc * value
            }

            // Assert
            assertThat(actual).isEqualTo(129600)
        }
    }

    @Nested
    inner class Fold_RunningFold_Test {

        private val fruits = listOf("apple", "cherry", "banana", "orange")

        @Test
        fun use_fold() {
            // Act
            val actual = fruits.fold(1) { acc, value ->
                acc * value.length
            }

            // Assert
            assertThat(actual).isEqualTo(1080)
        }

        @Test
        fun use_runningFold() {
            // Act
            val actual = fruits.runningFold(1) { acc, value ->
                acc * value.length
            }

            // Assert
            assertThat(actual).containsExactly(1, 5, 30, 180, 1080)
        }
    }
}