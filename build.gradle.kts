plugins {
    kotlin("jvm") version "1.4.30"
    id("maven-publish")
}

buildscript {
    repositories {
        mavenCentral()
    }
}


group = "skywolf46"
version = properties["version"] as String

repositories {
    mavenCentral()

    maven(properties["reposilite.release"] as String)
    maven(properties["reposilite.spigot"] as String)
}

tasks {
    processResources {
        outputs.upToDateWhen { false }
        expand("version" to project.properties["version"])
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot:1.12.2")
    implementation("skywolf46:exutil:1.35.1")
}

publishing {
    repositories {
        maven {
            name = "Github"
            url = uri("https://maven.pkg.github.com/milkyway0308/InventoryUserInterface")
            credentials {
                username = properties["gpr.user"] as String
                password = properties["gpr.key"] as String
            }
        }
        maven {
            name = "Reposilite"
            url = uri(properties["reposilite.release"] as String)
            credentials {
                username = properties["reposilite.user"] as String
                password = properties["reposilite.token"] as String
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("jar") {
            from(components["java"])
            groupId = "skywolf46"
            artifactId = "inventoryuserinterface"
            version = properties["version"] as String
        }
        create<MavenPublication>("jarAlias") {
            from(components["java"])
            groupId = "skywolf46"
            artifactId = "iui"
            version = properties["version"] as String
        }
    }
}
