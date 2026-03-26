package app.application.notification

import java.awt.SystemTray
import java.awt.TrayIcon
import java.awt.image.BufferedImage

object WindowsNotifications {
    private var trayIcon: TrayIcon? = null

    fun init() {
        if (!SystemTray.isSupported() || trayIcon != null) return

        val tray = SystemTray.getSystemTray()
        val image = BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB)
        val newTrayIcon = TrayIcon(image, "Tmr")

        newTrayIcon.isImageAutoSize = true

        tray.add(newTrayIcon)
        trayIcon = newTrayIcon
    }

    fun notify(
        title: String,
        message: String,
    ) {
        trayIcon?.displayMessage(title, message, TrayIcon.MessageType.INFO)
    }
}
