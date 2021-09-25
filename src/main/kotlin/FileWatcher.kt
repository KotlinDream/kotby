import mu.KotlinLogging
import java.io.File
import java.nio.file.*
import java.nio.file.StandardWatchEventKinds.*

class FileWatcher(private val watchPath: String) {

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
        logger.info{ "Start watch path [$dir]" }
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
        registerWatcher(watchPath)
        registerAllSubDir(watchPath)
    }

    private fun handleFileEvent(event: WatchEvent<*>, parentPath: File) {
        val kind = event.kind()
        val file = parentPath.resolve(event.context().toString())

        logger.info { """
            *********************************
            event:          [${event.kind()}]
            event.context() [${event.context()}]
            parentPath:     [$parentPath]
            filePath:       [${file.absolutePath}]
            isFile?:        [${file.isFile}]
            *********************************
        """.trimIndent() }

        if(file.isFile) {
            when(kind) {
                ENTRY_CREATE -> fileCreateAction?.invoke(file.absolutePath)
                ENTRY_MODIFY -> fileUpdateAction?.invoke(file.absolutePath)
                ENTRY_DELETE -> fileDeleteAction?.invoke(file.absolutePath)
            }
        }
    }

    // TODO: 2021/9/22 把执行放到后台线程，使得主程序还可以添加键盘的监听事件
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