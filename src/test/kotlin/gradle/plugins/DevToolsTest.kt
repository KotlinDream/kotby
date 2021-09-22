package gradle.plugins

import org.amshove.kluent.shouldNotBeNull
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName

internal class DevToolsTest {

    @Test
    @DisplayName("测试所有任务都被加载成功")
    fun apply() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply("info.dreamcoder.devtools")

        project.tasks.getByName("guard").shouldNotBeNull()
    }
}