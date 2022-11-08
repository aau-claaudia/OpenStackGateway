import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val openstack4jVersion = "3.10"
val jacksonVersion = "2.14.0"
val kotlinVersion = "1.6.20"

plugins {
    id("org.springframework.boot") version "2.6.7"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
    kotlin("plugin.allopen") version "1.6.21"
    kotlin("kapt") version "1.6.21"
    id("org.liquibase.gradle") version "2.0.4"
    id("org.openapi.generator") version "5.4.0"
    id("org.jetbrains.dokka") version "1.6.21"
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}

group = "dk.aau.claaudia"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        name = "UCloudMaven"
        url = uri("https://mvn.cloud.sdu.dk/releases")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux:2.6.7")
    implementation("org.springframework.cloud:spring-cloud-starter-config:3.1.2")
    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap:3.1.2")
    implementation("com.auth0:java-jwt:3.19.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.github.openstack4j.core:openstack4j-core:$openstack4jVersion")
    implementation("com.github.openstack4j.core.connectors:openstack4j-httpclient:$openstack4jVersion")

    // SDU UCloud Provider Module
    implementation("dk.sdu.cloud:jvm-provider-support:2022.1.4")
    implementation("io.ktor:ktor-client-core:1.6.7")
    implementation("io.ktor:ktor-client-cio:1.6.7")
    implementation("io.ktor:ktor-client-websockets:1.6.7")
    implementation("io.ktor:ktor-client-okhttp:1.6.7")

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
    testImplementation("com.ninja-squad:springmockk:3.1.1")

    kapt("org.springframework.boot:spring-boot-configuration-processor")
}


tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

openApiGenerate {
    generatorName.set("kotlin-spring")
    inputSpec.set("$rootDir/specs/ucloud.json")
//    outputDir.set("$buildDir/generated-src")
    outputDir.set("$rootDir")
    modelPackage.set("dk.sdu.cloud.models")
//    modelNamePrefix.set("Ucloud")
    skipValidateSpec.set(true)
    generateModelDocumentation.set(false)
    //Only generate models for now
    globalProperties.put("models", "")
}

tasks.dokkaGfm.configure {
    outputDirectory.set(buildDir.parentFile.resolve("docs/code"))

    moduleName.set("OpenStackGateway")
}