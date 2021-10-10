package info.dreamcoder.kotby.io

import mu.KotlinLogging
import java.io.File
import java.nio.file.*
import java.nio.file.StandardWatchEventKinds.*

class FileWatcher(vararg watchPaths: String) {

    private val watchPaths = watchPaths
    private val logger  = KotlinLogging.logger {}
    private val watcher = FileSystems.getDefault().newWatchService()

    private var fileCreateAction: ((String) -> Unit)? = null
    private var fileUpdateAction: ((String) -> Unit)? = null
    private var fileDeleteAction: ((String) -> Unit)? = null

    fun onFileCreate(action: (String) -> Unit) {
        fileCreateAction = action
    }

    fun onFileModify(action: (String) -> Unit) {
        fileUpdateAction = action
    }

    fun onFileDelete(action: (String) -> Unit) {
        fileDeleteAction = action
    }


    private fun registerWatcher(dir: String) {
        logger.info { "开始监听目录 [$dir]" }
        Paths.get(dir)
             .toAbsolutePath()
             .register(watcher, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE)
    }

    private fun registerAllSubDir(dir: String) {
        File(dir).walk().forEach {
            if (it.isDirectory) {
                registerWatcher(it.path)
            }
        }
    }

    private fun registerSelfAndAllSubDir() {
        watchPaths.forEach {
            if(File(it).isDirectory) {
                registerAllSubDir(it)
            } else {
                logger.info { "监控的目录 [$it] 不存在" }
            }
        }
    }

    private fun handleFileEvent(event: WatchEvent<*>, parentPath: File) {
        val kind = event.kind()
        val file = parentPath.resolve(event.context().toString())

        if(file.isFile) {

            logger.info {"""
                *********************************
                event:          [${event.kind()}]
                event.context() [${event.context()}]
                parentPath:     [$parentPath]
                filePath:       [${file.absolutePath}]
                isFile?:        [${file.isFile}]
                *********************************
            """.trimIndent()}

            if(FileContentWatcher.isChange(file.absolutePath)) {
                when(kind) {
                    ENTRY_CREATE -> fileCreateAction?.invoke(file.absolutePath)
                    ENTRY_MODIFY -> fileUpdateAction?.invoke(file.absolutePath)
                    ENTRY_DELETE -> fileDeleteAction?.invoke(file.absolutePath)
                }
            } else {
                logger.info { "[${file.path}] 文件内容没有变动" }
            }

        }
    }

    private fun processWatch() {
        while(true) {
            val key = watcher.take()
            val dir = File(key.watchable().toString())
            key.pollEvents().forEach { handleFileEvent(it, dir) }
            key.reset()
        }
    }

    fun create() {
        registerSelfAndAllSubDir()
        processWatch()
    }

}