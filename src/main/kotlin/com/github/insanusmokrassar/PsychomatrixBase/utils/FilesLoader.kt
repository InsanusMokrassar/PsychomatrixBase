package com.github.insanusmokrassar.PsychomatrixBase.utils

import java.io.FileInputStream
import java.io.InputStream

object FilesLoader {
    @JvmStatic
    fun load(filename: String) : InputStream {
        return (this::class.java.classLoader.getResourceAsStream(filename) ?: FileInputStream(filename))
    }
}
