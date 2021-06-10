import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val openstack4jVersion = "3.8"
val jacksonVersion = "2.12.0"
val kotlinVersion = "1.4.20"

plugins {
    id("org.springframework.boot") version "2.4.0"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    kotlin("jvm") version "1.4.20"
    kotlin("plugin.spring") version "1.4.20"
    kotlin("plugin.jpa") version "1.4.20"
    kotlin("plugin.allopen") version "1.4.20"
    kotlin("kapt") version "1.4.20"
    id("org.liquibase.gradle") version "2.0.4"
    id("org.openapi.generator") version "5.0.0"
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
    implementation("org.springframework.boot:spring-boot-starter-webflux:2.4.2")
    //implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.cloud:spring-cloud-starter-config:3.0.1")
    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap:3.0.1")
    implementation("com.auth0:java-jwt:3.12.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("org.springdoc:springdoc-openapi-ui:1.5.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.github.openstack4j.core:openstack4j-core:$openstack4jVersion")
    implementation("com.github.openstack4j.core.connectors:openstack4j-httpclient:$openstack4jVersion")

    implementation("dk.sdu.cloud:jvm-provider-support:2021.1.5")
    implementation("com.squareup.okhttp3:okhttp:4.6.0")
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
    testImplementation("com.ninja-squad:springmockk:1.1.3")

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
