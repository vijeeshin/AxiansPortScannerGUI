plugins {
    id("java")
}

group = "com.axians"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.miglayout:miglayout-swing:11.0")
    implementation("org.hibernate.validator:hibernate-validator:9.0.0.Beta3")
    implementation("org.glassfish:jakarta.el:5.0.0-M1")
    implementation("org.hibernate.validator:hibernate-validator-annotation-processor:9.0.0.Beta3")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    manifest {
        attributes(
            "Main-Class" to "com.axians.Main" // Replace with your main class
        )
    }
}

java {
    {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

sourceSets {
    main {
        resources {
            srcDirs("src/main/resources") // Specify the directory containing your resources
        }
    }
}