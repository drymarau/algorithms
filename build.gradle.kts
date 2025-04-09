plugins {
    id("java")
}

group = "com.dzmitryrymarau.algorithms"
version = "0.1.0-SNAPSHOT"

dependencies {
    implementation(files("libs/algs4.jar"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

tasks.test {
    useJUnitPlatform()
}
