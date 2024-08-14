import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val openstack4jVersion = "3.12"
val jacksonVersion = "2.17.2"
val kotlinVersion = "1.9.24"
// TODO: we can't update to springboot 3 while we use the old sdu provider library, since that breaks dependencies
//val springBootVersion = "3.3.2"
val springBootVersion = "2.7.18"
val ktorClientVersion = "1.6.8"

plugins {
    //id("org.springframework.boot") version "3.3.2"
    id("org.springframework.boot") version "2.7.18"
    id("io.spring.dependency-management") version "1.1.2"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
    kotlin("plugin.jpa") version "1.9.24"
    kotlin("kapt") version "1.9.24"
    id("org.jetbrains.dokka") version "1.8.20"
}

group = "dk.aau.claaudia"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_19

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        name = "UCloudMaven"
        url = uri("https://mvn.cloud.sdu.dk/releases")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-webflux:$springBootVersion")
    implementation("org.springframework.cloud:spring-cloud-starter-config:3.1.8") // TODO: update when updating spring boot version to 3.x
    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap:3.1.7") // TODO: update when updating spring boot version to 3.x
    implementation("com.auth0:java-jwt:4.4.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("org.springdoc:springdoc-openapi-ui:1.8.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.github.openstack4j.core:openstack4j-core:$openstack4jVersion")
    implementation("com.github.openstack4j.core.connectors:openstack4j-httpclient:$openstack4jVersion")

    // SDU UCloud Provider Module
    implementation("dk.sdu.cloud:jvm-provider-support:2022.1.51-devel-hippo")
    // TODO: we need to upgrade the sdu library before we can update the ktor libraries because of dependencies
    implementation("io.ktor:ktor-client-core:$ktorClientVersion")
    implementation("io.ktor:ktor-client-cio:$ktorClientVersion")
    implementation("io.ktor:ktor-client-websockets:$ktorClientVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorClientVersion")

    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("org.springframework.boot:spring-boot-starter-websocket")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
        exclude(module = "mockito-core")
    }
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("com.ninja-squad:springmockk:4.0.2")

    kapt("org.springframework.boot:spring-boot-configuration-processor")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "19"
    }
}

tasks.dokkaGfm.configure {
    outputDirectory.set(buildDir.parentFile.resolve("docs/code"))

    moduleName.set("OpenStackGateway")
}