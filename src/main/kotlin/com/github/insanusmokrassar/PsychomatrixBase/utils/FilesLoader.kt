package com.github.insanusmokrassar.PsychomatrixBase.utils

import java.io.FileInputStream
import java.io.InputStream

fun load(filename: String) : InputStream {
    return (ClassLoader.getSystemResourceAsStream(filename) ?: FileInputStream(filename))
}
