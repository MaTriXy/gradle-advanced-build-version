package org.moallemi.gradle.advancedbuildversion.integration

import com.android.build.gradle.AppPlugin
import org.gradle.api.Project
import org.gradle.plugin.devel.plugins.JavaGradlePluginPlugin
import org.gradle.testfixtures.ProjectBuilder
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.lessThan
import org.junit.Test
import org.moallemi.gradle.advancedbuildversion.AdvancedBuildVersionPlugin
import org.moallemi.gradle.advancedbuildversion.AdvancedBuildVersionPlugin.Companion.EXTENSION_NAME
import org.moallemi.gradle.advancedbuildversion.gradleextensions.AdvancedBuildVersionConfig
import org.moallemi.gradle.advancedbuildversion.gradleextensions.VersionCodeType.AUTO_INCREMENT_DATE
import org.moallemi.gradle.advancedbuildversion.gradleextensions.VersionCodeType.DATE

class VersionCodeConfigTest {

    @Test
    fun `Check versionCodeType = DATE`() {
        val advancedVersioning = givenProject()

        advancedVersioning.versionCodeConfig.versionCodeType(DATE)

        assertThat(advancedVersioning.versionCode, lessThan(MAX_VERSION_CODE))
    }

    @Test
    fun `Check versionCodeType = AUTO_INCREMENT_DATE`() {
        val advancedVersioning = givenProject()

        advancedVersioning.versionCodeConfig.versionCodeType(AUTO_INCREMENT_DATE)

        assertThat(advancedVersioning.versionCode, lessThan(MAX_VERSION_CODE))
    }

    private fun givenProject(): AdvancedBuildVersionConfig {
        val project: Project = ProjectBuilder.builder().build()
        project.repositories.google()

        project.plugins.apply(JavaGradlePluginPlugin::class.java)
        project.buildscript.dependencies.add("classpath", "com.android.tools.build:gradle:3.0.0")

        project.plugins.apply(AppPlugin::class.java)
        project.plugins.apply(AdvancedBuildVersionPlugin::class.java)
        return project.extensions.getByName(EXTENSION_NAME) as AdvancedBuildVersionConfig
    }

    companion object {
        // Based on https://developer.android.com/studio/publish/versioning
        private const val MAX_VERSION_CODE = 2_100_000_000
    }
}
