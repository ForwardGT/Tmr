package org.example.project.core.utils

import java.awt.Taskbar
import javax.imageio.ImageIO

fun setTaskbarIcon(resourcePath: String) {
    val url = object {}.javaClass.getResource(resourcePath)
    url?.let {
        Taskbar.getTaskbar().iconImage = ImageIO.read(it)
    }
}