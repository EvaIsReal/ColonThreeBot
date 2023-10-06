plugins {
    id("java")
}

group = "me.eva"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("net.dv8tion:JDA:5.0.0-beta.15")
    implementation("io.github.cdimascio:dotenv-java:3.0.0")
    implementation("io.github.classgraph:classgraph:4.8.162")
    implementation("mysql:mysql-connector-java:8.0.33")
}

tasks.test {
    useJUnitPlatform()
}