plugins {
    id("java")
    id("application")
}

group = "com.dzmitryrymarau.algorithms"
version = "0.1.0-SNAPSHOT"

application {
    mainClass = "RandomWord"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {
    implementation(files("libs/algs4.jar"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}
