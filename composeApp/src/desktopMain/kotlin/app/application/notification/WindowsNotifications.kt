package app.application.notification

import java.awt.SystemTray
import java.awt.TrayIcon
import java.awt.image.BufferedImage

object WindowsNotifications {
    private var trayIcon: TrayIcon? = null

    fun init() {
        if (!SystemTray.isSupported()) return

        val tray = SystemTray.getSystemTray()

        val image = BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB)

        trayIcon = TrayIcon(image, "Tmr")
        trayIcon!!.isImageAutoSize = true  //TODO Переписать это говно

        tray.add(trayIcon)
    }

    fun notify(
        title: String,
        message: String,
    ) {
        trayIcon?.displayMessage(title, message, TrayIcon.MessageType.INFO)
    }
}

private fun sample () {

    // Не забыть инит сделать в application
    WindowsNotifications.init()


    // Вызов уведа
    WindowsNotifications.notify(
        "Tmr",
        "Settings opened"
    )
}
