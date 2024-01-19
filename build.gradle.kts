import net.pwall.json.kotlin.codegen.gradle.JSONSchemaCodegen
import net.pwall.json.kotlin.codegen.gradle.JSONSchemaCodegenPlugin

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("net.pwall.json:json-kotlin-gradle:0.96.1")
    }
}

plugins {
    kotlin("jvm") version "1.9.21"
    kotlin("plugin.serialization") version "1.9.21"
    `maven-publish`
}

repositories {
    mavenCentral()
}

val GITHUB_USER: String by project
val GITHUB_TOKEN: String by project

sourceSets.main.configure {
    kotlin.srcDirs(layout.buildDirectory.dir("generated-sources/kotlin"))
}

apply<JSONSchemaCodegenPlugin>()
configure<JSONSchemaCodegen> {
    packageName.set("no.domstol.esas.kontrakter")
    inputs {
        inputFile(file("kontrakter"))
    }
    outputDir.set(file("build/generated-sources/kotlin"))
}

publishing {

    repositories {
        maven {
            name = project.name
            url = uri("https://maven.pkg.github.com/domstolene/esas-genererte-kontrakter")
            credentials {
                username = GITHUB_USER
                password = GITHUB_TOKEN
            }
        }
    }

    publications {
        create<MavenPublication>("maven") {
            groupId = "no.domstol"
            artifactId = project.name
            from(components["java"])
            pom {
                licenses {
                    license {
                        name.set("GNU Lesser General Public License version 3 or later")
                        url.set("https://www.gnu.org/licenses/lgpl-3.0.txt")
                    }
                }
            }
        }
    }
}

