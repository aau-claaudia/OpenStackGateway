import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.dokka.gradle.DokkaTask

val openstack4jVersion = "3.10"
val jacksonVersion = "2.13.0"
val kotlinVersion = "1.5.31"

plugins {
    id("org.springframework.boot") version "2.5.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.spring") version "1.5.31"
    kotlin("plugin.jpa") version "1.5.31"
    kotlin("plugin.allopen") version "1.5.31"
    kotlin("kapt") version "1.5.31"
    id("org.liquibase.gradle") version "2.0.4"
    id("org.openapi.generator") version "5.0.0"
    id("org.jetbrains.dokka") version "1.5.30"
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
        name = "UCloud Packages"
        url = uri("https://maven.pkg.github.com/sdu-escience/ucloud")
        credentials {
            val helpText = """





				Missing GitHub credentials. These are required to pull the packages required for this project. Please
				create a personal access token here: https://github.com/settings/tokens. This access token require
				the 'read:packages' scope.

				With this information you will need to add the following lines to your Gradle properties
				(~/.gradle/gradle.properties):

				gpr.user=YOUR_GITHUB_USERNAME
				gpr.token=YOUR_GITHUB_PERSONAL_ACCESS_TOKEN





			""".trimIndent()
            username = (project.findProperty("gpr.user") as? String?)
                ?: System.getenv("PACKAGES_USERNAME") ?: error(helpText)
            password = (project.findProperty("gpr.key") as? String?)
                ?: System.getenv("PACKAGES_TOKEN") ?: error(helpText)
        }
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux:2.5.5")
    implementation("org.springframework.cloud:spring-cloud-starter-config:3.0.5")
    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap:3.0.4")
    implementation("com.auth0:java-jwt:3.18.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("org.springdoc:springdoc-openapi-ui:1.5.10")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.github.openstack4j.core:openstack4j-core:$openstack4jVersion")
    implementation("com.github.openstack4j.core.connectors:openstack4j-httpclient:$openstack4jVersion")

    implementation("dk.sdu.cloud:jvm-provider-support-jvm:2021.2.0-storage0")
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("org.springframework.boot:spring-boot-starter-websocket")

    implementation("org.liquibase:liquibase-core:4.2.2")
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
    testImplementation("com.ninja-squad:springmockk:3.0.1")

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
    outputDirectory.set(buildDir.parentFile.resolve("documentation"))

    moduleName.set("OpenStackGateway")
}