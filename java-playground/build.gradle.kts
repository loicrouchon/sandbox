version = "1.0-SNAPSHOT"

plugins {
    java
    `jvm-test-suite`
    idea
    jacoco
}

java {
    modularity.inferModulePath.set(true)
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter(libs.versions.junit.get())

            dependencies {
                compileOnly(libs.lombok)
                // annotationProcessor(libs.lombok)
                implementation(libs.assertj)
                implementation(libs.bundles.mockito)
            }
        }
    }
}

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    testAnnotationProcessor(libs.lombok)

    implementation("com.opencsv:opencsv:3.8")
    implementation("org.ow2.sat4j:org.ow2.sat4j.core:2.3.4")
}
