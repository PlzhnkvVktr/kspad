import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization").version("1.9.22")
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
    maven { url = uri("https://jitpack.io") }

    flatDir {
        dirs("libs")
    }
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
                implementation("org.jetbrains.androidx.navigation:navigation-compose:2.7.0-alpha07")
                implementation("com.fazecast:jSerialComm:2.7.0")
                implementation(":kserialpooler-2.0")
                implementation(":composables-lib-1.0")
                implementation(":polling-essentials-2.0")
                implementation("io.github.microutils:kotlin-logging:1.8.3")
                implementation("org.slf4j:slf4j-api:1.7.25")
                implementation("ch.qos.logback:logback-classic:1.2.3")
                implementation("ch.qos.logback:logback-core:1.2.3")
                implementation("org.jetbrains.exposed:exposed:0.17.14")
                implementation("org.xerial:sqlite-jdbc:3.39.3.0")
                implementation("org.apache.poi:poi:5.0.0")
                implementation("org.apache.poi:poi-ooxml:5.0.0")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0")
                implementation("org.jetbrains.compose.material:material-icons-extended-desktop:1.1.1")
//                implementation("io.insert-koin:koin-core:3.5.6")
                implementation(project.dependencies.platform("io.insert-koin:koin-bom:3.5.6"))
                implementation("io.insert-koin:koin-core")
                implementation("io.insert-koin:koin-compose")
//                implementation("io.insert-koin:koin-androidx-compose")
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "ru.avem.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "kspad"
            packageVersion = "1.0.0"
        }
    }
}
