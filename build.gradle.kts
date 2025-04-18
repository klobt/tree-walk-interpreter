plugins {
    java
}

group = "org.klobt"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains:annotations:24.0.1")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("--enable-preview")
}

tasks.withType<Test>().configureEach {
    jvmArgs = listOf("--enable-preview")
}

tasks.withType<JavaExec>().configureEach {
    jvmArgs = listOf("--enable-preview")
}

tasks.jar {
    archiveBaseName.set("tree-walk-interpreter")
    archiveVersion.set("0.1")
    manifest {
        attributes["Main-Class"] = "org.klobt.Main"
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
