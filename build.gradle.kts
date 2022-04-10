import com.adarshr.gradle.testlogger.theme.ThemeType
import kotlinx.kover.api.VerificationValueType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"

    id("com.adarshr.test-logger") version "3.2.0"
    id("org.jetbrains.kotlinx.kover") version "0.5.0"
}

group = "com.miya10kei.tips"
version = "0.0.1"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    testImplementation(platform("org.junit:junit-bom:5.8.2"))

    testImplementation("org.assertj:assertj-core:3.11.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf(
            "-opt-in=kotlin.RequiresOptIn"
        )
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    testlogger {
        theme = ThemeType.MOCHA
    }
}

tasks.koverVerify {
    rule {
        name = "Minimal line coverage rate in percent"
        bound {
            minValue = 100
        }
    }
}