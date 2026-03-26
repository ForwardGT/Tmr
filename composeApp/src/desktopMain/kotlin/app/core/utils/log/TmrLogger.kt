package app.core.utils.log

object TmrLogger {

    fun d(tag: String, message: String) {
        println("[Tmr][$tag] $message")
    }

    fun e(tag: String, throwable: Throwable, message: String? = null) {
        val prefix = if (message.isNullOrBlank()) "" else "$message: "
        println("[Tmr][$tag] ${prefix}${throwable.message}")
    }
}
