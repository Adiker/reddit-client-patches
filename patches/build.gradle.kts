group = "app.morphe"

patches {
    about {
        name = "Reddit Client Patches"
        description = "Morphe patches for Reddit clients, including Infinity+ 8.3.0. " +
                "This app uses code from Patcheddit. To learn more, visit https://reddit.com/r/patcheddit"
        source = "https://github.com/Adiker/reddit-client-patches"
        author = "Adiker; original patches by wchill and contributors"
        contact = "https://github.com/Adiker/reddit-client-patches/issues"
        website = "https://github.com/Adiker/reddit-client-patches"
        license = "Additional conditions under GPL section 7 apply: attribution and project name restrictions. See LICENSE file."
    }
}

dependencies {
    // Used by JsonGenerator.
    implementation(libs.gson)

    // Required due to smali, or build fails. Can be removed once smali is bumped.
    implementation(libs.guava)

    implementation(libs.morphe.patches.library)

    // Android API stubs defined here.
    compileOnly(project(":patches:stub"))
}

tasks {
    register<JavaExec>("checkStringResources") {
        description = "Checks resource strings for invalid formatting"

        dependsOn(compileKotlin)

        classpath = sourceSets["main"].runtimeClasspath
        mainClass.set("app.morphe.util.resource.CheckStringResourcesKt")
    }

    register<JavaExec>("generatePatchesList") {
        description = "Build patch with patch list"

        dependsOn(build)

        classpath = sourceSets["main"].runtimeClasspath
        mainClass.set("app.morphe.util.PatchListGeneratorKt")
    }
    // Used by gradle-semantic-release-plugin.
    publish {
        dependsOn("generatePatchesList")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs = listOf("-Xcontext-parameters")
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Adiker/reddit-client-patches")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
