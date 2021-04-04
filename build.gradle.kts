plugins {
    kotlin("jvm") version "1.4.30"
    id ("maven-publish")
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

    maven("https://maven.pkg.github.com/FUNetwork/SkywolfExtraUtility") {
        credentials {
            username = properties["gpr.user"] as String
            password = properties["gpr.key"] as String
        }
    }
}

tasks {
    processResources {
        outputs.upToDateWhen { false }
        expand("version" to project.properties["version"])
    }
}

dependencies {
    compileOnly(files("V:/API/Java/Minecraft/Bukkit/Spigot/Spigot 1.12.2.jar"))
    implementation("skywolf46:exutil:1.28.1")
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
    }
    publications{
        create<MavenPublication>("jar"){
            from(components["java"])
            groupId = "skywolf46"
            artifactId = "inventoryuserinterface"
            version = properties["version"] as String
            pom{
                url.set("https://github.com/milkyway0308/InventoryUserInterfac.git")
            }
        }
    }
}
