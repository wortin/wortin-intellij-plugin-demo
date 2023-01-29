plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.8.0"
}

group = "com.wortin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2021.3.3")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf("java"))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    patchPluginXml {
        sinceBuild.set("213")
        untilBuild.set("223.*")
    }

    // 用于插件签名
    signPlugin {
        certificateChain.set(File(System.getenv("CERTIFICATE_CHAIN")).readText(Charsets.UTF_8))
        privateKey.set(File(System.getenv("PRIVATE_KEY")).readText(Charsets.UTF_8))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }

    publishPlugin {
        channels.set(listOf("alpha"))
    }

    // 禁用构建可搜索选项，可以避免在运行开发实例时，重新构建后，提示只能运行一个实例的错误
    // 沙盒IDE实例在调试器下运行时，热加载不起作用
    buildSearchableOptions {
        enabled = false
    }
}
